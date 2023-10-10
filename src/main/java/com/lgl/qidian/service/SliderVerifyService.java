package com.lgl.qidian.service;

import com.alibaba.fastjson.JSON;
import com.lgl.qidian.config.jwt.JwtConfig;
import com.lgl.qidian.entity.use_slider.SliderXY;
import com.lgl.qidian.entity.web_state.RejectFactoryFast;
import com.lgl.qidian.util.JwtUtils;
import com.lgl.qidian.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @auther 刘广林
 */
@Service
public class SliderVerifyService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    JwtConfig jwtConfig;

    public void verifySlider(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //从请求中拿取值，为null或为空，结束请求
        String fromUrl = request.getParameter("fromUrl");
        if (fromUrl == null || fromUrl.isEmpty()){
            ResponseUtils.message(403,"未写fromUrl字段",response);
            return;
        }
        //从请求中拿取值，为null或为空，结束请求
        String x_indexString = request.getParameter("x_index");
        if (x_indexString == null || x_indexString.isEmpty()){
            ResponseUtils.message(403,"未写字段",response);
            return;
        }
        // 如果x_index不为纯数字的字符串结束请求
        try{
            // 大整数解析不了传入的string，说明这个字符串不是数字字符串
            BigInteger bigInteger = new BigInteger(x_indexString);

        }catch (NumberFormatException exception){
            ResponseUtils.message(403,"未写x_index字段不为纯数字",response);
            return;
        }
        //将字符串x_indexString 转变成 int类型 x_index
        int x_index = Integer.valueOf(x_indexString);


        //从请求中拿取值，为null或为空，结束请求
        String uuid = request.getParameter("uuid");
        if (uuid == null || uuid.isEmpty()){
            ResponseUtils.message(403,"未写uuid字段",response);
            return;
        }
        // uuid为key，从redis中拿取值。
        // 为null说明过期或uuid不争取，结束请求
        // 不为空，将拿到的json序列化为SliderXY对象
        String x_y_range = redisTemplate.opsForValue().get(uuid);
        SliderXY sliderXY = null;
        if (x_y_range == null){
            ResponseUtils.message(403,"uuid字段无效或过期",response);
            return;
        }else {
            sliderXY = JSON.parseObject(x_y_range, SliderXY.class);
        }

        // 判断x坐标是否在可允许的偏差范围内
        int x_max = Integer.valueOf(sliderXY.getX()) + Integer.valueOf(sliderXY.getRange());
        int x_min = Integer.valueOf(sliderXY.getX()) - Integer.valueOf(sliderXY.getRange());
        // 校验的x坐标不满足条件
        if (! (x_min <= x_index && x_index <= x_max) ){
            ResponseUtils.message(403,"x坐标校验不通过",response);
            return;
        }
        //
        String token_key = fromUrl.substring(1).toUpperCase() + "_TOKEN";
        String to_uuid = UUID.randomUUID().toString();
        String token = JwtUtils.generyToken(to_uuid, jwtConfig.getSecret(), token_key);
        redisTemplate.opsForValue().set(to_uuid, "", 1, TimeUnit.DAYS);
        //删除存储着对应滑块坐标信息的缓存
        redisTemplate.delete(uuid);
        PrintWriter writer = response.getWriter();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(token_key, token);
        writer.write(JSON.toJSONString(hashMap));
        writer.flush();
        writer.close();
    }
}
