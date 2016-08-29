package com.zac.example;

import java.util.List;

import com.zac.spring_batch.dao.m_userMapper;
import com.zac.spring_batch.dao.m_userSqlProvider;
import com.zac.spring_batch.entity.m_user;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


/**
 * Dummy {@link ItemWriter} which only logs data it receives.
 */
@Component("writer")
public class ExampleItemWriter extends SqlSessionDaoSupport implements ItemWriter<Object> {

     private static final Log log = LogFactory.getLog(ExampleItemWriter.class);

     private m_userMapper userMapper;

     /**
      * @see ItemWriter#write(Object)
      */
     public void write(List<? extends Object> data) throws Exception {
          SqlSession session = getSqlSession();
          userMapper = session.getMapper(m_userMapper.class);
//          m_user rank = userMapper.(data.get(0).toString());
//          log.info(data);
//          log.info(user.getName());
     }

}
