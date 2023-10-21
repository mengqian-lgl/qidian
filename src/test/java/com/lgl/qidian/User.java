package com.lgl.qidian;

import com.lgl.qidian.entity.web_security.User_Role;
import com.lgl.qidian.mapper.UserRoleMapper;
import com.lgl.qidian.other.son;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @auther 刘广林
 */

@Component
public class User {

    public String userId = "123";
    public String password = "123";
    public Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
    private son so = new son(){
        @Override
        protected void test() {
            super.test();
        }
    };

    @Autowired
    UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = ArithmeticException.class)
    public void transactional(@Nullable Object o) throws Exception{

        userRoleMapper.insertUserRole(new User_Role(5, (short) 0));
        int i = 4 / 0;

    }
    private static Logger logger = LoggerFactory.getLogger(User.class);
    public static void T() throws Exception {
        logger.warn("{}信息应该是不打印的","warn");
    }

    public static void main(String[] args) throws Exception {
        T();
    }
}
