package com.the_check.test002;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test02 {
    public static void main(String[] args) {

        //文件与BufferedImage间的转换
        BufferedImage bi = file2img("C:\\Users\\Administrator\\Desktop\\image\\yBQCH1mDI02AZ4zAAAH4P2R6HAA521.png");  //读取图片
        BufferedImage bii = img_color_brightness(bi, 55);
        img2file(bii, "jpg", "C:\\Users\\Administrator\\Desktop\\test1.jpg");  //生成图片

    }

    //图片色阶调整，调整rgb的分量
    public static BufferedImage img_color_brightness(BufferedImage imgsrc, int brightness) {
        try {
            //创建一个不带透明度的图片
            BufferedImage back = new BufferedImage(imgsrc.getWidth(), imgsrc.getHeight(), BufferedImage.TYPE_INT_RGB);
            int width = imgsrc.getWidth();
            int height = imgsrc.getHeight();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = imgsrc.getRGB(j, i);
                    Color color = new Color(pixel);
                    int red = color.getRed() + brightness;
                    if (red > 255) red = 255;
                    if (red < 0) red = 0;
                    int green = color.getGreen() + brightness;
                    if (green > 255) green = 255;
                    if (green < 0) green = 0;
                    int blue = color.getBlue() + brightness;
                    if (blue > 255) blue = 255;
                    if (blue < 0) blue = 0;
                    color = new Color(red, green, blue);
                    int x = color.getRGB();
                    back.setRGB(j, i, x);
                }
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //读取图片
    public static BufferedImage file2img(String imgpath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imgpath));
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //保存图片,extent为格式，"jpg"、"png"等
    public static void img2file(BufferedImage img, String extent, String newfile) {
        try {
            ImageIO.write(img, extent, new File(newfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
