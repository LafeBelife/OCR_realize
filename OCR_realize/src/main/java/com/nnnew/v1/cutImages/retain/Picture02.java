package com.nnnew.v1.cutImages.retain;

import com.nnnew.v1.cutImages.constant.PictureNumber;
import com.nnnew.v1.cutImages.util.IncisionUtil;

import java.awt.image.BufferedImage;

/**
 * 图片类
 *
 * @author 王保卫 2018-7-20
 */
public class Picture02 {/*
    private int[] minx = {200, 200};   // 起始 x
    private int centreX;    // 中间 y 轴，x坐标
    private BufferedImage bufferedImage;
    private String picturePath; // 切割图片路径
    private String saveFolderPath;  // 保存图片路径
    private int num;  // 计数器

    public Picture02(String picturePath, String saveFolderPath, BufferedImage bufferedImage) {
        this.picturePath = picturePath;
        this.bufferedImage = bufferedImage;
        this.saveFolderPath = saveFolderPath;

    }

    private int getTopOrBottomX(int startX, int j, int bottomMinX) {
        int count = 0;
        for (int i = startX; i < PictureNumber.PICTURE_X_CUT; i++) { // 200 为 色素到x起始位置。
            if (isRgbEquals(i, j)
                    && count++ == PictureNumber.PICTURE_COUNT
                    && (i - PictureNumber.PICTURE_COUNT) < bottomMinX) {
                bottomMinX = i - PictureNumber.PICTURE_COUNT;
                return bottomMinX;
            }
        }
        return bottomMinX;
    }

    public int[] getMinx() {  // 获取顶部 和 尾部 x
        // 获取 顶部 x
        for (int j = bufferedImage.getMinY(); j < PictureNumber.PICTURE_Y_FIRST_HEIGHT; j++) {
            minx[0] = getTopOrBottomX(bufferedImage.getMinX(), j, minx[0]);
        }// 获取底部 x 10 防止bufferedImage 获取最大高度，出现越界。
        for (int j = bufferedImage.getHeight() - 10; j > PictureNumber.PICTURE_Y_LAST_HEIGHT; j--) {
            minx[1] = getTopOrBottomX(bufferedImage.getMinX(), j, minx[1]);
        }
        return minx;
    }

    public int getCentreX() {   // 获取中间x 位置
        int count;
        for (int i = bufferedImage.getWidth() / 2 - PictureNumber.PICTURE_X_CENTRE_OFFSET;// 200 为 色素到x起始位置。
             i < bufferedImage.getWidth() / 2 + PictureNumber.PICTURE_X_CENTRE_OFFSET; i++) {
            count = 0;
            for (int j = bufferedImage.getMinY(); j < PictureNumber.PICTURE_X_CENTRE; j++) { // 250
                if (isRgbEquals(i, j)
                        && count++ == PictureNumber.PICTURE_COUNT) {
                    centreX = i;
                    return centreX;
                }
            }
        }
        return centreX;
    }


    public String getPicturePath() {
        return picturePath;
    }

    private String getImagePrefixPath() {// 图片前缀
        // 截取 以 “.” 为前缀命名， 不包含 “.”
        return saveFolderPath
                + picturePath.substring(picturePath.lastIndexOf("\\"),
                picturePath.lastIndexOf("."));
    }

    private String getSuffix() {// 图片后缀
        // 截取图片 以“.” 后缀 （包含 “.”）
        return IncisionUtil.getStringSuffix(picturePath);
    }

    public boolean cutPicture(int x, int y, int width, int height, String pictureJointName) {// 切割图片
        return IncisionUtil.cutPicture(getSuffix().substring(1, getSuffix().length()),
                picturePath, x, y, width, height,
                getImagePrefixPath() + "_" + pictureJointName + num++ + getSuffix());
    }

    public boolean isRgbEquals(int i, int j) { // 判断 rgb 像素
        int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
        int nextPixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
        return pixel < PictureNumber.PICTURE_X_CUT && nextPixel < PictureNumber.PICTURE_X_CUT;
    }

    public boolean isIconRgbEquals(int i, int j) { // 判断 rgb 像素
        int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
        int nextPixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
        return pixel < 180 && nextPixel < 180;  // 不包含中国商标的色素
    }

    static int top_num = 0;    // 计数器

    public int[] getIconTopYAndBottomY() {
        // 获取图标 最顶部 绝对位置 ,获取图标y , 获取图标最大 高度值.
        int[] y = {0, 0};
        for (int j = IncisionUtil.getInt(bufferedImage.getHeight() * 0.1);
             j < IncisionUtil.getInt(bufferedImage.getHeight() * 0.35); j++) {
            int v = getIconY(j);
            y[0] = v == 0 ? y[0] : v;
        }
        top_num = 0;
        for (int j = IncisionUtil.getInt(bufferedImage.getHeight() * 0.9);
             j > IncisionUtil.getInt(bufferedImage.getHeight() * 0.35); j--) {
            int v = getIconY(j);
            y[1] = v == 0 ? y[1] : v;
        }
        y[0] = y[0] + 10;
        y[1] = y[1] - 10;
        return y;
    }

    private int getIconY(int j) {
        int val = 0;
        for (int i = PictureNumber.PICTURE_COUNT; i < 40; i++) {  // 50 为 0 到 图标位置
            int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
            int nextPixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取色素
            if (pixel == 255 && nextPixel == 255 && top_num == 0) {
                top_num++;
                val = j;
            }
            if (isIconRgbEquals(i, j)) {// 去除 顶部 部分 rgb 使用
                top_num = 0;
            }
        }
        return val;
    }*/
}