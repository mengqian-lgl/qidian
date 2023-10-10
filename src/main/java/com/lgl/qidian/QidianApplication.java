package com.lgl.qidian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class QidianApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(QidianApplication.class, args);
        System.out.println("111");
    }

}