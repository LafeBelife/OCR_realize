package com.nnnew.v1.baiduAi.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import javax.imageio.ImageIO;

public class BinaryTest {
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMXZSQ\\group2\\M00\\0C\\BD\\yBQCIFmFJmmAHLcuAAG43UxcWNA786.jpg"));
        int w = image.getWidth();
        int h = image.getHeight();
        float[] rgb = new float[3];
        double[][] zuobiao = new double[w][h];
        int R = 0;
        float red = 0;
        float green = 0;
        float blue = 0;
        BufferedImage bi= new BufferedImage(w, h,
                BufferedImage.TYPE_BYTE_BINARY);;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int pixel = image.getRGB(x, y);
//                sop(pixel);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                red += rgb[0];
                green += rgb[1];
                blue += rgb[2];
                R = (x+1) *(y+1);
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;
                zuobiao[x][y] = avg;

            }
        }
        double SW = 230;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (zuobiao[x][y] <= SW) {
                    int max = new Color(0, 0, 0).getRGB();
                    bi.setRGB(x, y, max);
                }else{
                    int min = new Color(255, 255, 255).getRGB();
                    bi.setRGB(x, y, min);
                }
            }
        }

        ImageIO.write(bi, "jpg", new File("C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMXZSQ\\group2\\M00\\0C\\BD\\3.jpg"));
    }

    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
