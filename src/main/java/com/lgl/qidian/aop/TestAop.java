package com.lgl.qidian.aop;

import com.lgl.qidian.entity.web_security.User_Role;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @auther 刘广林
 */
@Aspect
@Component
public class TestAop {

    @Pointcut(value = "execution(public * com.lgl.qidian.service.TestService.*(..))")
    public void testPoint(){}


    @Around(value = "testPoint()")
    public void testAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.getSignature());
        System.out.println("13456");
        System.out.println(joinPoint.getTarget().getClass().getName());
        User_Role proceed = (User_Role)joinPoint.proceed();
        System.out.println(proceed);
    }
}
