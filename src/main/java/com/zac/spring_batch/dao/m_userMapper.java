package com.zac.spring_batch.dao;

import com.zac.spring_batch.entity.m_user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

public interface m_userMapper {
    @Insert({
        "insert into m_user (user_id, first_name, ",
        "last_name)",
        "values (#{user_id,jdbcType=INTEGER}, #{first_name,jdbcType=VARCHAR}, ",
        "#{last_name,jdbcType=VARCHAR})"
    })
    int insert(m_user record);

    @InsertProvider(type=m_userSqlProvider.class, method="insertSelective")
    int insertSelective(m_user record);
}