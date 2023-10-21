package com.lgl.qidian.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @auther 刘广林
 */
@Component
@ServerEndpoint("/test")
public class WebSocket2 {
    @OnOpen
    public void onOpen(Session session){
        System.out.println("我连接了");
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println(message);
    }
}
