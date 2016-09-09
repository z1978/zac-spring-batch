package com.zac.conf;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan("com.zac.spring_batch.dao")
public class DataSourceConfig {


    @Value("${zac.test.jdbc.driverClassName}")
    private String driverClassName;
    @Value("${zac.test.jdbc.url}")
    private String url;
    @Value("${zac.test.jdbc.username}")
    private String userName;
    @Value("${zac.test.jdbc.password}")
    private String passWord;

    /**
     * 
     * @param dataSource
     * @return
     */
    @Bean(destroyMethod="close")
    public BasicDataSource basicDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);       
        return dataSource;
    }
   
    @Bean()
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(basicDataSource());
        return bean;
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionFactoryTemplate() throws Exception {
        return new SqlSessionTemplate(
                sqlSessionFactoryBean().getObject(), 
                ExecutorType.BATCH
                );
    }

    @Bean
     public DataSourceTransactionManager dataSourceTransactionManager(){
        return new DataSourceTransactionManager(basicDataSource());
     }
    

}
