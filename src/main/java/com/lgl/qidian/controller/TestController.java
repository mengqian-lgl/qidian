package com.lgl.qidian.controller;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/123/{name}")
    public String test1(HttpServletRequest request,@PathVariable String name){
        System.out.println("从param中获取值："+request.getParameter("name"));
        System.out.println("从rest中获取值："+ name);
        return "12";
    }

}
