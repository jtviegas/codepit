package org.challenges.camunda.bpmn;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
public class Solver {

    private static final String NODE_TYPE_START="startEvent";
    private static final String NODE_TYPE_END="endEvent";

    public Optional<Deque<FlowNode>> getPath(String xml, String startNodeId, String endNodeId){

        log.trace("[getPath|in] ({},{},{})", xml, startNodeId, endNodeId);

        Optional<Deque<FlowNode>> result = Optional.empty();
        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        ModelElementInstance start = modelInstance.getModelElementById(startNodeId);
        if( null == start )
            throw new RuntimeException(format("couldn't find start node with id: %s", startNodeId));

        ModelElementInstance end = modelInstance.getModelElementById(endNodeId);
        if( null == end )
            throw new RuntimeException(format("couldn't find end node with id: %s", endNodeId));

        if( start.equals(end) ){
            Deque<FlowNode> r = new LinkedList<>();
            r.addFirst((FlowNode)start);
            result = Optional.of(r);
        }
        else {
            Deque<FlowNode> initialPath = new LinkedList<>();
            initialPath.addFirst((FlowNode)start);

            // work-in-progress paths will be held here until they are sorted out
            List<Deque<FlowNode>> pathsFound = new LinkedList<>();
            pathsFound.add(initialPath);

            while( ! pathsFound.isEmpty() ) {
                Iterator<Deque<FlowNode>> pathsProcessing = new ArrayList(pathsFound).iterator();
                while( pathsProcessing.hasNext() ){

                    Deque<FlowNode> pathInProcess = pathsProcessing.next();
                    FlowNode lastNode = pathInProcess.getLast();

                    if( lastNode.equals((FlowNode)end) ){
                        // this is an end node, so we found a complete path
                        result =  Optional.of(pathInProcess);
                        break;
                    }

                    Collection<SequenceFlow> outgoingEdges = lastNode.getOutgoing();
                    if( outgoingEdges.isEmpty() ){
                        // cul-de-sac - it is not the desired end node and there is no way out of here
                        pathsFound.remove(pathInProcess);
                    }
                    else if( 1 == outgoingEdges.size() ){
                        // we should add one more node to the path if it is not a loop
                        SequenceFlow edge = (SequenceFlow) outgoingEdges.toArray()[0];
                        if( !pathInProcess.contains(edge.getTarget()) )
                           pathInProcess.addLast(edge.getTarget());
                    }
                    else {
                        // this is a path bifurcation, we have to replicate paths
                        // let's keep the base one, at its initial state, from which we are going to replicate
                        Deque<FlowNode> basePath = new LinkedList<>(pathInProcess);
                        int i = 0;
                        for(SequenceFlow edge : outgoingEdges){
                            Deque<FlowNode> pathPointer = null;
                            boolean replicatedPath = false;
                            if( 0 == i++ )
                                pathPointer = pathInProcess;
                            else {
                                replicatedPath = true;
                                pathPointer = new LinkedList<>(basePath);
                            }

                            if( ! pathPointer.contains(edge.getTarget()) ){
                                pathPointer.addLast(edge.getTarget());
                                if(replicatedPath)
                                    pathsFound.add(pathPointer);
                            }

                        }
                    }

                }
                if( result.isPresent() )
                    break;
            }
        }

        log.trace("[getPath|out] => {}", result);
        return result;
    }

    public static String toString(Optional<Deque<FlowNode>> path, String startNodeId, String endNodeId){
        String result = null;
        if( path.isPresent() )
            result = format("The path from %s to %s is: [%s]", startNodeId, endNodeId, path.get().stream().map(FlowNode::getId).collect(Collectors.joining(", "))) ;
        else
            result = format("couldn't find path from %s to %s", startNodeId, endNodeId) ;
        return result;
    }


}
