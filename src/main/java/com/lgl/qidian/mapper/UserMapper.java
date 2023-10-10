package com.lgl.qidian.mapper;

import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.entity.web_security.User_Role;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

/**
 * @auther 刘广林
 */
@Mapper
public interface UserMapper {
    public User selectUserByUserId(Long userId);

    public int insertUser(User user);


}
