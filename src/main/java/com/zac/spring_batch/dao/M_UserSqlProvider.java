package com.zac.spring_batch.dao;

import com.zac.spring_batch.entity.M_User;
import org.apache.ibatis.jdbc.SQL;

public class M_UserSqlProvider {

    public String insertSelective(M_User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("m_user");
        
        if (record.getUser_id() != null) {
            sql.VALUES("user_id", "#{user_id,jdbcType=INTEGER}");
        }
        
        if (record.getFirst_name() != null) {
            sql.VALUES("first_name", "#{first_name,jdbcType=VARCHAR}");
        }
        
        if (record.getLast_name() != null) {
            sql.VALUES("last_name", "#{last_name,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}