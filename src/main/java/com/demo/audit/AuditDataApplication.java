package com.demo.audit;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableJpaAuditing
public class AuditDataApplication {//implements ApplicationRunner  {
	
	 
	public static void main(String[] args) {
		SpringApplication.run(AuditDataApplication.class, args);
	}

	
	@Bean(name="enrichThreadPoolTaskExecutorBean")
	public Executor getAsysncExceutor() {
		int corePoolSize=10;
		int maxPoolSize=100;
		ThreadPoolTaskExecutor executor= new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setThreadNamePrefix("enrich-");
		executor.initialize();
		return executor;
	}

}
