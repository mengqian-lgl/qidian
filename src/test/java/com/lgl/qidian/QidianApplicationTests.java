package com.lgl.qidian;



import com.auth0.jwt.interfaces.DecodedJWT;
import com.lgl.qidian.util.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QidianApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Test
    public void testRedis(){
        System.out.println(stringRedisTemplate);
        stringRedisTemplate.opsForValue().set("刘广林","");
        String s = stringRedisTemplate.opsForValue().get("刘广林");
        System.out.println(s);
        System.out.println(stringRedisTemplate.delete("55"));
    }

    @Test
    public void testthking(){

    }


    //雪花算法 64位   19byte
    // UUID   32位   36byte
    // JWT生成的密匙长度对比  256位   109byte


    //验证转成父类或接口后，多态的方法内能不能拿到子类声明的变量

    //结论：多态的方法能拿到子类中声明的变量



    //装饰器模式实现

    //验证模板方法不讲子类转为父类时能否用父类方法 成功完成模板设计模式

    @Test
    public void testClassLoader(){
        InputStream fupo = this.getClass().getClassLoader().getResourceAsStream("fupo.jpg");
        System.out.println(fupo);
    }

    @Test
    public void testClass(){
        InputStream fupo = this.getClass().getResourceAsStream("fupo.jpg");
        System.out.println(fupo);
    }

    @Test
    public void testJwt(){
        String token = JwtUtils.generyToken(UUID.randomUUID().toString(), "123456", "key");
        DecodedJWT decodedJWT = JwtUtils.decodeJWT(token, "123456");
        String string = decodedJWT.getClaim("key").toString();
        System.out.println(string);
    }


    @Autowired
    RabbitTemplate rabbitTemplate;
    //测试消息队列的confirm return在主线程结束后，还会不会执行
    //结论，回调是在rabbitmq的线程池中启用一个线程，不用在发送的线程中等待
    @Test
    public void testRabbitmq() throws InterruptedException {
        CachingConnectionFactory connectionFactory = (CachingConnectionFactory)rabbitTemplate.getConnectionFactory();
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("我是comfirm回调方法id为"+ correlationData.getId() + ack);
            }
        });
        rabbitTemplate.send("admin","adjudication",
                new Message("我是消息呀，快开门".getBytes())
                );
        System.out.println("我是主线程");
    }
}
