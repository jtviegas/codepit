package org.aprestos.labs.tests.caching.engines.mongo.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Bootstrap config class for datarepositories, client code must always include
 * this package to be "component-scanned"
 * 
 * @author jtv
 * 
 */
@Configuration
//@EnableJpaRepositories(basePackages={"org.aprestos.labs.tests.caching.engines.mongo.repositories"})
@ImportResource({ "classpath*:**/mongo-config.xml" })
@ComponentScan(basePackages = "org.aprestos.labs.tests.caching.engines.mongo")
public class LocalMongoConfig {
	
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
