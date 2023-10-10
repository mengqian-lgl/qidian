package com.lgl.qidian.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lgl.qidian.config.jwt.JwtConfig;
import com.lgl.qidian.entity.web_state.RejectFactoryFast;
import com.lgl.qidian.service.slider_genery_service.SliderService;
import com.lgl.qidian.util.JwtUtils;
import com.lgl.qidian.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

/**
 * @auther 刘广林
 */
//是否进行滑块验证的拦截器
@Component("sliderInterceptor")
public class SliderInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    SliderService sliderService;

    @Autowired
    JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的url，对url进行处理，进而获得这个请求所携带的token的名称
        //token名称应该是有格式的
        //例如  /register  ----》 REGISTER_TOKEN
        //     /register/ababa ----》REGISTER_ABABA_TOKEN
        String requestURI = request.getRequestURI();
        String[] split = requestURI.split("/");
        String tokeName = "";
        for (String part : split) {
            if (part.isEmpty()){ continue;}
            tokeName += part.toUpperCase() + "_";
        }
        tokeName += "TOKEN";

        //尝试获得对应的JWT
        String token = (String) request.getParameter(tokeName);
        //没有JWT属性值或者为空，进入获取背景图，滑块图等的处理
        if (token == null || token.isBlank()){
            //请求在这个方法里面结束,响应携带两个编码图片返回，一张是底图，一张是滑块图
            sliderService.backTwoImageAndEncodeBybase64(request,response);
            return false;
        }
        String uuid = null;
        //解析token，报错则解析失败，结束请求
        try{
            DecodedJWT decodedJWT = JwtUtils.decodeJWT(token, jwtConfig.getSecret());
            System.out.println(decodedJWT.getClaim(tokeName).toString());
            uuid = decodedJWT.getClaim(tokeName).asString();
        }catch (Exception e){
            ResponseUtils.message(new RejectFactoryFast().createWebstate(), response);
            return false;
        }
        //redis使用uuid作为key 设置了 ”“ 为值
        String emptyString = stringRedisTemplate.opsForValue().get(uuid);
        //如果为null 表示key过期
        if (emptyString == null){
            ResponseUtils.message(new RejectFactoryFast().createWebstate(), response);
            return false;
        }
        //获取完后删除reids中验证成功对应的uuid缓存
        stringRedisTemplate.delete(uuid);
        return true;
    }
}
