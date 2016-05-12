package org.aprestos.labs.ee.datarepositories.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

/**
 * Bootstrap config class for datarepositories, client code must always include
 * this package to be "component-scanned"
 * 
 * @author jtv
 * 
 */
@Configuration
@ImportResource({ "classpath*:/datarepositories-config.xml" })
@PropertySource({ "classpath:/datarepositories.properties" })
@ComponentScan(basePackages = "org.aprestos.labs.ee.datarepositories.mongo")
public class Config {

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/*@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		
		return new SimpleMongoDbFactory(new MongoClient(), "yourdb");
	}
 
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
 
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
 
		return mongoTemplate;
 
	}*/

}
