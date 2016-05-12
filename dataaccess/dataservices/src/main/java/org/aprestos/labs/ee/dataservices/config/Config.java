package org.aprestos.labs.ee.dataservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Bootstrap config class for dataservices, client code must always include
 * this package to be "component-scanned"
 * 
 * @author jtv
 * 
 */
@Configuration
// import data repositories Configuration
@Import({org.aprestos.labs.ee.datarepositories.config.Config.class})
@ImportResource({ "classpath*:/dataservices-config.xml" })
@PropertySource({ "classpath:/dataservices.properties" })
@ComponentScan(basePackages = "org.aprestos.labs.ee.dataservices.services")
public class Config {

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}


}
