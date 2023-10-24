package com.lgl.qidian.controller;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.lgl.qidian.service.Test;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ContentHandler;
import java.util.Map;

/**
 * @auther 刘广林
 */
@RestController
public class TestController {

    /*
     *  @PathVariable 匹配rest风格  我只在get方法中使用成功过
     *  @RequestParams  传统 param
     *  @RequestBody    json
     *
     */

    @PostMapping("/123/{name}")
    public String test(HttpServletRequest request,@RequestParam String name2, @RequestBody String name1,
                       @PathVariable("name") String name){
        System.out.println("从rest中获取值："+ name);
//        System.out.println("从param中获取值："+ name2);
//        System.out.println("从json中获取值："+ name1);
        return "12";
    }


    @Autowired
    @Qualifier("serviceTest")
    Test test;

    @DeleteMapping("/123/{name}")
    public void test1(HttpServletRequest request,@PathVariable String name,@RequestBody Map map) throws IOException {
        System.out.println("从param中获取值："+request.getParameter("name"));
        System.out.println("从rest中获取值："+ name);
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getResponse();
        response.setHeader("test","test");
        response.setStatus(450);
        System.out.println(map.get("name"));
        test.test();
    }

    @PostMapping("/test/perall")
    public void testPerall(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();

    }

    class test{

        @JSONField()
        String name;

        @JSONField()
        String pass;
    }
}
