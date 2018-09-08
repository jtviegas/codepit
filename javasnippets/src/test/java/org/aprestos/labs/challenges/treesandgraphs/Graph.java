package org.aprestos.labs.challenges.treesandgraphs;

import java.util.Queue;

import com.google.common.collect.Queues;

public class Graph {

  private static final int MAX_VERTEX_NUMBER = 1000;
  
  
  EdgeNode[] edges = new EdgeNode[MAX_VERTEX_NUMBER+1];
  int[] degree = new int[MAX_VERTEX_NUMBER+1];
  int nVertices;
  int nEdges;
  boolean directed;

  private void init(boolean directed) {
    this.directed=directed;
  }
  
  void read(int[][] def, boolean directed) {
    
    init(directed);
    
    for(int i=0; i<def.length; i++) 
      insert(def[i][0], def[i][1], directed);
    
  }
  
  void insert(int x, int y, boolean directed) {
    
    EdgeNode node = new EdgeNode();
    node.weight = 0;
    node.y = y;
    node.next = edges[x];
    if(null == edges[x])
      nVertices++;
    edges[x] = node;
    degree[x]++;
    
    if(!directed) 
      insert(y,x,true);
    else
      nEdges++;
    
  }
  
  void print() {
    
    for(int i = 0 ;i < nVertices;i++) {
      System.out.print(String.format("%d: ", i));
      EdgeNode p = edges[i];
      while(null != p) {
        System.out.print(String.format(" %d", p.y));
        p = p.next;
      }
      System.out.print(System.getProperty("line.separator"));
    }
    
  }
  
  void process_vertex_start(int v) {
    
  }
  
  void process_vertex_end(int v) {
    
  }
  
  void bfs(int start) {
    Queue<Integer> queue = Queues.newArrayDeque();
    int v, y;
    EdgeNode edge;
    boolean[] discovered = new boolean[MAX_VERTEX_NUMBER+1];
    boolean[] processed = new boolean[MAX_VERTEX_NUMBER+1];
    int[] parent = new int[MAX_VERTEX_NUMBER+1];
    
    queue.add(start);
    discovered[start] = true;
    while(!queue.isEmpty()) {
      v = queue.poll();
      process_vertex_start(v);
      EdgeNode p = edges[v];
      while(null != p) {
        y = p.y;
        
        if( !processed[y] || directed )
          discovered[y] = true;
          
        
      }
      process_vertex_end(v);
    }

  }
  
}
