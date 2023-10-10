package com.lgl.qidian.config.web;

import com.lgl.qidian.entity.use_slider.UseSliderInterceptorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther 刘广林
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("sliderInterceptor")
    HandlerInterceptor sliderInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //获得所有需要装载sliderInterceptor的接口路径
        //枚举类
        UseSliderInterceptorEntity[] values = UseSliderInterceptorEntity.values();

        //将枚举类的值存入List集合
        List<String> load_slider_url = new ArrayList<>();
        for (UseSliderInterceptorEntity url : values){
            load_slider_url.add(url.toString());
        }

        //注册拦截器到相应的url
        registry.addInterceptor(sliderInterceptor).addPathPatterns(load_slider_url);
    }
}
