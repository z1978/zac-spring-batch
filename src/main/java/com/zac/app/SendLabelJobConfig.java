package com.zac.app;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcabi.aspects.Loggable;


@Configuration
@EnableAutoConfiguration
@Loggable(value = Loggable.TRACE, prepend = true)
public class SendLabelJobConfig {

	
	
	@Bean
	@Autowired
	public Job sendLabelJob(JobBuilderFactory jobs, 
			@Qualifier("sendLabelStep") Step sendLabelStep) {
		
		return jobs.get("sendLabelJob").incrementer(new RunIdIncrementer())				
				.start(sendLabelStep)
				.build();
	}

}
