package org.challenges.camunda.bpmn;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Deque;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@SpringBootApplication
public class BpmnApplication {

	public static void main(String[] args) {
		if( 2 != args.length)
			usage();
		else
			SpringApplication.run(BpmnApplication.class, args);
	}

	private static void usage(){
		System.out.println("usage:\njava -jar <jar-file> <startNode> <endNode>");
	}


	@Profile("!test")
	@Bean
	public CommandLineRunner run(final XmlFetcher fetcher) {
		return (args) -> {
			log.trace("[run|in]");
			try {
				String xml = fetcher.fetch();
				Solver solver = new Solver();

				Optional<Deque<FlowNode>> path = solver.getPath(xml, args[0], args[1]);
				System.out.println( Solver.toString(path, args[0], args[1]) );

			} catch (Exception e) {
				log.error("[run]", e);
				System.exit(-1);
			} finally {
				log.trace("[run|out]");
			}
		};

	}

}
