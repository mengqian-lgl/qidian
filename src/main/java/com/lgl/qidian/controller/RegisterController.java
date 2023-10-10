package com.lgl.qidian.controller;

import com.alibaba.fastjson.JSON;
import com.lgl.qidian.algorithm.IdWorker;
import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @auther 刘广林
 */
@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public void register(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // todo 待实现从用户空余id池中获得 id


        //注册服务
        User user = registerService.insertUser(request.getParameter("userName"), request.getParameter("userPassword"));

        //根据雪花算法生成id 存入redis  key：雪花算法id  value：user
        IdWorker idWorker = new IdWorker(1, 1);
        long l = idWorker.getNextId();
        String userJson = JSON.toJSONString(user);
        stringRedisTemplate.opsForValue().set(String.valueOf(l),userJson,2, TimeUnit.DAYS);
        System.out.println(userJson);
        response.addCookie(new Cookie("JSESSION", String.valueOf(l)));

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("账号为",String.valueOf(user.getUserId()));
        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSON.toJSONString(hashMap).getBytes());
        outputStream.flush();
        outputStream.close();

//        PrintWriter writer = response.getWriter();
//       writer.write(JSON.toJSONString(hashMap));
//        writer.flush();
//        writer.close();
    }

    class Username_Password{
        public String userName;
        public String userPassword;

        public String getPassword() {
            return userPassword;
        }

        public void setPassword(String password) {
            this.userPassword = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
