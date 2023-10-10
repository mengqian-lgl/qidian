package com.lgl.qidian.service;

import com.alibaba.fastjson.JSON;
import com.lgl.qidian.controller.ToBeWriterController;
import com.lgl.qidian.entity.tobe_writer_contoller.ToBeWriterBody;
import com.lgl.qidian.mapper.UserDetailsMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther 刘广林
 */
@Service
public class ToBeWriterService {

    @Autowired
    UserDetailsMapper userDetailsMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

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


            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack == false){

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
            @Override
            public void returnedMessage(ReturnedMessage returned) {

            }
        });
        rabbitTemplate.send("admin","adjudication",
                new Message(String.valueOf(body.getUserId()).getBytes()),
                //传递用户id，方便申请失败时查找对应的用户消息表
                new CorrelationData(String.valueOf(body.getUserId())));



    }
}
