package com.nnnew.v1.cutImages.retain;

import com.nnnew.v1.cutImages.entity.Picture;
import com.nnnew.v1.cutImages.util.IncisionUtil;
import com.nnnew.v1.cutImages.util.Log4jLog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 图片切割，图标切割
 *
 * @author 王保卫 2018-7-14
 */
public class IncisionPicture03 {/*

    private BufferedImage bufferedImage = null;// 读取图片 信息 流
    private Picture picture = null;
    private boolean boo;

    *//**
     * 根据目录 切割图片 及 图标。
     *
     * @param pictureFolderPath 文件目录
     * @param saveFolderPath    保存的文件夹路径
     * @return saveFolderPath
     *//*
    public boolean incisionPictureByFolder(String pictureFolderPath, String saveFolderPath) {
        int num = 0;
        if (!IncisionUtil.isFolderPath(pictureFolderPath) && !IncisionUtil.isFolderPath(saveFolderPath)) {
            return false;
        }
        File[] picFiles = new File(pictureFolderPath).listFiles();
        if (picFiles == null || picFiles.length < 1) {
            Log4jLog.log.info("文件目录不存在子文件:" + pictureFolderPath);
            return false;
        }
        for (File file : Objects.requireNonNull(picFiles)) {
            if (file.isDirectory()) {    //若是目录，则递归打印该目录下的文件
                incisionPictureByFolder(file.toString(), saveFolderPath);
            }
            if (file.isFile()) {        //若是文件，直接剪切
                String tempPath = saveFolderPath + "//image_" + num++;
                File fileTemp = new File(tempPath);
                if (!fileTemp.exists() && !fileTemp.mkdirs()) {
                    Log4jLog.log.info("创建文件夹失败：" + tempPath);
                }
                boo = incisionPicture(file.getPath(), tempPath);
                if (!boo) {
                    Log4jLog.log.info("截取图片出现错误：" + file.getPath());
                }
                File[] iconFiles = new File(tempPath).listFiles();
                for (File icon : iconFiles) {
                    if (icon.isFile()) {
                        boo = incisionIcon(icon.getPath(), icon.getParent());
                        if (!boo) {
                            Log4jLog.log.info("截取图标出现错误：" + file.getPath());
                        }
                    }
                }
            }
        }
        return true;
    }

    *//**
     * 根据传入图片地址，剪切图片 图标。
     *
     * @param iconPath     剪切的图标路径
     * @param saveIconPath 保存图标的路径
     * @return savePath 保存图标的路径
     *//*
    public boolean incisionIcon(String iconPath, String saveIconPath) {
        if (!IncisionUtil.isPictureFile(iconPath) && !IncisionUtil.isPictureFile(saveIconPath)) { // 验证图片及文件夹
            return false;
        }
        bufferedImage = IncisionUtil.readImageIo(iconPath);
        if (bufferedImage == null) {
            Log4jLog.log.info("图片无法读取:" + iconPath);
            return false;
        }
        if (bufferedImage.getHeight() < 300) {  // 300 以下为 分类标签图片
            Log4jLog.log.info("读取图片不具备切割图标条件:" + iconPath);
            return false;
        }

        picture = new Picture(iconPath, bufferedImage);
        picture.setSuffix(IncisionUtil.getStringSuffix(iconPath));  // 截取 图片 后缀
        int top_num = 0, max_value = 0; // 获取图标位置// 获取图标 最顶部 绝对位置 ,获取图标y , 获取图标最大 高度值.
        picture.setNum(0);
        for (int j = IncisionUtil.getInt(picture.getHeight() * 0.1); j < picture.getHeight(); j++) { //起始位置 0.1 百分比，在 注册号位置
            for (int i = picture.getMinx(); i < 50; i++) {  // 50 为 0 到 图标位置
                picture.setPixel(bufferedImage.getRGB(i, j));

                if (j > IncisionUtil.getInt(picture.getHeight() * 0.33) && picture.getRgb()[0] < 100 && picture.rgbEquals()) {
                    j = picture.getHeight();// 超出图标下面 坐标 ，不为空白（255）时，结束循环。
                    break;
                }
                if (picture.getRgb()[0] == 255 && picture.rgbEquals()) {
                    picture.setNext_pixel(bufferedImage.getRGB(i + 1, j));
                    if (Arrays.equals(picture.getRgb(), picture.getNext_rgb())) {
                        max_value = j;
                        if (i > 8 && top_num == 0) {// x 小于8 是空白部分，不应该包含在内
                            picture.setMiny(j);
                            top_num++;
                        }
                    }
                }
                if (j < IncisionUtil.getInt(picture.getHeight() * 0.33) && picture.getRgb()[0] < 150) {// 去除 顶部 部分 rgb 使用
                    top_num = 0;
                }
            }
        }
        // 截取图片  top_y 顶部位置 ，x 从 top_y 开始切割，所以应该去除
        return picture.cutPicture(picture.getMinx(), picture.getMiny(), picture.getWidth() - 20, max_value - picture.getMiny(), saveIconPath);
    }

    *//**
     * 根据传入图片地址，分割图片。
     *
     * @param picturePath    切割的图片路径
     * @param saveFolderPath 保存的文件夹路径
     * @return saveFolderPath
     *//*
    public boolean incisionPicture(String picturePath, String saveFolderPath) {

        if (!IncisionUtil.isPictureFile(picturePath) && !IncisionUtil.isFolderPath(saveFolderPath)) { // 验证图片及文件夹
            return false;
        }
        bufferedImage = IncisionUtil.readImageIo(picturePath);
        if (bufferedImage == null) {
            Log4jLog.log.info("图片不是一个有效的图片:" + picturePath);
            return false;
        }

        picture = new Picture(picturePath, bufferedImage);
        picture.setSuffix(IncisionUtil.getStringSuffix(picturePath));    // todo 截取图片后缀
        String fileName = picturePath.substring(picturePath.lastIndexOf("\\") + 1, picturePath.lastIndexOf("."));   // 截取图片名称
        picture.setImagePrefixPath(saveFolderPath + "\\" + fileName + "_");

        // todo 截取顶部位置  + "top_"     204 为顶部空白区域
        boo = picture.cutPicture(0, 0, picture.getWidth(), 204, picture.savePicturePath("top_" + (picture.getNum())));
        if (!boo) {
            Log4jLog.log.info("图片切割错误：" + picture.savePicturePath("top_" + (picture.getNum())));
            return false;
        }
        // todo 截取尾部位置 stern   + "stern_"    // 1208 为尾部位置 高度
        boo = picture.cutPicture(0, 1208, picture.getWidth(), bufferedImage.getHeight() - 1208, picture.savePicturePath("stern_" + (picture.getNum())));
        if (!boo) {
            Log4jLog.log.info("图片切割错误：" + picture.savePicturePath("stern_" + (picture.getNum())));
            return false;
        }
        int width, count = 1; // count 计数器

        List<Integer> listY = new ArrayList<>();
        for (int x = 0; x < 2; x++) {
            listY.clear();
            width = count == 1 ? 200 : picture.getWidth() / 2 + 50;    // 200 为左半部分截图位置，50位右半部分截图位置
            picture.setMinx(x == 0 ? 0 : picture.getWidth() / 2 + 6);
            for (int j = picture.getMiny(); j < picture.getHeight(); j++) {
                count = 0;
                for (int i = picture.getMinx(); i < width; i++) {
                    picture.setPixel(bufferedImage.getRGB(i, j)); // 下面三行代码将一个数字转换为RGB数字
                    if (picture.rgbEquals() && picture.getRgb()[0] < 123) { // 判断rgb 是否相等
                        picture.setNext_pixel(bufferedImage.getRGB(i + 1, j));
                        if (Arrays.equals(picture.getRgb(), picture.getNext_rgb())) {
                            if (count++ == 31) {    // 连续重复值
                                listY.add(j);
                                break;
                            }
                        }
                    }
                }
            }
            if (!traversePicturePathMap(listY, x)) { // 遍历 map 切割图片
                Log4jLog.log.info("切割图片出现错误：" + picture.getCutPicturePath());
                return false;
            }
        }
        return true;
    }

    *//**
     * 切割图片
     *
     * @param list
     * @param x
     * @return boolean
     *//*
    private boolean traversePicturePathMap(List<Integer> list, int x) {
        String savePicturePath;
        int up_val = 0;
        for (Integer value : list) {
            if (up_val == 0) {  // 跳出头部 空白区域
                up_val = value;
                continue;
            }
            if ((value - up_val) > 50) { // 此处 防止 同一 x,y 轴，错误截取.
                savePicturePath = picture.savePicturePath(picture.getNum() + "");
                boo = picture.cutPicture(x == 0 ? 130 : picture.getWidth() / 2 + 6, up_val, picture.getWidth() / 2 - 130, value - up_val, savePicturePath);
                if (!boo) {
                    Log4jLog.log.info("切割图片出现错误：" + savePicturePath);
                    return false;
                }
                up_val = value;
            }
        }
        return boo;
    }

    public void cutPic(String picturePath, String saveFolderPath) {

//  todo      1.先获取最小 x 。 minX
        *//*
        todo 获取最大 起始 到 x位置。
         todo   获取最小 起始 到 x轴位置， 最大 - 最小 = 偏移量。
         *//*
        bufferedImage = IncisionUtil.readImageIo(picturePath);
        int topMinX = 200;
        int bottomMinX = 200;
        int count;
        for (int j = bufferedImage.getMinY(); j < bufferedImage.getHeight() * 0.2; j++) {
            count = 0;
            for (int i = bufferedImage.getMinX(); i < 180; i++) { // 200 为 色素到x起始位置。
                int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
                int next_pixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
                if (pixel < 200 && next_pixel < 200 && count++ == 30 && (i - 30) < topMinX) {
                    topMinX = i - 30;
                    break;
                }
            }
        }
        for (int j = bufferedImage.getHeight() - 10; j > IncisionUtil.getInt(bufferedImage.getHeight() * 0.8); j--) {
            count = 0;
            for (int i = bufferedImage.getMinX(); i < 180; i++) { // 200 为 色素到x起始位置。
                int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
                int next_pixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
                if (pixel < 200 && next_pixel < 200 && count++ == 30 && (i - 30) < bottomMinX) {
                    bottomMinX = i - 30;
                    break;
                }
            }
        }
        System.out.println("顶部x：" + topMinX + ",底部x：" + bottomMinX);
        // TODO  2.获取中间 i 轴 centreY
        bufferedImage = IncisionUtil.readImageIo(picturePath);
        int centreX = 0;

        for (int i = bufferedImage.getWidth() / 2 - 60; i < bufferedImage.getWidth() / 2 + 60; i++) { // 200 为 色素到x起始位置。
            count = 0;
            for (int j = bufferedImage.getMinY(); j < 250; j++) { //bufferedImage.getHeight() / 5
                int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
                int next_pixel = (bufferedImage.getRGB(i, j + 1) & 0xff00) >> 8;  // 获取下一个色素
                if (pixel < 200 && next_pixel < 200 && count++ == 30) {
                    centreX = i;
                    System.out.println(i + "\t" + j);
                    i = bufferedImage.getHeight();
                    break;
                }
            }
        }
        System.out.println("中间x：" + centreX);
//        todo 3.遍历切割图片 centreY bottomMinX topMinX
        int minX;   // 最小起始位置
        if (bottomMinX > topMinX) {
//            centreX = centreX - (bottomMinX - topMinX); // 往右偏移
            minX = topMinX;
        } else {
            centreX = centreX - (topMinX - bottomMinX); // 往左偏移
            minX = bottomMinX;
        }
        List<Integer> listY = new ArrayList<>();
        int width = 0;
        for (int x = 0; x < 2; x++) {
            listY.clear();
            minX = x == 0 ? minX : centreX;
            width = x == 0 ? minX + 50 : centreX + 50;
            for (int j = bufferedImage.getMinY(); j < bufferedImage.getHeight(); j++) {
                count = 0;
                for (int i = minX; i < width; i++) {
                    int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
                    int next_pixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
                    if (pixel < 200 && next_pixel < 200 && count++ == 30) {
                        if (count++ == 31) {    // 连续重复值
                            listY.add(j);
                            System.out.println("x:" + minX + "\t y:" + j);
                            j = j + 5;  // 此处去除同一 位置 x 轴 占三个点元素。
                            break;
                        }
                    }
                }

            }
            traversePicture(listY, minX, x == 0 ? centreX - minX : centreX, saveFolderPath, picturePath);
            System.out.println();
        }
    }

    static int count = 0;

    private boolean traversePicture(List<Integer> list, int x, int width, String savePicturePath, String picturePath) {
        int up_val = 0;
        for (Integer value : list) {
            if (up_val == 0) {  // 跳出头部 空白区域
                up_val = value;
                continue;
            }
//            savePicturePath = picture.savePicturePath(picture.getNum() + "");
//            boo = picture.cutPicture(x, up_val, width, value - up_val, savePicturePath);
            boo = IncisionUtil.cutPicture("jpg", picturePath, x, up_val, width, value - up_val, savePicturePath + "\\" + count++ + ".jpg");
            if (!boo) {
                Log4jLog.log.info("切割图片出现错误：" + savePicturePath);
//                return false;
            }
            up_val = value;
        }
        return boo;
    }*/
}