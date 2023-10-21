package com.lgl.qidian.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 刘广林
 */
@Component
@ServerEndpoint("/chat/{key}")
@Slf4j
public class WebSocket implements ApplicationContextAware {

    private static Map<String,Session> connectSocket = new ConcurrentHashMap<>();

    private static ApplicationContext context;
   @OnOpen()
    public void onOpen(@PathParam("key") String key, Session session ){
       System.out.println(key);
       connectSocket.put(key,session);
   }

   @OnMessage()
    public void onMessage(String message,Session session) throws IOException {

       System.out.println(message);
       System.out.println(session.getRequestURI());
       System.out.println(session.getId());
       session.getBasicRemote().sendText("菜鸟");
   }

   @OnClose()
    public void onClose(){
       System.out.println("连接关闭");

   }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
