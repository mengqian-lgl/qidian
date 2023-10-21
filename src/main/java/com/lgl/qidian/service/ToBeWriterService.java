package com.lgl.qidian.service;

import com.lgl.qidian.entity.UserIdMessageDo;
import com.lgl.qidian.entity.tobe_writer_contoller.AdjudicationWriterDo;
import com.lgl.qidian.entity.tobe_writer_contoller.ToBeWriterBody;
import com.lgl.qidian.mapper.AdjudicationWriterMapper;
import com.lgl.qidian.mapper.UserDetailsMapper;
import com.lgl.qidian.mapper.UserIdMessageMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

/**
 * @auther 刘广林
 */
@Service
public class ToBeWriterService {

    @Autowired
    UserDetailsMapper userDetailsMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AdjudicationWriterMapper adjudicationWriterMapper;


    @Transactional(rollbackFor = Exception.class)
    public void service(ToBeWriterBody body) {
        //将信息写入表中，等待审核员审核
        userDetailsMapper.insertUserDetails(body);


        //申请消息传递给审核员
        //开启确保到达交换机的confirm无论失败成功程序domfirmCallback都会执行
        //开启并设置confirm回调类型，设置关联CorrelationData类型的回调
        CachingConnectionFactory connectionFactory = (CachingConnectionFactory)rabbitTemplate.getConnectionFactory();
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        //设置confirm回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Autowired
            AdjudicationWriterMapper adjudicationWriterMapper;

            @Autowired
            UserIdMessageMapper userIdMessageMapper;

            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                //如果消息丢失，消息应该从申请列表中删除，并发送消息到用户消息列表
                if (ack == false){
                    adjudicationWriterMapper.deleteByUserId(Long.valueOf(correlationData.getId()));
                    UserIdMessageDo userIdMessageDo = new UserIdMessageDo();
                    userIdMessageDo.setDate(new Date(new java.util.Date().getTime()));
                    userIdMessageDo.setUserId(Long.valueOf(correlationData.getId()));
                    userIdMessageDo.setMessageTitle("申请丢失，请重新申请");
                    userIdMessageDo.setMessageText("申请丢失，请重新申请");
                    userIdMessageMapper.insert(userIdMessageDo);
                }
            }
        });

        //设置return确保消息到达队列，失败时callback
        //开启return回调
        connectionFactory.setPublisherReturns(true);
        //强制失败时执行回调
        rabbitTemplate.setMandatory(true);
        //设置return回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Autowired
            AdjudicationWriterMapper adjudicationWriterMapper;

            @Autowired
            UserIdMessageMapper userIdMessageMapper;

            @Override
            public void returnedMessage(ReturnedMessage returned) {
                adjudicationWriterMapper.deleteByUserId(Long.valueOf(new String(returned.getMessage().getBody())));
                UserIdMessageDo userIdMessageDo = new UserIdMessageDo();
                userIdMessageDo.setDate(new Date(new java.util.Date().getTime()));
                userIdMessageDo.setUserId(Long.valueOf(new String(returned.getMessage().getBody())));
                userIdMessageDo.setMessageTitle("申请丢失，请重新申请");
                userIdMessageDo.setMessageText("申请丢失，请重新申请");
                userIdMessageMapper.insert(userIdMessageDo);
            }
        });
        rabbitTemplate.send("admin","adjudication",
                new Message(String.valueOf(body.getUserId()).getBytes()),
                //传递用户id，方便申请失败时查找对应的用户消息表
                new CorrelationData(String.valueOf(body.getUserId())));

        //将等级申请消息写入adjudicatioon_writer表
        AdjudicationWriterDo adjudicationWriterDo = new AdjudicationWriterDo();
        adjudicationWriterDo.setUserId(body.getUserId());
        adjudicationWriterDo.setIsAck("false");
        adjudicationWriterMapper.insertAdjudicationWriter(adjudicationWriterDo);


    }
}
