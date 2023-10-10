package com.lgl.qidian.service;

import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.mapper.UserMapper;
import com.lgl.qidian.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @auther 刘广林
 */
@Service
public class RegisterService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public final short role = 1;

    @Transactional(rollbackFor = Exception.class)
    public User insertUser(String username, String password){
        User user = new User();
        user.setUserName(username);
        user.setUserPassword(bCryptPasswordEncoder.encode(password));
        Timestamp create_time = new Timestamp(new java.util.Date().getTime());
        user.setCreateTime(create_time);
       try{
           //插入用户信息
           userMapper.insertUser(user);
           System.out.println("输出主键值为：" + user.getUserId());
           com.lgl.qidian.entity.web_security.User_Role user_role = new com.lgl.qidian.entity.web_security.User_Role();
           user_role.setUser_id(user.getUserId());
           //用户角色为user
           user_role.setRole_id(this.role);
           //插入用户对应的角色
           userRoleMapper.insertUserRole(user_role);
       }catch (Exception e){
           throw e;
       }
       return user;
    }
}
