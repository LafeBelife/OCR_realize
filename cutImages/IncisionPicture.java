package com.the_check.cutImages;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 图片切割
 * 实现图片指定顺序切割。
 * 实现图片图标切割。
 *
 * @author 王保卫
 * @Date 2018-7-7
 */
public class IncisionPicture {
    /*private IncisionUtil incisionUtil = new IncisionUtil();

    *//**
     * 根据传入图片地址，剪切图片 图标。
     *
     * @param iconPath     剪切的图标路径
     * @param saveIconPath 保存图标的路径
     * @return savePath 保存图标的路径
     *//*
    public String incisionIcon(String iconPath, String saveIconPath) {
//        String fileName = "C:\\Users\\Administrator\\Desktop\\aaaaa\\1111_2.jpg";
        if (iconPath.isEmpty() || saveIconPath.isEmpty()) { // 判断不为空
            throw new NullPointerException("剪切图片 或 保存图标路径为 空 !");
        }
        if (!new File(iconPath).exists() || !new File(saveIconPath).getParentFile().exists()) { // 判断图片是否存在
            try {
                throw new FileNotFoundException("剪切图片 或 保存图标路径 不存在!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        BufferedImage sourceImgage = null;
        try {
            sourceImgage = ImageIO.read(new FileInputStream(iconPath));  // 读取图片的地址
        } catch (IOException e) {
            e.printStackTrace();
        }
        String suffix = iconPath.substring(iconPath.lastIndexOf(".") + 1);  // 截取后缀
        assert sourceImgage != null;
        int width = sourceImgage.getWidth();    // 获取图片的 宽度
        int height = sourceImgage.getHeight(); // 获取图片的 高度
        int x = sourceImgage.getMinX();
        saveIconPath = incisionUtil.cutPicture(suffix, iconPath, x, incisionUtil.getInt(height * 0.2), width - 20, incisionUtil.getInt(height * 0.40), saveIconPath);  // 0.65
        return saveIconPath;
    }

    *//**
     * 根据传入图片地址，分割图片。
     *
     * @param picturePath    切割的图片路径
     * @param saveFolderPath 保存的文件夹路径
     * @return saveFolderPath
     *//*
    public String incisionPicture(String picturePath, String saveFolderPath) {
        if (picturePath.isEmpty() || saveFolderPath.isEmpty()) { // 判断不为空
            throw new NullPointerException("剪切图片 或 保存文件夹路径为 空 !");
        }
        if (!new File(picturePath).exists() || !new File(saveFolderPath).exists()) { // 判断图片是否存在
            try {
                throw new FileNotFoundException("剪切图片 或 保存文件夹路径 不存在!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
//        String fileName = "C:\\Users\\Administrator\\Desktop\\_img\\564.jpg";
        int[] rgb = new int[3];
        int[] next_rgb = new int[3];
        BufferedImage sourceImg = null;
        List<Integer> list = new ArrayList<Integer>();// 去除只出现限一次的值
        try {
            sourceImg = ImageIO.read(new FileInputStream(picturePath));  // 读取图片的地址
        } catch (IOException e) {
            e.printStackTrace();
        }
        String suffix = picturePath.substring(picturePath.lastIndexOf(".") + 1);    // 截取图片后缀
        Map<Integer, Integer> map = new IdentityHashMap<>();
        int width = sourceImg.getWidth();    // 获取图片的 宽度
        int height = 1100; // 获取图片的 高度     顶部 132,204，  底部 132,1210
        int minx = 0;
        int miny = 210; // 去顶部

        for (int j = miny; j < height; j++) {
            for (int i = minx; i < width; i++) {
                if (i < 200 || ((i > width / 2) && (i < (width / 2 + 80)))) {
                    int pixel = sourceImg.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    // 此处可能有为录入完的色素，请注意。 此 if 不可直接去除，否则 会出现 无法想象到的混乱现象，除非已解决。
                    if ((rgb[0] == 68 && rgb[1] == 68 && rgb[2] == 68) || (rgb[0] == 40 && rgb[1] == 40 && rgb[2] == 40) || (rgb[0] == 28 && rgb[1] == 28 && rgb[2] == 28)
                            || (rgb[0] == 18 && rgb[1] == 18 && rgb[2] == 18) || (rgb[0] == 20 && rgb[1] == 20 && rgb[2] == 20) || (rgb[0] == 19 && rgb[1] == 19 && rgb[2] == 19)
                            || (rgb[0] == 21 && rgb[1] == 21 && rgb[2] == 21) || (rgb[0] == 25 && rgb[1] == 25 && rgb[2] == 25) || (rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0)
                            || (rgb[0] == 49 && rgb[1] == 49 && rgb[2] == 49) || (rgb[0] == 17 && rgb[1] == 17 && rgb[2] == 17) || (rgb[0] == 41 && rgb[1] == 41 && rgb[2] == 41)
                            || (rgb[0] == 57 && rgb[1] == 57 && rgb[2] == 57) || (rgb[0] == 53 && rgb[1] == 53 && rgb[2] == 53) || (rgb[0] == 79 && rgb[1] == 79 && rgb[2] == 79)
                            || (rgb[0] == 38 && rgb[1] == 38 && rgb[2] == 38) || (rgb[0] == 2 && rgb[1] == 2 && rgb[2] == 2) || (rgb[0] == 50 && rgb[1] == 50 && rgb[2] == 50)
                            || (rgb[0] == 30 && rgb[1] == 30 && rgb[2] == 30) || (rgb[0] == 23 && rgb[1] == 23 && rgb[2] == 23)) {
                        int next_pixel = sourceImg.getRGB(i + 1, j);
                        next_rgb[0] = (next_pixel & 0xff0000) >> 16;
                        next_rgb[1] = (next_pixel & 0xff00) >> 8;
                        next_rgb[2] = (next_pixel & 0xff);
                        if (Arrays.equals(next_rgb, rgb)) {
//                        System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + ","
//                                + rgb[1] + "," + rgb[2] + ")");
                            map.put(i, j);
                            list.add(j);
                        }
                    }
                }
            }
        }
        // 获取 只出现一次的 像素位置
        Object[] ints = list.toArray(new Object[list.size()]);
        List<Integer> list1 = incisionUtil.findNumAppearOnce(ints);
        // 去除 只出现一次的 像素位置
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
//            System.out.println(entry.getKey() + "\t" + entry.getValue());

            for (Object l : list1) {
                if (l.equals(entry.getValue())) {//特别注意：不能使用map.remove(name)  否则会报同样的错误
                    iterator.remove();
                    // 此break 不可去除，不然可能会报 java.lang.IllegalStateException: Entry was removed
                    break;
                }
            }
        }


        int _num = 0;     // 命名用
        int _left_map_last = 0;// map长度的值,截取最后一张图片
        int _right_map_last = 0;
        int _left_up_val = 1;   // 左边上一个 高度 val值
        int _right_up_val = 1;// 右边上一个 高度 val值
        int _left = 0;  // 左边计数器，截取最后一张
        int _right = 0;// 右边计数器，截取最后一张
        // map value去重
        Map<Integer, Integer> _map = incisionUtil.removeRepFromMap(map);
        Collection<Integer> valueCollection = _map.values();
        List<Integer> mapValueList = new ArrayList<>(valueCollection);
        Collections.sort(mapValueList);// 排序

        int _left_count = 0, _right_count = 0;
        for (int count : mapValueList) {
            Iterator<Map.Entry<Integer, Integer>> it = _map.entrySet().iterator();
            while (it.hasNext()) { // map 在遍历时，不容许直接修改，添加元素，否则会报 java.util.ConcurrentModificationException
                Map.Entry<Integer, Integer> entry = it.next();
                if (entry.getValue() == count) {
                    System.out.println(entry.getKey() + "\t" + entry.getValue());
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
                    if (_map.size() == 1) { // 如果 左边 与 右边 y 轴 持平， 则map去重时会把 右边y也去掉，此时需要手动添加一个
                        _map.put(width / 2 + entry.getKey(), entry.getValue());
                        _right_map_last = entry.getValue();
                        _right_count = _left_count = 1;
                        break;
                    }
                }
            }
        }
        Map _map_temp = new IdentityHashMap();
        if (_left_count == 2 && _right_count == 0) { // 判断 6 张图片的
            Iterator<Map.Entry<Integer, Integer>> it = _map.entrySet().iterator();
            while (it.hasNext()) { // map 在遍历时，不容许直接修改，添加元素，否则会报 java.util.ConcurrentModificationException
                Map.Entry<Integer, Integer> entry = it.next();
                if (entry.getValue() > _right_map_last) {
                    _right_map_last = entry.getValue();
                }
                _right_count++;
                _map_temp.put(width / 2 + 10, entry.getValue());
            }
            Iterator<Map.Entry<Integer, Integer>> _it = _map_temp.entrySet().iterator();
            while (_it.hasNext()) { // map 在遍历时，不容许直接修改，添加元素，否则会报 java.util.ConcurrentModificationException
                Map.Entry<Integer, Integer> entry = _it.next();
                System.out.println(entry.getKey() + "\t" + entry.getValue());
                _map.put(entry.getKey(), entry.getValue());
            }
        }


//        ListIterator<Map.Entry<Integer, Integer>> i = new ArrayList<>(resultMap.entrySet()).listIterator(resultMap.size());

        // 截取顶部位置
        String str = saveFolderPath + "\\" + picturePath.substring(picturePath.lastIndexOf("\\") + 1, picturePath.lastIndexOf(".")) + "_";
        String aa = incisionUtil.cutPicture(suffix, picturePath, 0, 0, width, 204, str + (_num++) + "." + suffix); // 204 顶部空白区域
        System.out.println(aa + "\n");


        for (int count : mapValueList) {
            Iterator<Map.Entry<Integer, Integer>> iterator1 = _map.entrySet().iterator();
            while (iterator1.hasNext()) {
                Map.Entry<Integer, Integer> entry = iterator1.next();
                if (entry.getValue() == count) {

                    System.out.println(entry.getKey() + "\t" + entry.getValue());
                    if (entry.getKey() < width / 2) {
                        if (_left++ == 0) {
                            System.out.println("_left" + _num);
                            // 截取左边 第一张
                            incisionUtil.cutPicture(suffix, picturePath, 130, 204, width / 2 - 130, entry.getValue() - 204, str + (_num++) + "." + suffix); // 204 顶部空白区域
                            _left_up_val = entry.getValue();
                        }
                        if (_left_count > 1 && entry.getValue() != _left_up_val) {
                            System.out.println("_left_map_num else" + _num);
                            // 截取中间位置
                            incisionUtil.cutPicture(suffix, picturePath, 130, _left_up_val, width / 2 - 130, entry.getValue() - _left_up_val, str + (_num++) + "." + suffix);
                            _left_up_val = entry.getValue();
                        }

                        if (_left_map_last == entry.getValue()) {
                            System.out.println("_left_map_num" + _num);
                            // 截取左边 最后一张
                            incisionUtil.cutPicture(suffix, picturePath, 130, entry.getValue(), width / 2 - 130, 1208 - entry.getValue(), str + (_num++) + "." + suffix);
                        }
                    }
                }
            }
        }

        System.out.println();

        for (int count : mapValueList) {
            Iterator<Map.Entry<Integer, Integer>> iterator2 = _map.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<Integer, Integer> entry = iterator2.next();

                if (entry.getValue() == count) {
                    System.out.println(entry.getKey() + "\t" + entry.getValue());
                    if (entry.getKey() > width / 2) {
                        if (_right++ == 0) {
                            System.out.println("_right" + _num);
                            // 截取右边 第一张
                            incisionUtil.cutPicture(suffix, picturePath, width / 2 + 10, 204, width / 2 - 130, entry.getValue() - 204, str + (_num++) + "." + suffix);
                            _right_up_val = entry.getValue();
                        }
                        if (_right_count > 1 && entry.getValue() != _right_up_val) {
                            // 截取 图片中间位置
                            System.out.println("_right_map_num else" + _num);
                            incisionUtil.cutPicture(suffix, picturePath, width / 2 + 10, _right_up_val, width / 2 - 130, entry.getValue() - _right_up_val, str + (_num++) + "." + suffix);
                            _right_up_val = entry.getValue();
                        }

                        if (_right_map_last == entry.getValue()) {
                            System.out.println("_right_map_num" + _num);
                            // 截取右边 最后一张
                            incisionUtil.cutPicture(suffix, picturePath, width / 2 + 10, entry.getValue(), width / 2 - 130, 1208 - entry.getValue(), str + (_num++) + "." + suffix);
                        }
                    }
                }
            }
        }
        System.out.println();
        return saveFolderPath;
    }*/
}