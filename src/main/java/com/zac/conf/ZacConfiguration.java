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
@EnableBatchProcessing
@PropertySource({"classpath:zac-common.properties", "classpath:zac-env.properties"}) 
@MapperScan("com.zac.spring_batch.dao")
@EnableAutoConfiguration
public class ZacConfiguration {

	private static final Logger log = LoggerFactory.getLogger(ZacConfiguration.class);
	/*
     *  The following configuration code is commented out fix a problem that the batch framework start-up failure caused by the update of springframework dependency, from 4.1.7 to 4.2.4\
     *	The issue is because the different behaviour of the two versions while handling the case that it tries to create a bean definition, but it has already existed in the context.
     */

    @Bean
     public PlatformTransactionManager transactionManager(){
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
