package com.lgl.qidian.mapper;

import com.lgl.qidian.entity.web_security.User_Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther 刘广林
 */
@Mapper
public interface UserRoleMapper {
    public int insertUserRole(User_Role user_role);

    public List<User_Role> selectRoleByUserId(Long userId);
}
