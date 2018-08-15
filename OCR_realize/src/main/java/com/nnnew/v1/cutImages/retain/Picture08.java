//package com.nnnew.v1.cutImages.retain;
//
//import com.nnnew.v1.cutImages.constant.PictureNumber;
//import com.nnnew.v1.cutImages.util.IncisionUtil;
//
//import java.awt.image.BufferedImage;
//
///**
// * 图片类
// *
// * @author 王保卫 2018年7月27日11:29:37
// */
//public class Picture03 {
//    private int[] minx = {200, 200};   // 起始 x
//    private int centreX;    // 中间 y 轴，x坐标
//    private BufferedImage bufferedImage;
//    private String picturePath; // 切割图片路径
//    private String saveFolderPath;  // 保存图片路径
//
//    public Picture03(String picturePath, String saveFolderPath, BufferedImage bufferedImage) {
//        this.picturePath = picturePath;
//        this.bufferedImage = bufferedImage;
//        this.saveFolderPath = saveFolderPath;
//
//    }
//
//    private int getTopOrBottomX(int startX, int j, int bottomMinX) {
//        int count = 0;
//        for (int i = startX; i < PictureNumber.PICTURE_X_CUT; i++) { // 200 为 色素到x起始位置。
//            if (isRgbEquals(i, j)
//                    && count++ == PictureNumber.PICTURE_COUNT
//                    && (i - PictureNumber.PICTURE_COUNT) < bottomMinX) {
//                bottomMinX = i - PictureNumber.PICTURE_COUNT;
//                return bottomMinX;
//            }
//        }
//        return bottomMinX;
//    }
//
//    public int[] getMinx() {  // 获取顶部 和 尾部 x
//        // 获取 顶部 x
//        for (int j = bufferedImage.getMinY() + 30; j < PictureNumber.PICTURE_Y_FIRST_HEIGHT; j++) {
//            minx[0] = getTopOrBottomX(bufferedImage.getMinX(), j, minx[0]);
//        }// 获取底部 x 10 防止bufferedImage 获取最大高度，出现越界。
//        for (int j = bufferedImage.getHeight() - 30; j > PictureNumber.PICTURE_Y_LAST_HEIGHT; j--) {
//            minx[1] = getTopOrBottomX(bufferedImage.getMinX(), j, minx[1]);
//        }
//        return minx;
//    }
//
//    public int getCentreX() {   // 获取中间x 位置
//        int count;
//        for (int i = bufferedImage.getWidth() / 2 - PictureNumber.PICTURE_X_CENTRE_OFFSET;// 200 为 色素到x起始位置。
//             i < bufferedImage.getWidth() / 2 + PictureNumber.PICTURE_X_CENTRE_OFFSET; i++) {
//            count = 0;
//            for (int j = bufferedImage.getMinY(); j < PictureNumber.PICTURE_X_CENTRE; j++) { // 250
//                if (isRgbEquals(i, j)
//                        && count++ == PictureNumber.PICTURE_COUNT) {
//                    centreX = i;
//                    return centreX;
//                }
//            }
//        }
//        return centreX;
//    }
//
//
//    public String getPicturePath() {
//        return picturePath;
//    }
//
//    private String getImagePrefixPath() {// 图片前缀
//        // 截取 以 “.” 为前缀命名， 不包含 “.”
//        return saveFolderPath
//                + picturePath.substring(picturePath.lastIndexOf("\\"),
//                picturePath.lastIndexOf("."));
//    }
//
//    public String getSuffix() {// 图片后缀
//        // 截取图片 以“.” 后缀 （包含 “.”）
//        return IncisionUtil.getStringSuffix(picturePath);
//    }
//
//    public boolean cutPicture(int x, int y, int width, int height, String pictureJointName) {// 切割图片
//        return IncisionUtil.cutPicture(getSuffix().substring(1, getSuffix().length()),
//                picturePath, x, y, width, height,
//                getImagePrefixPath() + "_" + pictureJointName + getSuffix());
//    }
//
//    public boolean isRgbEquals(int i, int j) { // 判断 rgb 像素
//        int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
//        int nextPixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
//        return pixel < PictureNumber.PICTURE_X_CUT && nextPixel < PictureNumber.PICTURE_X_CUT;
//    }
//
//    /*private boolean isIconRgbEquals(int i, int j) { // 判断 rgb 像素
//        int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
//        int nextPixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
//        return pixel < 130 && nextPixel < 130;  // 不包含中国商标的色素
//    }
//
//    private static Integer j = 0;
//    private static int num = 0;
//    private static int numLock = 0;
//
//    public int[] getIconTopYAndBottomY() {
//        // 获取图标 最顶部 绝对位置 ,获取图标y , 获取图标最大 高度值.
//        int[] y = {0, 0};
//        num = 0;
//        int numJ = 0;
//        for (j = 10; j < IncisionUtil.getInt(bufferedImage.getHeight() * 0.35); j++) {
//            getIconY(20);
//            if (num == 2 && numLock == 0) { // 此处判断商标是否存在，不存在返回
//                y[0] = j;
//                numLock = 1;
//            }
//            if (num == 3 && numLock == 0) {
//                for (int i = 15; i < 80; i++) {  // 50 为 0 到 图标位置 第一次 30
//                    int pixel = (bufferedImage.getRGB(i, j - 10) & 0xff0000) >> 16;  // 获取色素
//                    int nextPixel = (bufferedImage.getRGB(i, j - 5) & 0xff0000) >> 16;  // 获取色素
//                    if (pixel > 200 && nextPixel > 200) {
//                        if (numJ++ == 30) {
//                            y[0] = j;
//                            j = bufferedImage.getHeight();
//                            break;
//                        }
//                    }
//                }
//                numLock = 1;
//                break;
//            }
//        }
//        numJ = num = 0;
//        for (j = bufferedImage.getHeight() - 10;
//             j > IncisionUtil.getInt(bufferedImage.getHeight() * 0.35); j--) {
//            getIconY(-20);
//            if (num == 1 && numLock == 0) { // 此处判断地址是否存在，不存在返回
//                numLock = 1;
//                for (int i = 15; i < 80; i++) {
//                    int pixel = (bufferedImage.getRGB(i, j + 10) & 0xff0000) >> 16;  // 获取色素
//                    int nextPixel = (bufferedImage.getRGB(i, j + 5) & 0xff0000) >> 16;  // 获取色素
//                    if (pixel > 200 && nextPixel > 200) {
//                        if (numJ++ == 30) {
//                            break;
//                        }
//                    } else {
//                        numJ = 0;
//                    }
//                }
//                if (numJ < 30) {
//                    num = 2;
//                }
//            }
//            if (num == 3 && numLock == 0) {
//                y[1] = j;
//                numLock = 1;
//                break;
//            }
//        }
////        System.out.println(y[0] + "\t" + y[1]);
//        return y;
//    }
//
//    private void getIconY(int numJ) {
//        int temp = 1;
//        for (int i = 15; i < 80; i++) {  // 50 为 0 到 图标位置 第一次 30
//            if (isIconRgbEquals(i, j)) {// 去除 顶部 部分 rgb 使用
//                temp = 0;
//            }
//        }
//        if (temp++ == 0) { // 三次结束循环
//            j += numJ;
//            num++;
//            numLock = 0;
//        }
//    }*/
//    public boolean isIconRgbEquals(int i, int j) { // 判断 rgb 像素
//        int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
//        int nextPixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取下一个色素
//        return pixel < 180 && nextPixel < 180;  // 不包含中国商标的色素
//    }
//
//    private static int top_num = 0;    // 计数器
//    private static int[] y = {0, 0};
//
//    public int[] getIconTopYAndBottomY() {
//        // 获取图标 最顶部 绝对位置 ,获取图标y , 获取图标最大 高度值.
//        int upVal = 0;
//        top_num = 0;
//        for (int j = IncisionUtil.getInt(bufferedImage.getHeight() * 0.1);
//             j < IncisionUtil.getInt(bufferedImage.getHeight() * 0.35); j++) {
//            y[0] = getIconY(j, y[0]);
//        }
//        top_num = 0;
//        for (int j = IncisionUtil.getInt(bufferedImage.getHeight() * 0.9);
//             j > IncisionUtil.getInt(bufferedImage.getHeight() * 0.35); j--) {
//            y[1] = getIconY(j, y[1]);
//            if (y[1] - y[0] < 100) {
//                y[1] = upVal;
//                break;
//            }
//            upVal = y[1];
//        }
////        System.out.println(y[0] + "\t" + y[1]);
//        y[0] = y[0] + 15;
//        return y;
//    }
//
//    private int getIconY(int j, int val) {
//        for (int i = PictureNumber.PICTURE_COUNT; i < 50; i++) {  // 50 为 0 到 图标位置
//            int pixel = (bufferedImage.getRGB(i, j) & 0xff0000) >> 16;  // 获取色素
//            int nextPixel = (bufferedImage.getRGB(i + 1, j) & 0xff00) >> 8;  // 获取色素
//            if (pixel == 255 && nextPixel == 255 && top_num++ == PictureNumber.PICTURE_COUNT) {
//                val = j;//&& (j - val) > PictureNumber.PICTURE_COUNT
//            }
//            if (isIconRgbEquals(i, j)) {// 去除 顶部 部分 rgb 使用
//                top_num = 0;
//            }
//        }
//        return val;
//    }
//}