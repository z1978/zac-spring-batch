package com.zac.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * @author zac
 * @version
 * @since 2.0.0
 */
@Configuration
@EnableBatchProcessing(modular = true)
@PropertySource({ "classpath:jdbc.properties", "classpath:log4j.properties" })
@EnableAutoConfiguration
public class ZacConfiguration {

	private static final Logger log = LoggerFactory.getLogger(ZacConfiguration.class);

	// @Bean
	// public ApplicationContextFactory testConfig() {
	// return new GenericApplicationContextFactory(test.class);
	// }

	// Manual config Spring Batch
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public JobRepository jobRepository() {
		try {
			MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean();
			mapJobRepositoryFactoryBean.setTransactionManager(transactionManager());
			return mapJobRepositoryFactoryBean.getObject();
		} catch (Exception e) {
			log.error("Failed to init jobRepository!", e);
			return null;
		}
	}

	@Bean
	public JobLauncher jobLauncher() {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository());
		return simpleJobLauncher;
	}

	@Bean
	public JobRegistry jobRegistry() {
		return new MapJobRegistry();
	}

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry());
		return jobRegistryBeanPostProcessor;
	}

}
