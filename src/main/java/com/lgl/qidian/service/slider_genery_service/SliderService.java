package com.lgl.qidian.service.slider_genery_service;

import com.alibaba.fastjson.JSON;
import com.lgl.qidian.entity.use_slider.SliderXY;
import com.lgl.qidian.interceptor.SliderInterceptor;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @auther 刘广林
 */
@Service
// todo 导入日志
public class SliderService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * todo 看能不能在容器启动时读取到图片资源
     */
    static String[] backgroups = new String[]{"fupo.jpg"};

    static Random random = new Random();

    //用来将slider方法中生成的y坐标转移出来
    int y;

    //用来将slider方法中生成的uuid转移出来
    String uuid;


    /**
     *  todo base64压缩编码还没实现 已经实现
     * @param response
     * @throws IOException
     */
    @Transactional(rollbackFor = Exception.class)
    public void backTwoImageAndEncodeBybase64(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage backgroup = null;
        BufferedImage slider = null;
        try{
            //随机生成backgroups的下标
            //random.nextInt(X) 生成的数字是0 - （X-1） 不包含X
            int indexBackgroups = random.nextInt(backgroups.length);
            backgroup = getBackgroup(backgroups[indexBackgroups]);
            //获取滑块图片，并将backgroup图片进行抠图，将抠图的左上角坐标存入redis
            slider = getSlider(backgroup);
        }catch (IOException ioException){
            // 输出日志，还没导入日志的包
        }

        HashMap<String,String> body = new HashMap<>();
        body.put("toUrl", "/sliderverify");
        body.put("backgroup",bufferedToOutputToByteToString(backgroup));
        body.put("slider",bufferedToOutputToByteToString(slider));
        body.put("y_index",String.valueOf(this.y));
        body.put("this.url",request.getRequestURI());
        body.put("uuid",this.uuid);
        String bodyJson = JSON.toJSONString(body);
        PrintWriter writer = response.getWriter();
        writer.write(bodyJson);
        writer.flush();
        writer.close();

    }

    private BufferedImage getBackgroup(String classPath) throws IOException {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(classPath);
        BufferedImage readFromClassPath = ImageIO.read(resourceAsStream);

        //调整获得的图片大小
        BufferedImage bufferedImage = new BufferedImage(460, 165, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(readFromClassPath,0,0,460,165,null);
        graphics.dispose();

        return  bufferedImage;
    }

    //截取图片
    //坐标点随机生成
    //x 范围（四分之一背景图宽） ---  （四分之三背景图宽 - 六分之一背景图宽）
    //y 范围（四分之一背景图高） ---  （四分之三背景图高 - 六分之一背景图高）
    //宽高，为背景图宽高的六分之一
    private BufferedImage getSlider(BufferedImage backgroups){
        int xRange_min = backgroups.getWidth()/4;
        int xRange_max = backgroups.getWidth()/4 * 3 - backgroups.getWidth()/6;
        int yRange_min = backgroups.getHeight()/4;
        int yRange_max = backgroups.getHeight()/4 * 3 - backgroups.getHeight()/6;

        //随机生成一个在范围内浮动的数字
        int x = random.nextInt(xRange_max - xRange_min) + xRange_min;
        int y = random.nextInt(yRange_max - yRange_min) + yRange_min;
        this.y = y;

        // todo 记得删除这行
        System.out.println("生成的滑块x坐标为: " + x);
        //将坐标写入redis
        String sliderJson = JSON.toJSONString(new SliderXY(String.valueOf(x), String.valueOf(y)));
        this.uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(this.uuid, sliderJson, 1, TimeUnit.DAYS);

        //滑块
        BufferedImage slider = backgroups.getSubimage(x, y, backgroups.getWidth() / 6, backgroups.getHeight() / 6);

        //背景图进行抠图
        for(int back_y = y; back_y < y + backgroups.getHeight()/6; back_y++){
            for (int back_x = x; back_x < x + backgroups.getWidth()/6; back_x++){
                backgroups.setRGB(x,y,0);
            }
        }

        return slider;
    }

    public String bufferedToOutputToByteToString(BufferedImage src) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(src, "jpg", outputStream);
        return  encoder.encodeToString(outputStream.toByteArray());
    }
}
