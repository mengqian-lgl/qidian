package com.lgl.qidian.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther 刘广林
 */
@Configuration
public class RabbitmqConfig {
    //创建adjudication队列  队列绑定到admin交换机
    @Bean("adjudicationQueue")
    public Queue adjudicationQueue(){
        return QueueBuilder.durable("adjudication").build();
    }

    @Bean("adminExchange")
    public DirectExchange adminExchange(){
        return ExchangeBuilder.directExchange("admin").durable(true).build();
    }

    @Bean
    public Binding bindingAdmin_Adjudication(){
        return BindingBuilder.bind(adjudicationQueue()).to(adminExchange())
                .with("adjudication");
    }

}
