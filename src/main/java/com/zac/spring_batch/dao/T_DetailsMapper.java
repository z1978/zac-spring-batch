package com.zac.spring_batch.dao;

import com.zac.spring_batch.entity.T_Details;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

public interface T_DetailsMapper {
    @Insert({
        "insert into t_details (user_id, sex, ",
        "age, address)",
        "values (#{user_id,jdbcType=INTEGER}, #{sex,jdbcType=INTEGER}, ",
        "#{age,jdbcType=INTEGER}, #{address,jdbcType=VARCHAR})"
    })
    int insert(T_Details record);

    @InsertProvider(type=T_DetailsSqlProvider.class, method="insertSelective")
    int insertSelective(T_Details record);
}