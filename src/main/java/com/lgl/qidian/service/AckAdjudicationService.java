package com.lgl.qidian.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgl.qidian.entity.UserIdMessageDo;
import com.lgl.qidian.entity.WriterDo;
import com.lgl.qidian.mapper.AdjudicationWriterMapper;
import com.lgl.qidian.mapper.UserIdMessageMapper;
import com.lgl.qidian.mapper.WriterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;

/**
 * @auther 刘广林
 */
@Service
public class AckAdjudicationService {
    @Autowired
    UserIdMessageMapper userIdMessageMapper;

    @Autowired
    WriterMapper writerMapper;

    @Autowired
    AdjudicationWriterMapper adjudicationWriterMapper;

    public String service(String... agrs){
        if (agrs[0] == "ok"){
            UserIdMessageDo userIdMessageDo = new UserIdMessageDo();
            userIdMessageDo.setUserId(Long.valueOf(agrs[1]));
            userIdMessageDo.setMessageTitle("申请结果通知");
            userIdMessageDo.setMessageText("成功成为作者");
            userIdMessageDo.setDate(new Date(new java.util.Date().getTime()));
            userIdMessageMapper.insert(userIdMessageDo);
        }else {
            UserIdMessageDo userIdMessageDo = new UserIdMessageDo();
            userIdMessageDo.setUserId(Long.valueOf(agrs[1]));
            userIdMessageDo.setMessageTitle("申请结果通知");
            userIdMessageDo.setMessageText("申请不通过");
            userIdMessageDo.setDate(new Date(new java.util.Date().getTime()));
            userIdMessageMapper.insert(userIdMessageDo);
        }
        WriterDo writerDo = new WriterDo();
        writerDo.setUserId(Long.valueOf(agrs[1]));
        writerMapper.insert(writerDo);

        adjudicationWriterMapper.deleteByUserId(Long.valueOf(agrs[1]));
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("is_finish","yes");
        return JSON.toJSONString(hashMap);
    }
}
