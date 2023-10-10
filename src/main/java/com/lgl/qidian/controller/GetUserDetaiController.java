package com.lgl.qidian.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 刘广林
 */
@RestController
public class GetUserDetaiController {

//    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/getUserDetails")
    public String getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return JSON.toJSONString(authentication);
    }
}
