package com.the_check.cutImages;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class IncisonPicture {
    // 图片 切割工具类
    private IncisionUtil incisionUtil = new IncisionUtil();
    // 图片 后缀
    String suffix;
    // 保存切割图片的前缀路径
    String imagePrefixPath;
    // 读取图片 信息 流
    BufferedImage bufferedImage = null;
    int width;    // 获取图片的 宽度
    int height; // 获取图片的 高度     顶部 132,204，  底部 132,1210
    int _left_count, _right_count;
    int _num;     // 命名用
    int _left_map_last;// map长度的值,截取最后一张图片
    int _right_map_last;
    int _left_up_val;   // 左边上一个 高度 val值
    int _right_up_val;// 右边上一个 高度 val值
    int _left;  // 左边计数器，截取最后一张
    int _right;// 右边计数器，截取最后一张
    int[] rgb = new int[3];
    int[] next_rgb = new int[3];

    {
        height = 1100;
        _num = _left_count = _right_count = _left_map_last = _right_map_last = _left = _right = width = 0;
        _right_up_val = _left_up_val = 1;   // 左边上一个 高度 val值
    }

    /**
     * 根据传入图片地址，剪切图片 图标。
     *
     * @param iconPath     剪切的图标路径
     * @param saveIconPath 保存图标的路径
     * @return savePath 保存图标的路径
     */
    public String incisionIcon(String iconPath, String saveIconPath) {
        if (iconPath.isEmpty() || saveIconPath.isEmpty()) { // 判断不为空
            throw new NullPointerException("剪切图片 或 保存图标路径为 空 !");
        }
        try {
            if (!new File(iconPath).exists() || !new File(saveIconPath).getParentFile().exists()) { // 判断图片是否存在
                throw new FileNotFoundException("剪切图片 或 保存图标路径 不存在!");
            }
            bufferedImage = ImageIO.read(new FileInputStream(iconPath));  // 读取图片的地址
            if (bufferedImage.getHeight() < 250) {
                return saveIconPath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        suffix = iconPath.substring(iconPath.lastIndexOf(".") + 1);  // 截取 图片 后缀
        assert bufferedImage != null;   // 断言 不为 null
        // 获取图片的 宽度 // 获取图片的 高度// 剪切图标 的 x 轴
        int width = bufferedImage.getWidth(), height = bufferedImage.getHeight(), x = bufferedImage.getMinX(), y = bufferedImage.getMinY();
        Map<Integer, Integer> map = new IdentityHashMap<>();
        List<Integer> list = new ArrayList<>();// 去除只出现限一次的值
        int top_y = 0;
        Map<Integer, Integer> top_map = new IdentityHashMap<>();
        for (int j = incisionUtil.getInt(height * 0.1); j < height; j++) {
            for (int i = x; i < 50; i++) {
                if (i < incisionUtil.getInt(height * 0.2)) {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    if (rgb[0] == rgb[1] && rgb[0] == rgb[1] && rgb[0] < 100 && j > incisionUtil.getInt(height * 0.33)) {
                        j = height;
                        break;
                    }
                    if (rgb[0] == rgb[1] && rgb[0] == rgb[2] && rgb[0] == 255) {
                        int next_pixel = bufferedImage.getRGB(i + 1, j);
                        next_rgb[0] = (next_pixel & 0xff0000) >> 16;
                        next_rgb[1] = (next_pixel & 0xff00) >> 8;
                        next_rgb[2] = (next_pixel & 0xff);
                        if (Arrays.equals(next_rgb, rgb)) {
                            map.clear();
                            map.put(i, j);
                            list.add(j);
                            if (i > 8 && top_y == 0) {
                                top_map.clear();
                                top_map.put(i, j);
                                top_y++;
                            }
                        }
                    }
                    if (j < incisionUtil.getInt(height * 0.33) && rgb[0] < 150) {
                        top_y = 0;
                    }
                }
            }
        }

        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        int maxValue = 0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
            }
        }
        Iterator<Map.Entry<Integer, Integer>> it = top_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> entry = it.next();
            if (entry.getValue() > top_y) {
                top_y = entry.getValue();
            }
        }
        return incisionUtil.cutPicture(suffix, iconPath, x, top_y, width - 20, maxValue - top_y, saveIconPath);
    }

    /**
     * 根据传入图片地址，分割图片。
     *
     * @param picturePath    切割的图片路径
     * @param saveFolderPath 保存的文件夹路径
     * @return saveFolderPath
     */
    public String incisionPicture(String picturePath, String saveFolderPath) {
        if (picturePath.isEmpty() || saveFolderPath.isEmpty()) { // 判断 不为空
            throw new NullPointerException("剪切图片 或 保存文件夹路径为 空 !");
        }
        try {
            if (!new File(picturePath).exists() || !new File(saveFolderPath).exists()) { // 判断 图片 是否存在
                throw new FileNotFoundException("剪切图片 或 保存文件夹路径 不存在!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }


        List<Integer> list = new ArrayList<>();// 去除只出现限一次的值
        try {
            bufferedImage = ImageIO.read(new FileInputStream(picturePath));  // 读取图片的地址
        } catch (IOException e) {
            e.printStackTrace();
        }
        suffix = picturePath.substring(picturePath.lastIndexOf(".") + 1);    // 截取图片后缀
        Map<Integer, Integer> left_map = new IdentityHashMap<>();
        Map<Integer, Integer> right_map = new IdentityHashMap<>();
        width = bufferedImage.getWidth();    // 获取图片的 宽度
        int minx = 0;
        int miny = 210; // 去顶部

        for (int j = miny; j < height; j++) {
            for (int i = minx; i < width; i++) {
                if (i < 200 || ((i > width / 2 + 6) && (i < (width / 2 + 50)))) {
                    int pixel = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;  // 获取色素
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    // 此处可能有为录入完的色素，请注意。 此 if 不可直接去除，否则 会出现 无法想象到的混乱现象，除非已解决。
                 /*   if ((rgb[0] == 68 && rgb[1] == 68 && rgb[2] == 68) || (rgb[0] == 40 && rgb[1] == 40 && rgb[2] == 40) || (rgb[0] == 28 && rgb[1] == 28 && rgb[2] == 28)
                            || (rgb[0] == 18 && rgb[1] == 18 && rgb[2] == 18) || (rgb[0] == 20 && rgb[1] == 20 && rgb[2] == 20) || (rgb[0] == 19 && rgb[1] == 19 && rgb[2] == 19)
                            || (rgb[0] == 21 && rgb[1] == 21 && rgb[2] == 21) || (rgb[0] == 25 && rgb[1] == 25 && rgb[2] == 25) || (rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0)
                            || (rgb[0] == 49 && rgb[1] == 49 && rgb[2] == 49) || (rgb[0] == 17 && rgb[1] == 17 && rgb[2] == 17) || (rgb[0] == 41 && rgb[1] == 41 && rgb[2] == 41)
                            || (rgb[0] == 57 && rgb[1] == 57 && rgb[2] == 57) || (rgb[0] == 53 && rgb[1] == 53 && rgb[2] == 53) || (rgb[0] == 79 && rgb[1] == 79 && rgb[2] == 79)
                            || (rgb[0] == 38 && rgb[1] == 38 && rgb[2] == 38) || (rgb[0] == 2 && rgb[1] == 2 && rgb[2] == 2) || (rgb[0] == 50 && rgb[1] == 50 && rgb[2] == 50)
                            || (rgb[0] == 30 && rgb[1] == 30 && rgb[2] == 30) || (rgb[0] == 23 && rgb[1] == 23 && rgb[2] == 23) || (rgb[0] == 62 && rgb[1] == 62 && rgb[2] == 62)
                            || (rgb[0] == 33 && rgb[1] == 33 && rgb[2] == 33) ) {*/
                    if (rgb[0] == rgb[1] && rgb[0] == rgb[2] && rgb[0] < 123) { //|| (rgb[0] == 122 && rgb[1] == 122 && rgb[2] == 122)    rgb[0] < 80
                        int next_pixel = bufferedImage.getRGB(i + 1, j);
                        next_rgb[0] = (next_pixel & 0xff0000) >> 16;
                        next_rgb[1] = (next_pixel & 0xff00) >> 8;
                        next_rgb[2] = (next_pixel & 0xff);
                        if (Arrays.equals(next_rgb, rgb)) {
                            if (i < 200) {
                                left_map.put(i, j);
                            } else {
                                right_map.put(i, j);
                            }
                            list.add(j);    // 去除重复值使用
                        }
                    }
                }
            }
        }
        // 获取 只出现一次的 像素位置
        Object[] ints = list.toArray(new Object[list.size()]);
        List<Integer> list1 = incisionUtil.findNumAppearOnce(ints);
        // 去除 只出现一次的 像素位置
        Iterator<Map.Entry<Integer, Integer>> iterator = left_map.entrySet().iterator();
        removeMap(iterator, list1);

        iterator = right_map.entrySet().iterator();
        removeMap(iterator, list1);


        // map value去重
        Map<Integer, Integer> _left_map = incisionUtil.removeRepFromMap(left_map);
        Map<Integer, Integer> _right_map = incisionUtil.removeRepFromMap(right_map);
        List<Integer> _left_map_ValueList = new ArrayList<>(_left_map.values());
        Collections.sort(_left_map_ValueList);// 排序
        List<Integer> _right_map_ValueList = new ArrayList<>(_right_map.values());
        Collections.sort(_right_map_ValueList);// 排序


        sortLastMap(_left_map_ValueList, _left_map);
        sortLastMap(_right_map_ValueList, _right_map);

        // 截取顶部位置  + "top_"
        imagePrefixPath = saveFolderPath + "\\" + picturePath.substring(picturePath.lastIndexOf("\\") + 1, picturePath.lastIndexOf(".")) + "_";
        String picPath = incisionUtil.cutPicture(suffix, picturePath, 0, 0, width, 204, imagePrefixPath + (_num++) + "." + suffix); // 204 顶部空白区域
        System.out.println(picPath + "\n");

        for (int count : _left_map_ValueList) {
            Iterator<Map.Entry<Integer, Integer>> iterator1 = _left_map.entrySet().iterator();
            while (iterator1.hasNext()) {
                Map.Entry<Integer, Integer> entry = iterator1.next();
                if (entry.getValue() == count) {
                    if (entry.getKey() < width / 2) {
                        if (_left++ == 0) {
                            // 截取左边 第一张
                            incisionUtil.cutPicture(suffix, picturePath, 130, 204, width / 2 - 130, entry.getValue() - 204, imagePrefixPath + (_num++) + "." + suffix); // 204 顶部空白区域
                            _left_up_val = entry.getValue();
                        }
                        if (_left_count > 1 && (entry.getValue() - _left_up_val) > 50) {
                            // 截取中间位置
                            incisionUtil.cutPicture(suffix, picturePath, 130, _left_up_val, width / 2 - 130, entry.getValue() - _left_up_val, imagePrefixPath + (_num++) + "." + suffix);

                        }
                        if (_left_map_last == entry.getValue()) {
                            // 截取左边 最后一张
                            incisionUtil.cutPicture(suffix, picturePath, 130, (entry.getValue() - _left_up_val > 50 ? entry.getValue() : _left_up_val), width / 2 - 130, 1208 - (entry.getValue() - _left_up_val > 50 ? entry.getValue() : _left_up_val), imagePrefixPath + (_num++) + "." + suffix);
                        }
                        _left_up_val = entry.getValue();
                    }
                }
            }
        }
        for (int count : _right_map_ValueList) {
            Iterator<Map.Entry<Integer, Integer>> iterator2 = _right_map.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<Integer, Integer> entry = iterator2.next();
                if (entry.getValue() == count) {
                    if (entry.getKey() > width / 2) {
                        if (_right++ == 0) {
                            // 截取右边 第一张
                            incisionUtil.cutPicture(suffix, picturePath, width / 2 + 10, 204, width / 2 - 130, entry.getValue() - 204, imagePrefixPath + (_num++) + "." + suffix);
                            _right_up_val = entry.getValue();
                        }
                        if (_right_count > 1 && (entry.getValue() - _right_up_val) > 50) {
                            // 截取 图片中间位置
                            incisionUtil.cutPicture(suffix, picturePath, width / 2 + 10, _right_up_val, width / 2 - 130, entry.getValue() - _right_up_val, imagePrefixPath + (_num++) + "." + suffix);
                        }
                        if (_right_map_last == entry.getValue()) {
                            // 截取右边 最后一张
                            incisionUtil.cutPicture(suffix, picturePath, width / 2 + 10, (entry.getValue() - _right_up_val > 50 ? entry.getValue() : _right_up_val), width / 2 - 130, 1208 - (entry.getValue() - _right_up_val > 50 ? entry.getValue() : _right_up_val), imagePrefixPath + (_num++) + "." + suffix);
                        }
                        _right_up_val = entry.getValue();
                    }
                }
            }
        }
        // 截取尾部位置 stern   + "stern_"
        incisionUtil.cutPicture(suffix, picturePath, 0, 1208, width, bufferedImage.getHeight() - 1208, imagePrefixPath + (_num++) + "." + suffix); // 尾部
        return saveFolderPath;
    }

    /**
     *
     */
    public void removeMap(Iterator<Map.Entry<Integer, Integer>> iterator, List list1) {
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            for (Object l : list1) {
                if (l.equals(entry.getValue())) {//特别注意：不能使用map.remove(name)  否则会报同样的错误
                    iterator.remove();
                    // 此break 不可去除，不然可能会报 java.lang.IllegalStateException: Entry was removed
                    break;
                }
            }
        }
    }

    public void sortLastMap(List<Integer> mapValueList, Map<Integer, Integer> _map) {
        for (int count : mapValueList) {
            Iterator<Map.Entry<Integer, Integer>> it = _map.entrySet().iterator();
            while (it.hasNext()) { // map 在遍历时，不容许直接修改，添加元素，否则会报 java.util.ConcurrentModificationException
                Map.Entry<Integer, Integer> entry = it.next();
                if (entry.getValue() == count) {
                    if (entry.getKey() < width / 2) {
                        if (entry.getValue() > _left_map_last) {
                            _left_map_last = entry.getValue();
                        }
                        _left_count++;
                    } else {
                        if (entry.getValue() > _right_map_last) {
                            _right_map_last = entry.getValue();
                        }
                        _right_count++;
                    }
                }
            }
        }
    }
}