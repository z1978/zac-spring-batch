package com.zac.spring_batch.user_csv;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * 
 * @author zac
 * @version
 * @since 2.0.0
 */
@Getter
@Setter
@Component
public class UserCsvWriter extends JdbcBatchItemWriter<UserCsvBean> {
    private static final Logger logger = LoggerFactory.getLogger(UserCsvWriter.class);
//    @Override
//    public void write(List<? extends UserCsvBean> userCsvBean)
//            throws Exception {
//        // TODO Auto-generated method stub
//        logger.debug("---------- UserCsvWriter START ----------");
//        logger.debug("UserCsvWriter count = [" + userCsvBean.size() + "]");
//        
//        for (int i = 0; i < userCsvBean.size(); i++) {
////            logger.debug(userCsvBean.get(i);
//        }
//        logger.debug("---------- UserCsvWriter END ----------");
//    }
    @Autowired
    public DataSource dataSource;
    @Bean
    public JdbcBatchItemWriter<UserCsvBean> writer() {
        JdbcBatchItemWriter<UserCsvBean> writer = new JdbcBatchItemWriter<UserCsvBean>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<UserCsvBean>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }

}
