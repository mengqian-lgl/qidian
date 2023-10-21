package com.lgl.qidian.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @auther 刘广林
 */
@Mapper
public interface UserIdBookShelfMapper {
    public int createTableByUserId(String userId);
}
