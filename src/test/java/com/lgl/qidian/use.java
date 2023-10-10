package com.lgl.qidian;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
/**
 * @auther 刘广林
 */
public enum use {
        REGISTER("/register");

        private use(String name) {
            this.name = name;
        }

    @Override
    public String toString() {
        return this.name;
    }

    private String name;
}


 class ImageUtil {

    public static void cut(int x,int y,int width,int height,String srcpath,String subpath) throws IOException {//裁剪方法
        FileInputStream is=null;
        ImageInputStream iis=null;
        try{
            is=new FileInputStream(srcpath); //读取原始图片
            Iterator<ImageReader> it= ImageIO.getImageReadersByFormatName("jpg"); //ImageReader声称能够解码指定格式
            ImageReader reader=it.next();
            iis=ImageIO.createImageInputStream(is); //获取图片流
            reader.setInput(iis, true); //将iis标记为true（只向前搜索）意味着包含在输入源中的图像将只按顺序读取
            ImageReadParam param=reader.getDefaultReadParam(); //指定如何在输入时从 Java Image I/O框架的上下文中的流转换一幅图像或一组图像
            Rectangle rect=new Rectangle(x, y, width, height); //定义空间中的一个区域
            param.setSourceRegion(rect); //提供一个 BufferedImage，将其用作解码像素数据的目标。
            BufferedImage bi=reader.read(0, param); //读取索引imageIndex指定的对象
            ImageIO.write(bi, "jpg", new File(subpath)); //保存新图片
        }finally{
            if(is!= null)
                is.close();
            if(iis != null)
                iis.close();
        }
    }

    public void cutByTemplate2(BufferedImage oriImage,BufferedImage newSrc,BufferedImage newSrc2,int x,int y,int width,int height, int c_a, int c2_b){
        //固定圆半径为5
        int c_r=10;
        double rr=Math.pow(c_r, 2);//r平方
        //圆心的位置 cb
        //System.out.println(c_a);
        int c_b=y;

        //第二个圆（排除圆内的点） c2_a
        int c2_a=x;

        //System.out.println(oriImage.getWidth()+"   "+oriImage.getHeight());
        for(int i=0;i<oriImage.getWidth();i++){
            for(int j=0;j<oriImage.getHeight();j++){
                //data[i][j]=oriImage.getRGB(i,j);

                //(x-a)²+(y-b)²=r²中，有三个参数a、b、r，即圆心坐标为(a，b)，半径r。
                double f=Math.pow((i-c_a), 2)+Math.pow((j-c_b), 2);

                double f2=Math.pow((i-c2_a), 2)+Math.pow((j-c2_b), 2);

                int rgb=oriImage.getRGB(i,j);
                if(i>=x&&i<(x+width) &&j>=y&&j<(y+height) && f2>=rr){//在矩形内
                    //块范围内的值
                    in(newSrc, newSrc2, i, j, rgb);
                }else if(f<=rr){
                    //在圆内
                    in(newSrc, newSrc2, i, j, rgb);
                }else{
                    //剩余位置设置成透明
                    out(newSrc, newSrc2, i, j, rgb);
                }

            }
        }
    }

    private void in(BufferedImage newSrc,BufferedImage newSrc2,int i,int j,int rgb){
        newSrc.setRGB(i, j, rgb);
        //原图设置变灰
        int r = (0xff & rgb);
        int g = (0xff & (rgb >> 8));
        int b = (0xff & (rgb >> 16));
        rgb = r + (g << 8) + (b << 16) + (100 << 24);
        //rgb = r + (g << 8) + (b << 16);
        newSrc2.setRGB(i, j, rgb);
        newSrc.setRGB(i + 1, j + 1, rgb);
    }

    private void out(BufferedImage newSrc,BufferedImage newSrc2,int i,int j,int rgb){
        newSrc.setRGB(i, j, 0x00ffffff);
        newSrc2.setRGB(i, j, rgb);
    }


    public static BufferedImage createDropShadow(BufferedImage image,
                                                 int size, float opacity) {
        int width = image.getWidth() + size * 2;
        int height = image.getHeight() + size * 2;
        BufferedImage mask = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mask.createGraphics();
        g2.drawImage(image, size, size, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,
                opacity));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        BufferedImage shadow = createBlurOp(size).filter(mask, null);
        g2 = shadow.createGraphics();
        g2.dispose();
        return shadow;
    }
    private static ConvolveOp createBlurOp(int size) {
        float[] data = new float[size * size];
        float value = 1f / (float) (size * size);
        for (int i = 0; i < data.length; i++) {
            data[i] = value;
        }
        return new ConvolveOp(new Kernel(size, size, data),
                ConvolveOp.EDGE_NO_OP, null);
    }
}
