package com.lgl.qidian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lgl.qidian.util.JwtUtils;
import io.lettuce.core.ScriptOutputType;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @auther 刘广林
 */
public class TestNoFollowSpring {


    private Object Class;

    //验证FastJson能序列化字段的修饰符
    //结论：只有public的才能序列化
    @Test
    public void testthking(){
        User user = new User();
        System.out.println(user);
//        user.grantedAuthorities.add(new SimpleGrantedAuthority("user"));
//        user.grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
//        System.out.println(JSON.toJSON(user));
        JSONObject o = (JSONObject) JSON.toJSON(user);
        User t = JSON.toJavaObject(o, User.class);
        System.out.println(t);
    }


    @Test
    public void testEnum(){
        System.out.println(use.REGISTER);
    }

    @Test
    public void testString(){
        String url = "/register/456";
        String[] split = url.split("/");
        for (String s : split) {
            System.out.println(s);
        }
        System.out.println(url.toUpperCase());
    }

    @Test
    public void textImage() throws IOException {

        File file = new File("C:/Users/ASUS/Desktop/fupo.jpg");
        BufferedImage read = ImageIO.read(file);
        int height = read.getHeight();
        int width = read.getWidth();
        

        //滑块的位置应该在高度的四分之一到四分之三的区域，
        //             宽度的四分一到四分之三的位置
        //滑块的面积应该占这个区域的16分之一  允许误差滑块面积的的五分之一取整
        //也就是说滑块占背景图总面积的64分之一  宽width/8  长height/8
        //随机初始x坐标
        int randomWidth = (int) (Math.random() * (width/8*3) + 1) + width/4;
        //随机初始y坐标
        int randomHeight = (int) (Math.random() * (height/8*3) + 1) + height/4;
        System.out.println(randomWidth  + " " + (1080/4) + " " + (1080/4*3));
        System.out.println(randomHeight +" " + (1418/4) + " " + (1418/4*3));
        System.out.println(width/8);
        System.out.println(height/8);
        BufferedImage subimage = read.getSubimage(randomWidth, randomHeight, width/8, height/8);
//        BufferedImage tihuanhou = new BufferedImage(read.getWidth(),read.getHeight(),
//                BufferedImage.TYPE_4BYTE_ABGR);


        //生成成品滑块
        ImageIO.write(subimage,"jpg",new File("C:/Users/ASUS/Desktop/fupo_chengping.jpg"));

        //生成被扣掉滑块的背景图，设置为白色
        for (int y = randomHeight; y < randomHeight + height/8; y++){
            for (int x = randomWidth; x < randomWidth + width/8; x++){
                read.setRGB(x,y,10);
            }
        }
        ImageIO.write(read,"jpg",new File("C:/Users/ASUS/Desktop/fupo_backg.jpg"));

    }

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


    //
    @Test
    public void testBuffredImage() throws IOException {
        BufferedImage readin = ImageIO.read(new File("C:/Users/ASUS/Desktop/fupo.jpg"));


        BufferedImage alphaBackgroup = new BufferedImage(readin.getWidth(), readin.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = alphaBackgroup.createGraphics();
        alphaBackgroup = graphics.getDeviceConfiguration().createCompatibleImage(readin.getWidth(), readin.getHeight(),
                Transparency.TRANSLUCENT);
        graphics.dispose();
        Graphics2D graphics1 = alphaBackgroup.createGraphics();
        readin.getSubimage(0, 0, 100, 100);
        graphics1.drawImage(readin, 0,0, null);

        ImageIO.write(alphaBackgroup, "jpg", new File("C:/Users/ASUS/Desktop/myAlpha.jpg"));
    }
    @Test
    public void test() {
        try {
            // 读取图片
            BufferedImage bi1 = ImageIO.read(new File("C:/Users/ASUS/Desktop/fupo.jpg"));
            BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
                    .getHeight());
            Graphics2D g2 = bi2.createGraphics();
            g2.setBackground(Color.WHITE);
            g2.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
            g2.setClip(shape);
            //设置抗锯齿
            g2.drawImage(bi1, 0, 0, null);
            g2.dispose();
            ImageIO.write(bi2, "jpg", new File("C:/Users/ASUS/Desktop/myAlpha.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void myslider() throws IOException {
        File file = new File("C:/Users/ASUS/Desktop/fupo.jpg");
        BufferedImage read = ImageIO.read(file);
        int height = read.getHeight();
        int width = read.getWidth();


        //滑块的位置应该在高度的四分之一到四分之三的区域，
        //             宽度的四分一到四分之三的位置
        //滑块的面积应该占这个区域的16分之一  允许误差滑块面积的的五分之一取整
        //也就是说滑块占背景图总面积的64分之一  宽width/8  长height/8
        //随机初始x坐标
        int randomWidth = (int) (Math.random() * (width/8*3) + 1) + width/4;
        //随机初始y坐标
        int randomHeight = (int) (Math.random() * (height/8*3) + 1) + height/4;
        System.out.println(randomWidth  + " " + (1080/4) + " " + (1080/4*3));
        System.out.println(randomHeight +" " + (1418/4) + " " + (1418/4*3));


    }

    @Test
    public void testUUID(){
        System.out.println(UUID.randomUUID());

    }

    @Test
    public void testTimeStamp(){
        System.out.println(new Timestamp(0));
    }

    @Test
    public void testStringIsNumber(){
        String one = "-4582";
        String two = "4566";
        String three = "sss";
        BigInteger bigInteger = new BigInteger(three);
        System.out.println(bigInteger.toString());
    }
    @Test
    public void testJWTPayload(){

    }

    @Test
    public void testCalendar() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());

        System.out.println(format);
    }


}
