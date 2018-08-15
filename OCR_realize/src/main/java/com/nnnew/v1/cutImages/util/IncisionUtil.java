package com.nnnew.v1.cutImages.util;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 图片切割 工具类
 *
 * @author 王保卫 2018-7-13
 */
public class IncisionUtil {
    private static boolean boo = false;
    private static MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap(); // 判断是否为图片

    static { // 图片格式
        mimetypesFileTypeMap.addMimeTypes("image bmp pcx tiff gif jpeg tga exif fpx svg psd cdr pcd dxf ufo eps png hdri ai raw");
    }

    /**
     * 截取元素后缀 以 "." 结尾。
     *
     * @param str
     * @return suffix
     */
    public static String getStringSuffix(String str) {
        return str.substring(str.lastIndexOf("."));
    }


    /**
     * 功能描述：对图片裁剪，保存新图片 。
     *
     * @param picturePath     图片地址
     * @param x               开始剪切的x坐标
     * @param y               开始剪切的y坐标
     * @param width           需要剪切的宽
     * @param height          需啊剪切的高
     * @param savePicturePath 保存图片地址
     * @return savePicturePath    剪切图标的地址
     * <br>
     */
    public static boolean cutPicture(String suffix, String picturePath, int x, int y, int width,
                                     int height, String savePicturePath) {
        ImageInputStream imageInputStream = null;
        ImageReader reader = null; // 创建reader  读取
        File file = new File(savePicturePath);
        try {
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix); // 迭代器读取 后缀,获取格式
            while (it.hasNext()) {
                reader = it.next();
            }
            if (reader == null) {
                Log4jLog.log.info("图片 不是有效的：" + picturePath);
                return false;
            }
            imageInputStream = ImageIO.createImageInputStream(new FileInputStream(picturePath)); // 获取图片流
            Objects.requireNonNull(reader).setInput(imageInputStream, true); // 设置读取属性
            ImageReadParam imageReadParam = reader.getDefaultReadParam();
            imageReadParam.setSourceRegion(new Rectangle(x, y, width, height)); // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            BufferedImage bi = reader.read(0, imageReadParam);
            boo = ImageIO.write(bi, suffix, file); // 保存新图片  png形式保存
        } catch (IOException e) {
            Log4jLog.log.info("流关闭 IOException：" + picturePath);
        } finally {
            if (reader != null) {
                reader.dispose();
            }
            if (imageInputStream != null) {
                try {
                    imageInputStream.close();
                } catch (IOException e) {
                    Log4jLog.log.info("流关闭 IOException：" + picturePath);
                }
            }
            return boo;
        }
    }

    /**
     * 把 double 转换成 int
     *
     * @param number
     * @return int
     */
    public static int getInt(double number) {
        // 四舍五入把double转化int整型，0.5进一，小于0.5不进一
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }


    /**
     * 验证是否为图片
     *
     * @param picturePath
     * @return boolean
     * true 成功，false 失败
     */
    public static boolean isPictureFile(String picturePath) {
        if (picturePath == null || picturePath.isEmpty()) {
            Log4jLog.log.info("图片为 空 | null ：" + picturePath);
            return false;
        }

        File picturePathFile = new File(picturePath); // 获取 图片 文件
        if (!picturePathFile.exists() || !picturePathFile.isFile()) {
            Log4jLog.log.info("图片 不是有效的:" + picturePath);
            return false;
        }
        String strPictureMap = mimetypesFileTypeMap.getContentType(picturePath);    // 图片后缀
        if (!(strPictureMap.split("/")[0]).equals("image")) {
            Log4jLog.log.info("图片 后缀格式不正确:" + picturePath);
            return false;
        }
        return true;
    }

    /**
     * 验证是否为文件
     *
     * @param filePath
     * @return boolean
     * true 成功，false 失败
     */
    public static boolean isFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            Log4jLog.log.info("文件路径为 空 | null ：" + filePath);
            return false;
        }

        File file = new File(filePath); // 获取 图片 文件
        if (!file.exists()) {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                Log4jLog.log.info("文件 父目录创建失败:" + filePath);
                return false;
            }
            try {
                file = new File(file.getParent(), file.getName());
                file.createNewFile();
            } catch (IOException e) {
                Log4jLog.log.info("创建文件失败!");
                return false;
            }
        } else {
            if (!file.isFile()) {
                Log4jLog.log.info("文件 不是有效的:" + filePath);
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为有效文件夹
     * 及创建文件夹
     *
     * @param folderPath
     * @return boolean
     * true 成功，false 失败
     */
    public static boolean isFolderPath(String folderPath) {
        if (folderPath == null || folderPath.isEmpty()) {
            Log4jLog.log.info("文件夹 目录为 空 | null :" + folderPath);
            return false;
        }
        File saveFolderPathFile = new File(folderPath); // 获取保存 切割后的 图片路径
        if (!saveFolderPathFile.exists()) { // 保存图片文件夹路径是否 存在
            if (!saveFolderPathFile.mkdirs()) {
                Log4jLog.log.info("文件夹目录 不是有效的，创建失败:" + folderPath);
                return false;
            }
        }
        if (!saveFolderPathFile.isDirectory()) {    // 保存图片文件夹是否为 文件夹
            Log4jLog.log.info("文件夹 不是有效的:" + folderPath);
            return false;
        }
        return true;
    }

    public static BufferedImage readImageIo(String picturePath) {
        BufferedImage bufferedImage = null;
        try {// 如果不是一个图片，会返回 null 值
            bufferedImage = ImageIO.read(new FileInputStream(picturePath));
        } catch (IOException e) {
            Log4jLog.log.info("读取图片 io 异常:" + picturePath);
        }
        return bufferedImage;
    }

    /**
     * 旋转图片为指定角度
     *
     * @param bufferedImage 目标图像
     * @param degree        旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedImage,
                                            final int degree) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(width, height, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), width / 2, height / 2);
        graphics2d.drawImage(bufferedImage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}