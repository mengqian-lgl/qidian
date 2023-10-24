package com.lgl.qidian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @auther 刘广林
 */
public class MyMain {
//    public static void main(String[] args) {
//        Timer timer = new Timer();
//        TimerTask timerTask1 = new TimerTask(){
//
//            @Override
//            public void run() {
//
//                System.out.println("定时任务，1秒");
//            }
//        };
//        TimerTask timerTask2 = new TimerTask(){
//
//            @Override
//            public void run() {
//                int i = 2/0;
//                System.out.println("定时任务，2秒");
//            }
//        };
//        timer.schedule(timerTask1, 1000, 2000);
//        timer.schedule(timerTask2,2000,2000);

//    }
        public static void main(String[] args) {
            test test1 = new test();
            test1.name = "1";
            test1.pass = "2";
            String s = JSON.toJSONString(test1);
            System.out.println(s);

        }
    static class test{

        @JSONField(ordinal = 2,defaultValue = "lgl")
        String name;

        @JSONField(ordinal = 1, defaultValue = "153")
        String pass;
    }
}
