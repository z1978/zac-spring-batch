package com.zac.spring_batch.dao;

import com.zac.spring_batch.entity.t_details;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

public interface t_detailsMapper {
    @Insert({
        "insert into t_details (user_id, sex, ",
        "age, address)",
        "values (#{user_id,jdbcType=INTEGER}, #{sex,jdbcType=INTEGER}, ",
        "#{age,jdbcType=INTEGER}, #{address,jdbcType=VARCHAR})"
    })
    int insert(t_details record);

    @InsertProvider(type=t_detailsSqlProvider.class, method="insertSelective")
    int insertSelective(t_details record);
}