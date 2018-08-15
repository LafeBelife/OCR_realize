package com.nnnew.v1.cutImages.retain;

import com.nnnew.v1.cutImages.constant.PictureNumber;
import com.nnnew.v1.cutImages.entity.Picture;
import com.nnnew.v1.cutImages.util.IncisionUtil;
import com.nnnew.v1.cutImages.util.Log4jLog;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片切割，图标切割
 *
 * @author 王保卫 2018-7-14
 */
public class IncisionPicture07 {/*
    private Picture picture;
    private BufferedImage bufferedImage;// 读取图片 信息 流
    private int num;  // 计数器

    *//**
     * 根据目录 切割图片 及 图标。
     * 思路：
     * 非空，非null判断。
     * 文件及文件夹的真实性。
     * 遍历目录是否存在子文件，是目录 递归。
     * 否则切割图片。
     *
     * @param pictureFolderPath 文件目录
     * @param saveFolderPath    保存的文件夹路径
     * @return saveFolderPath
     *//*
    public synchronized boolean incisionPictureByFolder(String pictureFolderPath, String saveFolderPath) {

        if (!IncisionUtil.isFolderPath(pictureFolderPath) || !IncisionUtil.isFolderPath(saveFolderPath)) {
            Log4jLog.log.info("文件目录 不是有效的:" + pictureFolderPath + "\n" + saveFolderPath);
            return false;
        }
        File[] picFiles = new File(pictureFolderPath).listFiles();
        if (picFiles == null || picFiles.length < 1) {
            Log4jLog.log.info("文件目录 不是有效的:" + pictureFolderPath);
            return false;
        }
        int num = 0;
        for (File file : picFiles) {
            if (file.isDirectory()) {    //若是目录，则递归打印该目录下的文件
                incisionPictureByFolder(file.toString(), saveFolderPath + "\\" + file.getName());
            }
            if (file.isFile()) {      //若是文件，直接剪切
                String tempPath = saveFolderPath + "\\image_" + num++;
                File fileTemp = new File(tempPath);
                if (!fileTemp.exists() && !fileTemp.mkdirs()) {
                    Log4jLog.log.info("创建文件夹失败：" + tempPath);
                    return false;
                }
                boolean isBoolean = incisionPicture(file.getPath(), tempPath);
                if (!isBoolean) {
                    Log4jLog.log.info("切割图片失败：" + file.getPath());
                    continue;
                }
                File[] iconFiles = new File(tempPath).listFiles();
                if (iconFiles != null) {
                    for (File icon : iconFiles) {
                        if (IncisionUtil.isPictureFile(icon.getPath())) {
                            isBoolean = incisionIcon(icon.getPath(), icon.getParent());
                            if (!isBoolean) {
                                Log4jLog.log.info("图标：" + file.getPath());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    *//**
     * 根据传入图片地址，剪切图片 图标。
     * 实现思路：
     * 1.非空非null，文件及文件夹的真实性，不满足返回 false 。
     * * 2.读取图片的真实性，不为null，获得图片的基本信息。
     * * 3.截取图片后缀，获取图片前缀，得到新的 图片名。
     * * 如： 张三 张三_0,张三_1 ...
     * 4.使用for 循环 获取 图标 顶部空白，去除顶部文字部分，
     * 获取 图标 最低空白 ，到图标 文字部分。
     * 5.根据获取到的值切割图标。
     *
     * @param iconPath       剪切的图标路径
     * @param saveIconFolder 文件夹路径
     * @return savePath 保存图标的路径
     *//*
    public synchronized boolean incisionIcon(String iconPath, String saveIconFolder) {

        if (!IncisionUtil.isPictureFile(iconPath) || !IncisionUtil.isFolderPath(saveIconFolder)) { // 验证图片及文件夹
            Log4jLog.log.info("文件 | 保存文件目录 不是有效的!" + iconPath + "\n" + saveIconFolder);
            return false;
        }
        bufferedImage = IncisionUtil.readImageIo(iconPath);
        if (bufferedImage == null) {
            Log4jLog.log.info("图片 不是有效的:" + iconPath);
            return false;
        }
        if (iconPath.indexOf("image_start_") != -1 || iconPath.indexOf("image_end_") != -1
                || bufferedImage.getHeight() < PictureNumber.PICTURE_X_CUT
                || bufferedImage.getWidth() < PictureNumber.PICTURE_X_CUT) {  // 200 以下为 分类标签图片
            Log4jLog.log.info("图片为 start | end | 分类 图片:" + iconPath);
            return false;
        }
        picture = new Picture(iconPath, saveIconFolder, bufferedImage);
        // 获取图标 最顶部 绝对位置 ,获取图标y , 获取图标最大 高度值.
        int[] iconY = picture.getIconTopYAndBottomY();
        // 截取图片  top_y 顶部位置 ，x 从 top_y 开始切割，所以应该去除
        return picture.cutPicture(PictureNumber.PICTURE_COUNT, iconY[0], bufferedImage.getWidth(),
                iconY[1] - iconY[0], "icon");
    }

    *//**
     * 根据传入图片地址，分割图片。
     * 实现思路：
     * 1.非空非null，文件及文件夹的真实性，不满足返回 false 。
     * 2.读取图片的真实性，不为null，获得图片的基本信息。
     * 3.截取图片后缀，获取图片前缀，得到新的 图片名。
     * 如： 张三 张三_0,张三_1 ...
     * 4.获得顶部 x，底部 y，中间 x。
     * 5.判断获取 中间 x，偏移量。
     * 6.根据获取到的信息 分左右两部分切割图片，获取切割的y轴。
     * 7.切割图片，保存路径。
     *
     * @param picturePath    切割的图片路径
     * @param saveFolderPath 保存的文件夹路径
     * @return saveFolderPath
     *//*
    public boolean incisionPicture(String picturePath, String saveFolderPath) {

        if (!IncisionUtil.isPictureFile(picturePath) || !IncisionUtil.isFolderPath(saveFolderPath)) { // 验证图片及文件夹
            Log4jLog.log.info("文件 | 保存文件目录 不是有效的：\n" + picturePath + "\n" + saveFolderPath);
            return false;
        }

        bufferedImage = IncisionUtil.readImageIo(picturePath);
        if (bufferedImage == null) {
            Log4jLog.log.info("图片 不是有效的:" + picturePath);
            return false;
        }

        picture = new Picture(picturePath, saveFolderPath, bufferedImage);  // 得到图片信息

        int centreX = picture.getCentreX(); // 获取中间轴
        int[] min_X = picture.getMinx();    // 获取 起始x 最大和最小值
        if (centreX == 0 || min_X[0] == 200 || min_X[1] == 200) {
            Log4jLog.log.info("图片不具备切割条件：" + picturePath);
            return false;
        }
        int minX;
        if (min_X[1] > min_X[0]) {  // 计算偏移量
            minX = min_X[0];
        } else {
            centreX = centreX - (min_X[0] - min_X[1]); // 往左偏移
            minX = min_X[1];
        }
        List<Integer> listY = new ArrayList<>();    // 遍历，切割图片
        num = 0;
        int width, count;
        for (int x = 0; x < 2; x++) {
            listY.clear();
            minX = x == 0 ? minX : centreX;
            width = x == 0 ? minX + 50 : centreX + 50;
            for (int j = bufferedImage.getMinY();
                 j < bufferedImage.getHeight() - PictureNumber.PICTURE_COUNT; j++) {
                count = 0;
                for (int i = minX; i < width; i++) {
                    if (picture.isRgbEquals(i, j)
                            && count++ == PictureNumber.PICTURE_COUNT) {
                        listY.add(j);
                        j = j + 5;  // 此处去除同一 位置 x 轴 占三个点元素。
                        break;
                    }
                }
            }
            if (listY.size() == 0) {
                Log4jLog.log.info("图片不具备切割条件：" + picturePath);
                return false;
            }
            if (x == 0) {    // 切割头部和尾部
                picture.cutPicture(bufferedImage.getMinX(), bufferedImage.getMinY(),
                        bufferedImage.getWidth(), listY.get(0), "image_" + num++ + "_start");
                picture.cutPicture(bufferedImage.getMinX(), listY.get(listY.size() - 1),
                        bufferedImage.getWidth(),
                        bufferedImage.getHeight() - listY.get(listY.size() - 1),
                        "image_" + num++ + "_end");
            }
            *//*    切割图片****//*
            traversePicture(listY, minX, x == 0 ? centreX - minX : centreX);
        }
        return true;
    }

    *//**
     * 切割图片
     * 思路：
     * 跳出 第一次循环，第一次得到的值为顶部，
     * 根据上一次得到的值，用当前值减去上次值就是本次图片高度。
     * 从而实现切割
     *
     * @param list
     * @param x
     * @return boolean
     *//*
    private void traversePicture(List<Integer> list, int x, int width) {
        int up_val = 0;
        for (Integer value : list) {
            if (up_val == 0) {  // 跳出头部 空白区域
                up_val = value;
                continue;
            }
            if (value - up_val < PictureNumber.PICTURE_COUNT) {   // 判断误截取
                continue;
            }
            if (!picture.cutPicture(x, up_val, width, value - up_val, "image_" + num++)) {
                Log4jLog.log.info("切割图片出现错误：" + picture.getPicturePath());
            }
            up_val = value;
        }
    }

    public void cutPic(String picPath, String folderPath) {
        // todo 获取起始 topX，bottomX
        try {
            bufferedImage = ImageIO.read(new File(picPath));
        } catch (IOException e) {
            Log4jLog.log.info("");
        }
        picture = new Picture(picPath, folderPath, bufferedImage);
        // todo 判断是否倾斜
        int leftTopX = 200, leftTopY = 0, leftBottomX = 200, leftBottomY = 0;
        int rightTopX = 200, rightTopY = 0;

        // 获取 顶部 x
        for (int j = bufferedImage.getMinY(); j < PictureNumber.PICTURE_Y_FIRST_HEIGHT; j++) {
            int count = 0;
            for (int i = bufferedImage.getMinX(); i < PictureNumber.PICTURE_X_CUT; i++) { // 200 为 色素到x起始位置。
                if (picture.isRgbEquals(i, j)
                        && count++ == PictureNumber.PICTURE_COUNT
                        && (i - PictureNumber.PICTURE_COUNT) < leftTopX) {
                    leftTopX = i - PictureNumber.PICTURE_COUNT;
                    leftTopY = j;
                }
            }
        }
        System.out.println(leftTopX + "\t" + leftTopY);
        // 获取底部 x 10 防止bufferedImage 获取最大高度，出现越界。
        for (int j = bufferedImage.getHeight() - 10; j > PictureNumber.PICTURE_Y_LAST_HEIGHT; j--) {
            int count = 0;
            for (int i = bufferedImage.getMinX(); i < PictureNumber.PICTURE_X_CUT; i++) { // 200 为 色素到x起始位置。
                if (picture.isRgbEquals(i, j)
                        && count++ == PictureNumber.PICTURE_COUNT
                        && (i - PictureNumber.PICTURE_COUNT) < leftBottomX) {
                    leftBottomX = i - PictureNumber.PICTURE_COUNT;
                    leftBottomY = j;
                }
            }
        }
        System.out.println(leftBottomX + "\t" + leftBottomY);
        for (int j = bufferedImage.getMinY(); j < PictureNumber.PICTURE_Y_FIRST_HEIGHT; j++) {
            int count = 0;
            for (int i = bufferedImage.getWidth() - 10; i > bufferedImage.getWidth() / 2; i--) { // 200 为 色素到x起始位置。
                if (picture.isRgbEquals(i - 1, j)
                        && count++ == PictureNumber.PICTURE_COUNT
                        && i > rightTopX) {
                    rightTopX = i + PictureNumber.PICTURE_COUNT;
                    rightTopY = j;
                }
            }
        }
        System.out.println(rightTopX + "\t" + rightTopY);
        double offset = 0;
        BufferedImage bi = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_USHORT_555_RGB);
        int rgb[] = new int[3];
        *//*if (leftBottomX - leftTopX > 3) {
            offsetBottom = (double) (leftBottomX - leftTopX) / (double) (leftBottomY - leftTopY);
            for (int j = 0; j < bufferedImage.getHeight() - 10; j++) {
                for (int i = 0; i < bufferedImage.getWidth() - 30; i++) {
                    if (j >= leftTopY && i >= leftTopX && j <= leftBottomY) {
                        int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                        rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                        rgb[1] = (pixel & 0xff00) >> 8;
                        rgb[2] = (pixel & 0xff);
                        double offset = (j - leftTopY) * offsetBottom;
                        bufferedImage.setRGB((i - (int) offset), j, pixel);
                    }
                }
            }
        }*//*
        offset = (double) (rightTopY - leftTopY) / (double) (rightTopX - leftTopX);
        for (int j = 0; j < bufferedImage.getHeight() - 10; j++) {
            for (int i = 0; i < bufferedImage.getWidth() - 30; i++) {
                if (j >= leftTopY && i >= leftBottomX) {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    double _offset = (i - leftTopX) * offset;
                    bi.setRGB(i, j - (int) _offset, pixel);
                } else {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    bi.setRGB(i, j, pixel);
                }
            }
        }
        try {
            ImageIO.write(bi, "jpg", new File("D://a.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            bufferedImage = ImageIO.read(new File("D://a.jpg"));
        } catch (IOException e) {
            Log4jLog.log.info("");
        }
        // 获取 顶部 x
        for (int j = bufferedImage.getMinY(); j < PictureNumber.PICTURE_Y_FIRST_HEIGHT; j++) {
            int count = 0;
            for (int i = bufferedImage.getMinX(); i < PictureNumber.PICTURE_X_CUT; i++) { // 200 为 色素到x起始位置。
                if (picture.isRgbEquals(i, j)
                        && count++ == PictureNumber.PICTURE_COUNT
                        && (i - PictureNumber.PICTURE_COUNT) < leftTopX) {
                    leftTopX = i - PictureNumber.PICTURE_COUNT;
                    leftTopY = j;
                }
            }
        }
        System.out.println(leftTopX + "\t" + leftTopY);
        // 获取底部 x 10 防止bufferedImage 获取最大高度，出现越界。
        for (int j = bufferedImage.getHeight() - 10; j > PictureNumber.PICTURE_Y_LAST_HEIGHT; j--) {
            int count = 0;
            for (int i = bufferedImage.getMinX(); i < PictureNumber.PICTURE_X_CUT; i++) { // 200 为 色素到x起始位置。
                if (picture.isRgbEquals(i, j)
                        && count++ == PictureNumber.PICTURE_COUNT
                        && (i - PictureNumber.PICTURE_COUNT) < leftBottomX) {
                    leftBottomX = i - PictureNumber.PICTURE_COUNT;
                    leftBottomY = j;
                }
            }
        }
        System.out.println(leftBottomX + "\t" + leftBottomY);
        for (int j = bufferedImage.getMinY(); j < PictureNumber.PICTURE_Y_FIRST_HEIGHT; j++) {
            int count = 0;
            for (int i = bufferedImage.getWidth() - 10; i > bufferedImage.getWidth() / 2; i--) { // 200 为 色素到x起始位置。
                if (picture.isRgbEquals(i - 1, j)
                        && count++ == PictureNumber.PICTURE_COUNT
                        && i > rightTopX) {
                    rightTopX = i + PictureNumber.PICTURE_COUNT;
                    rightTopY = j;
                }
            }
        }
        bi = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_USHORT_555_RGB);
        offset = (double) (leftTopX - leftBottomX) / (double) (leftBottomY - leftTopY);
        for (int j = 0; j < bufferedImage.getHeight() - 10; j++) {
            for (int i = bufferedImage.getWidth() - 30; i > 30; i--) {
                if (j >= leftTopY && i >= leftBottomX) {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    double _offset = (j - leftTopY) * offset;
                    bi.setRGB(i + (int) _offset, j, pixel);
                } else {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    bi.setRGB(i, j, pixel);
                }
            }
        }

        try {
            ImageIO.write(bi, "jpg", new File("D://aa.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bufferedImage = ImageIO.read(new File("D://aa.jpg"));
        } catch (IOException e) {
            Log4jLog.log.info("");
        }
        bi = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_USHORT_555_RGB);
        for (int j = 0; j < bufferedImage.getHeight(); j++) {
            for (int i = bufferedImage.getWidth() - 1; i > 0; i--) {
                if (j >= leftTopY && j <= leftBottomY + 8 && i >= leftTopX && i <= rightTopX) {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    bi.setRGB(i, j, pixel);
                } else {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    bi.setRGB(i, j, -1);
                }
            }
        }

        try {
            ImageIO.write(bi, "jpg", new File("D://aaa.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // todo 如果倾斜，获取 rightX
        // todo 循环开始矫正图片
    }*/


}