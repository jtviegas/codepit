package org.aprestos.labs.apiclient;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@ComponentScan(basePackageClasses = { Bootstrap.class })
public class Bootstrap {

}
