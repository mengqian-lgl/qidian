package com.lgl.qidian;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @auther 刘广林
 */
public class MyMain2 {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleWithFixedDelay(()->{
            System.out.println("固定频率执行");
        },0,3,TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(()->{
            System.out.println("我使用的是");

        },0, 5,TimeUnit.SECONDS);
    }
}
