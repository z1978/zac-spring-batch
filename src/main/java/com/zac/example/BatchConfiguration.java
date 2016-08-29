package com.zac.example;


import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.zac.spring_batch.entity.m_user;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<m_user> reader() {
        FlatFileItemReader<m_user> reader = new FlatFileItemReader<m_user>();
        reader.setResource(new ClassPathResource("sample-data.csv"));

        reader.setLineMapper(new DefaultLineMapper<m_user>() {{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<m_user>() {{
                    setTargetType(m_user.class);
            }});
        }});

        /*
        // こっちの書き方の方がわかりやすいかも

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "firstName", "lastName" });
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<Person>();
        fieldSetMapper.setTargetType(Person.class);

        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);

        */
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<m_user> writer() {
        JdbcBatchItemWriter<m_user> writer = new JdbcBatchItemWriter<m_user>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<m_user>());
        writer.setSql("INSERT INTO people (user_id, first_name, last_name) VALUES (user_id:first_name, :last_name)");
        writer.setDataSource(dataSource);
        return writer;
    }

 
    @Bean
    public CsvItemProcessor processor() {
        return new CsvItemProcessor();
    }
    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener();
    }


    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<m_user, m_user> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}

