package org.aprestos.labs.tests.caching.engines.neo4j.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Bootstrap config class for datarepositories, client code must always include
 * this package to be "component-scanned"
 * 
 * @author jtv
 * 
 */
@Configuration
@ImportResource({ "classpath*:**/neo4j-config.xml" })
@ComponentScan(basePackages = "org.aprestos.labs.tests.caching.engines.neo4j")
public class LocalNeo4jConfig {


}
