package com.zac.conf;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class ZacDataSourceConfig {

	private @Value("${zac.jdbc.driverClassName}") String driverClassName;
    private @Value("${zac.jdbc.url}") String url;
    private @Value("${zac.jdbc.username}") String userName;
    private @Value("${zac.jdbc.password}") String passWord;

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
