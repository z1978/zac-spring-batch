package com.zac.spring_batch.dao;

import com.zac.spring_batch.entity.t_details;
import org.apache.ibatis.jdbc.SQL;

public class t_detailsSqlProvider {

    public String insertSelective(t_details record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_details");
        
        if (record.getUser_id() != null) {
            sql.VALUES("user_id", "#{user_id,jdbcType=INTEGER}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=INTEGER}");
        }
        
        if (record.getAge() != null) {
            sql.VALUES("age", "#{age,jdbcType=INTEGER}");
        }
        
        if (record.getAddress() != null) {
            sql.VALUES("address", "#{address,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}