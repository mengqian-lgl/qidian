package com.lgl.qidian.service;

import com.alibaba.fastjson2.JSON;
import com.lgl.qidian.algorithm.IdWorker;
import com.lgl.qidian.entity.web_security.MyUserDetail;
import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.util.IdWorkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther 刘广林
 */
@Service
public class UserService {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("MyProviderManager")
    AuthenticationManager authenticationManager;

    public void service(User user, HttpServletResponse response) throws IOException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (authenticate == null){
            response.setContentType("application/json");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("登入失败，密码或账号错误".getBytes());

            outputStream.flush();
            outputStream.close();
            return;
        }
        System.out.println(authenticate);
        //登入成功，获得雪花id为key，将用户信息存入redis。 JSSEION为雪花id
        long flnowId = IdWorkerUtils.getFlnowId();
        response.addCookie(new Cookie("JSESSION",String.valueOf(flnowId)));
        MyUserDetail myUserDetail = (MyUserDetail) authenticate.getPrincipal();
        stringRedisTemplate.opsForValue().set(String.valueOf(flnowId), JSON.toJSONString(myUserDetail.getUser()));
        response.setContentType("application/json");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write("登入成功".getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
