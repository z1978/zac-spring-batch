package com.zac.spring_batch.user_csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;



/**
 * 
 * @author zac
 * @version
 * @since 2.0.0
 */


@Component
public class UserCsvReader extends FlatFileItemReader<UserCsvBean> {
    private static final Logger logger = LoggerFactory.getLogger(UserCsvReader.class);
    @Bean
    public FlatFileItemReader<UserCsvBean> reader() {
        FlatFileItemReader<UserCsvBean> reader = new FlatFileItemReader<UserCsvBean>();
        reader.setResource(new ClassPathResource("sample-data.csv"));

        reader.setLineMapper(new DefaultLineMapper<UserCsvBean>() {{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<UserCsvBean>() {{
                    setTargetType(UserCsvBean.class);
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
}
