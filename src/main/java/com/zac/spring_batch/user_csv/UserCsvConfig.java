package com.zac.spring_batch.user_csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author zac
 * @version
 * @since 2.0.0
 */

@Configuration
public class UserCsvConfig {
    private static final Logger logger = LoggerFactory.getLogger(UserCsvConfig.class);
    private @Autowired @Qualifier("userCsvStep") Step userCsvStep;
    
    @Bean
    public Job userCsvJob(JobBuilderFactory jobs) {
        logger.debug("---------- userCsvJob ----------");
        return jobs.get("userCsvJob")
                .incrementer(new RunIdIncrementer())
                .flow(userCsvStep)
                .end()
                .build();
    }
    
    @Bean
    public Step userCsvStep(StepBuilderFactory stepBuilderFactory, 
            UserCsvProcessor processor, UserCsvReader reader, UserCsvWriter writer) {
        return stepBuilderFactory
                .get("userCsvStep")
                .<UserCsvBean , UserCsvBean> chunk(10)
                .reader(reader)
                .processor(processor)
//                .writer(writer)
                .build();
    }
}
