package com.zac.spring_batch.dao;

import com.zac.spring_batch.entity.M_User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

public interface M_UserMapper {
    @Insert({
        "insert into m_user (user_id, first_name, ",
        "last_name)",
        "values (#{user_id,jdbcType=INTEGER}, #{first_name,jdbcType=VARCHAR}, ",
        "#{last_name,jdbcType=VARCHAR})"
    })
    int insert(M_User record);

    @InsertProvider(type=M_UserSqlProvider.class, method="insertSelective")
    int insertSelective(M_User record);
}