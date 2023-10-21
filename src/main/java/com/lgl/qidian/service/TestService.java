package com.lgl.qidian.service;

import com.lgl.qidian.entity.web_security.User_Role;
import com.lgl.qidian.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther 刘广林
 */
@Service
public class TestService {
    @Autowired
    UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    public User_Role test(){


        System.out.println("我是进入了testService");
        return new User_Role(4,(short)5);
    }
}
