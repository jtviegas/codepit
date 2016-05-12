package org.aprestos.labs.tests.caching.engines.hashmap.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bootstrap config class for datarepositories, client code must always include
 * this package to be "component-scanned"
 * 
 * @author jtv
 * 
 */
@Configuration
@ComponentScan(basePackages = "org.aprestos.labs.tests.caching.engines.hashmap")
public class LocalHashMapConfig { }
