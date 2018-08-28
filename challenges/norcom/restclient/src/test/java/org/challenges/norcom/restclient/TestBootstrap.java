package org.challenges.norcom.restclient;

import java.util.concurrent.Executor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

@SpringBootApplication
public class TestBootstrap {

	// @Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(8);
		executor.setQueueCapacity(500);
		executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("common-pool-%d").setDaemon(true).build());
		executor.initialize();
		return executor;
	}

}
