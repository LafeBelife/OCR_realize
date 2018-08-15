package com.nnnew.v1.cutImages.retain;

import com.nnnew.v1.cutImages.util.IncisionUtil;

import java.awt.image.BufferedImage;

/**
 * 图片类
 *
 * @author 王保卫 2018-7-17
 */
public class Picture01 {/*
    private int width;  // 图片宽度
    private int height; // 图片高度
    private int minx;   // 起始 x,y
    private int miny;
    private int pixel;  // 图片像素
    private int next_pixel; // 下一个位置像素
    private int num = 0;    // 计数使用

    private int[] rgb = new int[3];  // 图片rgb （红绿蓝）
    private int[] next_rgb = new int[3];    // 下一个rgb

    private String cutPicturePath; // 切割图片路径
    private String imagePrefixPath; // 图片前缀
    private String suffix;

    public Picture01(String picturePath, BufferedImage bufferedImage) {
        this.cutPicturePath = picturePath;
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        this.minx = bufferedImage.getMinX();
        this.miny = bufferedImage.getMinY();
    }

    public int getMinx() {
        return minx;
    }

    public void setMinx(int minx) {
        this.minx = minx;
    }

    public int getMiny() {
        return miny;
    }

    public void setMiny(int miny) {
        this.miny = miny;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getRgb() {
        rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    public int[] getNext_rgb() {
        next_rgb[0] = (next_pixel & 0xff0000) >> 16;    // rgb 红绿蓝
        next_rgb[1] = (next_pixel & 0xff00) >> 8;
        next_rgb[2] = (next_pixel & 0xff);
        return next_rgb;
    }

    public void setNext_rgb(int[] next_rgb) {
        this.next_rgb = next_rgb;
    }

    public int getPixel() {
        return pixel;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
    }

    public int getNext_pixel() {
        return next_pixel;
    }

    public void setNext_pixel(int next_pixel) {
        this.next_pixel = next_pixel;
    }

    public String getImagePrefixPath() {
        return imagePrefixPath;
    }

    public void setImagePrefixPath(String imagePrefixPath) {
        this.imagePrefixPath = imagePrefixPath;
    }

    public int getNum() {
        return num++;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCutPicturePath() {
        return cutPicturePath;
    }

    public void setCutPicturePath(String cutPicturePath) {
        this.cutPicturePath = cutPicturePath;
    }

    public boolean cutPicture(int x, int y, int width, int height, String savePicture) { // 切割图片
        return IncisionUtil.cutPicture(suffix.substring(1,suffix.length()), cutPicturePath, x, y, width, height, savePicture);
    }

    public String savePicturePath(String pictureName) {
        return imagePrefixPath + pictureName + suffix;
    }

    public boolean rgbEquals() {    // 判断rgb 色素
        return rgb[0] == rgb[1] && rgb[0] == rgb[2];// || rgb[0] < 80
    }*/
}