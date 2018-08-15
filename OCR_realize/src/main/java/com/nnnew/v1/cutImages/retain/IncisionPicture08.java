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
import java.util.UUID;

/**
 * 图片切割，图标切割
 *
 * @author 王保卫 2018年7月27日11:28:48
 */
public class IncisionPicture08 {/*
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
                                Log4jLog.log.info("图标切割失败：" + file.getPath());
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
        return picture.cutPicture(bufferedImage.getMinX(), iconY[0], bufferedImage.getWidth(),
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
        if (picturePath.startsWith("temp_")) {
            Log4jLog.log.info("图片为临时图片：" + picturePath);
            return false;
        }
        bufferedImage = IncisionUtil.readImageIo(picturePath);
        if (bufferedImage == null) {
            Log4jLog.log.info("图片 不是有效的:" + picturePath);
            return false;
        }

        picture = new Picture(picturePath, saveFolderPath, bufferedImage);  // 得到图片信息

        int centreX = picture.getCentreX(); // 获取中间轴
        int[] minX = picture.getMinx();    // 获取 起始x 最大和最小值
        if (centreX == 0 || minX[0] == 200 || minX[1] == 200) {
            Log4jLog.log.info("图片不具备切割条件：" + picturePath);
            return false;
        }
        int offset = 0, startMinX = minX[0];// 偏移量
        if (minX[0] - minX[1] > 6) { // 图片朝左偏移
            offset = 360 - 1;   // 只能旋转 1度
        } else if (minX[1] - minX[0] > 6) {    // 向右偏移或不偏移
            offset = 1;
        }
        try {
            if (offset != 0) {  // 旋转图片 部分
                bufferedImage = IncisionUtil.rotateImage(bufferedImage, offset);
                picturePath = new File(picturePath).getParentFile().getPath() + "\\temp_" + UUID.randomUUID().toString().replaceAll("-", "") + picture.getSuffix();
                ImageIO.write(bufferedImage,
                        picture.getSuffix().substring(1, picture.getSuffix().length()), new File(picturePath));
                bufferedImage = IncisionUtil.readImageIo(picturePath);
                if (bufferedImage == null) {
                    Log4jLog.log.info("图片 不是有效的:" + picturePath);
                    return false;
                }
                picture = new Picture(picturePath, saveFolderPath, bufferedImage);  // 得到图片信息

                startMinX = picture.getMinx()[0];
                centreX = picture.getCentreX();
            }
        } catch (IOException e) {
            Log4jLog.log.info("矫正 生成新图片 IOException：" + "");
            return false;
        }
        List<Integer> listY = new ArrayList<>();    // 遍历，切割图片
        num = 0;
        int count;
        for (int x = 0; x < 2; x++) {
            listY.clear();
            startMinX = x == 0 ? startMinX : centreX;   // 获取起始点
            for (int j = bufferedImage.getMinY() + 30;  // 开始点，减去部分 为旋转部分
                 j < bufferedImage.getHeight() - PictureNumber.PICTURE_COUNT; j++) {
                count = 0;
                for (int i = startMinX + 10; i < startMinX + centreX / 2; i++) {  // 判断连续值
                    if (picture.isRgbEquals(i, j)) {
                        if (count++ == PictureNumber.PICTURE_COUNT) {
                            listY.add(j);
                            j = j + 10;  // 此处去除同一 位置 x 轴 占三个点元素。
                            break;
                        }
                    } else {
                        count = 0;
                    }
                }
                if (listY.size() == 0) {
                    Log4jLog.log.info("图片不具备切割条件：" + picturePath);
                    return false;
                }
                if (x == 0) {    // 切割头部和尾部
                    picture.cutPicture(bufferedImage.getMinX() + 30, bufferedImage.getMinY() + 20,
                            bufferedImage.getWidth() - 50, listY.get(0) - 20, "image_" + num++ + "_start");
                    picture.cutPicture(bufferedImage.getMinX() + 30, listY.get(listY.size() - 1),
                            bufferedImage.getWidth() - 50,
                            bufferedImage.getHeight() - listY.get(listY.size() - 1) - 20,
                            "image_" + num++ + "_end");
                }
                *//*    切割图片****//*
                traversePicture(listY, startMinX, x == 0 ? centreX - startMinX : centreX);
            }
        }
       *//* File file = new File(picturePath);
        if (file.isFile()) {
            System.out.println("删除图片：" + file.delete());
        }*//*
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
    }*/
}