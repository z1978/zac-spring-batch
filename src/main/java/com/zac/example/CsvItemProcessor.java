package com.zac.example;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.zac.spring_batch.entity.m_user;

/**
 * ItemProcessor类。
 */
@Component("csvItemProcessor")
public class CsvItemProcessor implements ItemProcessor<m_user, m_user> {

    /**
     * 对取到的数据进行简单的处理。
     * 
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    @Override
    public m_user process(m_user user) throws Exception {
        /* 合并ID和名字 */
    	user.setFirst_name(user.getFirst_name());

        /* 将处理后的结果传递给writer */
        return user;
    }
}
