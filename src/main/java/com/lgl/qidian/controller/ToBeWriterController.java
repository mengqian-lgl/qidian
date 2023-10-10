package com.lgl.qidian.controller;

import com.lgl.qidian.entity.tobe_writer_contoller.ToBeWriterBody;
import com.lgl.qidian.entity.web_security.MyUserDetail;
import com.lgl.qidian.mapper.UserDetailsMapper;
import com.lgl.qidian.service.ToBeWriterService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 刘广林
 */
@RestController
public class ToBeWriterController {

    @Autowired
    ToBeWriterService toBeWriterService;

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/tobewriter")
    public void toBeWriter(@RequestBody ToBeWriterBody body){
        //获取申请需要的信息
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = ((MyUserDetail)principal.getPrincipal()).getUser().getUserId();
        body.setUserId(userId);

        toBeWriterService.service(body);

    }


}
