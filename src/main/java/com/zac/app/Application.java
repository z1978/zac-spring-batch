package com.zac.app;


import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:zac-common.properties", "classpath:zac-env.properties"})
@ComponentScan({"com.zac"})
@EnableAutoConfiguration
public class Application {


    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
    	log.debug("Batch Framework starts.");
    	try {
        	ApplicationContext ctx = SpringApplication.run(Application.class, args);

        	//Environment env = ctx.getEnvironment();

        	//System.out.println("Let's inspect the properties provided by Spring Boot:");
        	//Map<String, Object> prop = getAllKnownProperties(env);
        	//for (Entry entry : prop.entrySet()) {
        	//    String parm = String.format("Key=%s, Value=%s", entry.getKey(), entry.getValue());
        	//    System.out.println(parm);
        	//}
        
        	//String loglevel = env.getProperty("log.level");
        	//System.out.println("loglevel = " + loglevel);
        	//String logcorelevel = env.getProperty("log.core.level");
        	//System.out.println("logcorelevel = " + logcorelevel);
        	//String enable = env.getProperty("spring.batch.job.enabled");
        	//System.out.println("enabled = " + enable);

        	log.trace("Let's inspect the beans provided by Spring Boot:");
        	String[] beanNames = ctx.getBeanDefinitionNames();
        	Arrays.sort(beanNames);
        	for (String beanName : beanNames) {
        	    log.debug(beanName);
        	}
//        	JobRegistry jobReg = (JobRegistry) ctx.getBean("jobRegistry");
//        	Job job = (Job) jobReg.getJob(args[0]);
//        	ApplicationContextFactory job = (ApplicationContextFactory) ctx.getBean(args[0]);
//        	Job job = (Job) ctx.getBean(args[0]);
//
//        	JobLauncher jobLch = (JobLauncher) ctx.getBean("jobLauncher");
//        	jobLch.run(job, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
        
        	CommandInterpreter ci = (CommandInterpreter)ctx.getBean("commandInterpreter");
        	ci.run(args);
    	} catch (Throwable t) { // NOSONAR
            // This is the Outter most part of the framework
            // We are trying our best to log something before the framework crash
            // Thus catching Errors which we cannot really handle and trys to log them
    		log.error("Fatal Error! Batch Framework aborts.", t);
    		throw t;
    	}
    	log.debug("Batch Framework ends.");
    	// Ensure that when the Framework ends, all threads ends
    	// If you want to have a long run service, you have to keep the main thread alive
    	System.exit(0);
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        c.setFileEncoding("UTF-8");
        return c;
    }

}
