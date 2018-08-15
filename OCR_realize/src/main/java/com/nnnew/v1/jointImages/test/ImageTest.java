package com.nnnew.v1.jointImages.test;

//导入需要的包

import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/*
 * 当导入一下这两个包时，MyEclipse会报错：
 * Access restriction: The type JPEGImageEncoder is not accessible due to
restriction on required library C:\Java\jre1.6.0_07\lib\rt.jar
 * 解决方法
 * Eclipse默认把这些受访问限制的API设成了ERROR。
 * 只要把Windows-Preferences-Java-Complicer-Errors/Warnings里面的
 * Deprecated and restricted API中的Forbidden references(access rules)选为Warning就可以编译通过。
 */
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageTest {

    public ImageTest() {
    }

    public static void main(String[] args) {
        try {
            ImageTest test = new ImageTest();
            test.ImageTset();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void ImageTset() throws Exception {
        //创建四个文件对象（注：这里四张图片的宽度都是相同的，因此下文定义BufferedImage宽度就取第一只的宽度就行了）
        String str = "C:\\Users\\Administrator\\Desktop\\temp_icon\\";
        File _file1 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_3.jpg");
        File _file2 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_4.jpg");
        File _file3 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_5.jpg");
        File _file4 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_6.jpg");
        File _file5 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_3.jpg");
        File _file6 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_4.jpg");
        File _file7 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_5.jpg");
        File _file8 = new File(str + "temp_1d69f51ce6ca40d6bb7af5f1fbb473d5_image_6.jpg");
        Image src1 = javax.imageio.ImageIO.read(_file1);
        Image src2 = javax.imageio.ImageIO.read(_file2);
        Image src3 = javax.imageio.ImageIO.read(_file3);
        Image src4 = javax.imageio.ImageIO.read(_file4);
        Image src5 = javax.imageio.ImageIO.read(_file5);
        Image src6 = javax.imageio.ImageIO.read(_file6);
        Image src7 = javax.imageio.ImageIO.read(_file7);
        Image src8 = javax.imageio.ImageIO.read(_file8);
        //获取图片的宽度
        int width = src1.getWidth(null);
        //获取各个图像的高度
        int height1 = src1.getHeight(null);
        int height2 = src2.getHeight(null);
        int height3 = src3.getHeight(null);
        int height4 = src4.getHeight(null);
        int height5 = src5.getHeight(null);
        int height6 = src6.getHeight(null);
        int height7 = src7.getHeight(null);
        int height8 = src8.getHeight(null);
        //构造一个类型为预定义图像类型之一的 BufferedImage。 宽度为第一只的宽度，高度为各个图片高度之和
        BufferedImage tag = new BufferedImage(width, height1 + height2 + height3 + height4 + height5 + height6 + height7 + height8, BufferedImage.TYPE_INT_RGB);
        //创建输出流
        FileOutputStream out = new FileOutputStream("D:\\treasureMap.jpg");
        //绘制合成图像
        Graphics g = tag.createGraphics();
        g.drawImage(src1, 0, 0, width, height1, null);
        g.drawImage(src2, 0, height1, width, height2, null);
        g.drawImage(src3, 0, height1 + height2, width, height3, null);
        g.drawImage(src4, 0, height1 + height2 + height3, width, height4, null);
        g.drawImage(src5, 0, height1 + height2 + height3 + height4, width, height5, null);
        g.drawImage(src6, 0, height1 + height2 + height3 + height4 + height5, width, height6, null);
        g.drawImage(src7, 0, height1 + height2 + height3 + height4 + height5 + height6, width, height7, null);
        g.drawImage(src8, 0, height1 + height2 + height3 + height4 + height5 + height6 + height7, width, height8, null);
        // 释放此图形的上下文以及它使用的所有系统资源。
        g.dispose();
        //将绘制的图像生成至输出流
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag);
        //关闭输出流
        out.close();
        System.out.println("藏宝图出来了");
    }
}
