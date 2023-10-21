package com.lgl.qidian.util;

import com.lgl.qidian.entity.web_security.User_Role;
import com.lgl.qidian.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @auther 刘广林
 */
@Component
public class test {
    @Autowired
    UserRoleMapper userRoleMapper;
    @Transactional(rollbackFor = ArithmeticException.class, timeout = 10)
    public void testUpdate(){
        try{
            System.out.println("我是updata排他锁后的结果"+userRoleMapper.selectRoleByUserIdUpdate((long) 4));
            Thread.sleep(5000);
            System.out.println("停了五秒");
        }catch (Exception e){
            System.out.println("我是排他锁的异常");

        }

    }


    @Transactional(rollbackFor = ArithmeticException.class, timeout = 5)
    public void transactional(){

        try{
            System.out.println("我是共享锁的结果"+userRoleMapper.selectRoleByUserId((long) 4));
        }catch (Exception e){
            System.out.println("我是共享锁的异常");
        }

//        try { userRoleMapper.insertUserRole(new User_Role(5, (short) 0));
//            int i = 4 / 0;
//        }catch (Exception e){
//            e.printStackTrace();
//            String message = e.getMessage();
//            System.out.println(message);
//        }
    }

    public void T() throws Exception {
        transactional();
    }
}
