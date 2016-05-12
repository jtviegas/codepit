package org.aprestos.labs.snippets.impl.data.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath*:/spring-dataredis.xml" })
public class LocalRedisConfig {

}
