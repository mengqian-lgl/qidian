package com.lgl.qidian.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgl.qidian.entity.UserDetailsDo;
import com.lgl.qidian.entity.tobe_writer_contoller.AdjudicationWriterDo;
import com.lgl.qidian.entity.web_security.User;
import com.lgl.qidian.mapper.AdjudicationWriterMapper;
import com.lgl.qidian.mapper.UserDetailsMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @auther 刘广林
 */
@Service
public class GetAdjudicationService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserDetailsMapper userDetailsMapper;

    @Autowired
    AdjudicationWriterMapper adjudicationWriterMapper;

    public String service() throws JsonProcessingException {
        Message adjudicationQueue = rabbitTemplate.receive("adjudicationQueue");
        String userId = new String(adjudicationQueue.getBody());
        UserDetailsDo userDetailsDo = userDetailsMapper.selectByUserId(Long.valueOf(userId));
        String string = new ObjectMapper().writeValueAsString(userDetailsDo);

        //填写adjudication_writer表的admin字段
        AdjudicationWriterDo adjudicationWriterDo = new AdjudicationWriterDo();
        adjudicationWriterDo.setUserId(Long.valueOf(userId));
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        adjudicationWriterDo.setAdminId(principal.getUserId());
        adjudicationWriterMapper.update(adjudicationWriterDo);
        return string;
    }
}
