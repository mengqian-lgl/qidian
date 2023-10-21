package com.lgl.qidian.mapper;

import com.lgl.qidian.entity.UserIdMessageDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * @auther 刘广林
 */
@Mapper
public interface UserIdMessageMapper {
    public int createTableByUserId(String userId);

    public int insert(UserIdMessageDo userIdMessageDo);
}
