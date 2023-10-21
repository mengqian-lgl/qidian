package com.lgl.qidian.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.mapper.UserIdBookShelfMapper;
import com.lgl.qidian.mapper.UserIdMessageMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther 刘广林
 */
@Aspect
@Component
public class RegisterServiceAfterAop {

    private Logger logger = LoggerFactory.getLogger(RegisterServiceAfterAop.class);

    @Pointcut(value = "execution(public void com.lgl.qidian.service.RegisterService.insertUser(..))")
    public void registerServicePoincut(){

    }


    @Autowired
    UserIdBookShelfMapper userIdBookShelfMapper;

    @Autowired
    UserIdMessageMapper userIdMessageMapper;

    @Around(value = "registerServicePoincut()")
    public void createBookShelfAndMessageTable(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = (User)joinPoint.proceed();
        userIdBookShelfMapper.createTableByUserId(String.valueOf(user.getUserId()));
        userIdMessageMapper.createTableByUserId(String.valueOf(user.getUserId()));

    }

}
