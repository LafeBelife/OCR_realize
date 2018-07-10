package com.the_check.test001;


import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jzhung on 2017/2/27.
 * 文档图片水印处理
 */
public class ImageConverter {
    private static List<File> fileList = new ArrayList<File>();

    public static void main(String[] args) {
        System.out.println("输入需要去水印的图片所在的根目录回车（支持递归子目录）：");
        Scanner input = new Scanner(System.in);
        String dir = input.nextLine().trim();

        System.out.println("输入转换后的存储目录：");
        String saveDir = input.nextLine().trim();
        System.out.println();
        convertAllImages(dir, saveDir);
    }

    private static void convertAllImages(String dir, String saveDir) {
        File dirFile = new File(dir);
        File saveDirFile = new File(saveDir);
        dir = dirFile.getAbsolutePath();
        saveDir = saveDirFile.getAbsolutePath();
        loadImages(new File(dir));
        for (File file : fileList) {
            String filePath = file.getAbsolutePath();

            String dstPath = saveDir + filePath.substring(filePath.indexOf(dir) + dir.length(), filePath.length());
            System.out.println("converting: " + filePath);
            replaceColor(file.getAbsolutePath(), dstPath);
        }
    }

    public static void loadImages(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        loadImages(fileArray[i]);
                    }
                }
            } else {
                String name = f.getName();
                if (name.endsWith("png") || name.endsWith("jpg")) {
                    fileList.add(f);
                }
            }
        }
    }

    private static void replaceColor(String srcFile, String dstFile) {
        try {
            Color color = new Color(255, 195, 195);
            replaceImageColor(srcFile, dstFile, color, Color.WHITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replaceImageColor(String file, String dstFile, Color srcColor, Color targetColor) throws IOException {
        URL http;
        if (file.trim().startsWith("https")) {
            http = new URL(file);
            HttpsURLConnection conn = (HttpsURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else if (file.trim().startsWith("http")) {
            http = new URL(file);
            HttpURLConnection conn = (HttpURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else {
            http = new File(file).toURI().toURL();
        }
        BufferedImage bi = ImageIO.read(http.openStream());
        if (bi == null) {
            return;
        }

        Color wColor = new Color(255, 255, 255);

//        Color wColor1 = new Color(80, 80, 80);
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                //System.out.println(bi.getRGB(i, j));
                int color = bi.getRGB(i, j);
                Color oriColor = new Color(color);
                int red = oriColor.getRed();
                int greed = oriColor.getGreen();
                int blue = oriColor.getBlue();
                //粉色
//                if (greed < 190 || blue < 190) {
//
//                } else {
////                    if (red == 255 && greed > 180 && blue > 180) {
////                        bi.setRGB(i, j, wColor.getRGB());
////                    }

                if (red > 180 && greed > 180 && blue > 180 && red == greed && greed == blue) { // && red != 255 && greed != 255 && blue != 255
                    bi.setRGB(i, j, wColor.getRGB());
                }
//                C:\Users\Administrator\Desktop\image
//                if (red > 170 && greed > 170 && blue > 170 && red == greed && greed == blue) {
//                    bi.setRGB(i, j, wColor.getRGB());
//                }
            }
        }
        String type = file.substring(file.lastIndexOf(".") + 1, file.length());
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(type);
        ImageWriter writer = it.next();
        File f = new File(dstFile);
        f.getParentFile().mkdirs();
        ImageOutputStream ios = ImageIO.createImageOutputStream(f);
        writer.setOutput(ios);
        writer.write(bi);
        bi.flush();
        ios.flush();
        ios.close();
    }
}