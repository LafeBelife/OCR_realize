package com.nnnew.v1.cutImages;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
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
    /**
     * 根据传入图片地址，剪切图片 图标。
     *
     * @param filePath 剪切的文件路径
     * @param savePath 保存图标的路径
     * @return savePath 保存图标的路径
     */
    public String incisionIcon(String filePath, String savePath) {
//        String fileName = "C:\\Users\\Administrator\\Desktop\\aaaaa\\1111_2.jpg";
        if (filePath.isEmpty() || savePath.isEmpty()) { // 判断不为空
            throw new NullPointerException("剪切图片 或 保存图标路径为 空 !");
        }
        if (!new File(filePath).exists() || !new File(savePath).exists()) { // 判断图片是否存在
            try {
                throw new FileNotFoundException("剪切图片 或 保存图标路径 不存在!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        BufferedImage sourceImgage = null;
        try {
            sourceImgage = ImageIO.read(new FileInputStream(filePath));  // 读取图片的地址
        } catch (IOException e) {
            e.printStackTrace();
        }
        String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);  // 截取后缀
        assert sourceImgage != null;
        int width = sourceImgage.getWidth();    // 获取图片的 宽度
        int height = sourceImgage.getHeight(); // 获取图片的 高度
        int x = sourceImgage.getMinX();
        if (height < 400) {
            savePath = cutPicture(suffix, filePath, x, getInt(height * 0.2), width - 20, getInt(height * 0.65), savePath);
        } else {
            savePath = cutPicture(suffix, filePath, x, getInt(height * 0.2), width - 20, getInt(height * 0.50) - 20, savePath);
        }
        return savePath;
    }

    /**
     * 根据传入图片地址，分割图片。
     *
     * @param picturePath 切割的文件路径
     * @param folderPath  保存的文件夹路径
     * @return folderPath
     */
    public String incisionPicture(String picturePath, String folderPath) {
        if (picturePath.isEmpty() || folderPath.isEmpty()) { // 判断不为空
            throw new NullPointerException("剪切图片 或 保存文件夹路径为 空 !");
        }
        if (!new File(picturePath).exists() || !new File(folderPath).exists()) { // 判断图片是否存在
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
        int height = 1210; // 获取图片的 高度     顶部 132,204，  底部 132,1210
        int minx = 0;
        int miny = 132; // 去顶部

        for (int j = miny; j < height; j++) {
            for (int i = minx; i < width; i++) {
                if (i < 200 || ((i > width / 2) && (i < (width / 2 + 80)))) {
                    int pixel = sourceImg.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    if ((rgb[0] == 68 && rgb[1] == 68 && rgb[2] == 68) || (rgb[0] == 40 && rgb[1] == 40 && rgb[2] == 40) || (rgb[0] == 28 && rgb[1] == 28 && rgb[2] == 28)
                            || (rgb[0] == 18 && rgb[1] == 18 && rgb[2] == 18) || (rgb[0] == 20 && rgb[1] == 20 && rgb[2] == 20)
                            || (rgb[0] == 21 && rgb[1] == 21 && rgb[2] == 21) || (rgb[0] == 25 && rgb[1] == 25 && rgb[2] == 25)) {
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
        List<Integer> list1 = FindNumAppearOnce(ints);
        // 去除 只出现一次的 像素位置
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();


        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
//            System.out.println(entry.getKey() + "\t" + entry.getValue());

            for (Object l : list1) {
                if (l.equals(entry.getValue())) {//特别注意：不能使用map.remove(name)  否则会报同样的错误
                    iterator.remove();
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
        Map<Integer, Integer> _map = RemoveRepFromMap(map);

        // map value 排序
        Map<Integer, Integer> resultMap = sortMapByValue(_map);
        ListIterator<Map.Entry<Integer, Integer>> i = new ArrayList<>(resultMap.entrySet()).listIterator(resultMap.size());
        while (i.hasPrevious()) {
            System.out.println(resultMap.size());
            Map.Entry<Integer, Integer> entry = i.previous();
            System.out.println(entry.getKey() + "\t" + entry.getValue());
            if (entry.getKey() < width / 2) {
                _left_map_last = entry.getValue();
            } else {
                _right_map_last = entry.getValue();
            }
            if (resultMap.size() == 1) { // 如果 左边 与 右边 y 轴 持平， 则map去重时会把 右边y也去掉，此时需要手动添加一个
                resultMap.put(width / 2 + entry.getKey(), entry.getValue());
                _right_map_last = entry.getValue();
            }
        }

        System.out.println(_right_map_last);
        System.out.println();
        // 截取顶部位置


        cutPicture(suffix, picturePath, 0, 0, width, 204, folderPath + new File(picturePath).getName().substring(picturePath.lastIndexOf(".") + 1) + "_" + (_num++) + suffix); // 204 顶部空白区域

        ListIterator<Map.Entry<Integer, Integer>> listIterator = new ArrayList<>(resultMap.entrySet()).listIterator(resultMap.size());
        while (listIterator.hasPrevious()) {
            Map.Entry<Integer, Integer> entry = listIterator.previous();
            System.out.println(entry.getKey() + "\t" + entry.getValue());
            if (entry.getKey() < width / 2) {
                if (_left++ == 0) {
                    System.out.println("_left" + _num);
                    // 截取左边 第一张
                    cutPicture(suffix, picturePath, 144 - 10, 204, width / 2 - 144 + 20, entry.getValue() - 204, folderPath + new File(picturePath).getName().substring(picturePath.lastIndexOf(".") + 1) + "_" + (_num++) + suffix); // 204 顶部空白区域
                }
                if (_left_map_last == entry.getValue()) {
                    System.out.println("_left_map_num" + _num);
                    // 截取左边 最后一张
                    cutPicture(suffix, picturePath, entry.getKey() - 10, entry.getValue(), width / 2 - 144 + 20, 1210 - entry.getValue(), folderPath + new File(picturePath).getName().substring(picturePath.lastIndexOf(".") + 1) + "_" + (_num++) + suffix);
                } else {
                    System.out.println("_left_map_num else" + _num);
                    cutPicture(suffix, picturePath, entry.getKey() - 10, entry.getValue(), width / 2 - 144 + 20, entry.getValue() - _left_up_val - 204 - 20, folderPath + new File(picturePath).getName().substring(picturePath.lastIndexOf(".") + 1) + "_" + (_num++) + suffix);
                    _left_up_val = entry.getValue();
                }
            }
        }
        listIterator = new ArrayList<>(resultMap.entrySet()).listIterator(resultMap.size());
        while (listIterator.hasPrevious()) {
            Map.Entry<Integer, Integer> entry = listIterator.previous();
            System.out.println(entry.getKey() + "\t" + entry.getValue() + "\t" + width / 2);
            if (entry.getKey() > width / 2) {
                if (_right++ == 0) {
                    System.out.println("_right" + _num);
                    // 截取右边 第一张
                    cutPicture(suffix, picturePath, width / 2 + 10, 204, width / 2 - 144 + 20, entry.getValue() - 204 + 20, folderPath + new File(picturePath).getName().substring(picturePath.lastIndexOf(".") + 1) + "_" + (_num++) + suffix);
                }
                if (_right_map_last == entry.getValue()) {
                    System.out.println("_right_map_num" + _num);
                    // 截取右边 最后一张
                    cutPicture(suffix, picturePath, width / 2 + 10, entry.getValue(), width / 2 - 144 + 20, 1210 - entry.getValue(), folderPath + new File(picturePath).getName().substring(picturePath.lastIndexOf(".") + 1) + "_" + (_num++) + suffix);
                } else {
                    System.out.println("_right_map_num else" + _num);
                    cutPicture(suffix, picturePath, width / 2 + 10, entry.getValue(), width / 2 - 144 + 20, entry.getValue() - _right_up_val - 204 - 20, folderPath + new File(picturePath).getName().substring(picturePath.lastIndexOf(".") + 1) + "_" + (_num++) + suffix);
                    _right_up_val = entry.getValue();
                }
            }
        }
        return folderPath;
    }

    /**
     * 获取只出现一次的值
     *
     * @param array
     * @return ArrayList<Integer>
     */
    private static ArrayList<Integer> FindNumAppearOnce(Object[] array) {

        ArrayList<Integer> arr = new ArrayList<>();
        Arrays.sort(array);// 排序
        for (int i = 0, len = array.length; i < len; i++) {
            if (i == len - 1 && !array[i].equals(array[i - 1])) {
                arr.add((Integer) array[i]);
            } else if (i == 0 && !array[i].equals(array[i + 1])) {
                arr.add((Integer) array[i]);
            } else {
                if (i != 0 && i != len - 1 && !array[i].equals(array[i - 1]) && !array[i].equals(array[i + 1])) {
                    arr.add((Integer) array[i]);
                }
            }
        }
        return arr;
    }

    /**
     * 去除重复map值
     *
     * @param map
     * @return Map<Integer   ,       Integer>
     */
    private static Map<Integer, Integer> RemoveRepFromMap(Map<Integer, Integer> map) {

        Set<Map.Entry<Integer, Integer>> set = map.entrySet();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(set);

        list.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            //重载compare函数  对list集合进行排序，根据value值进行排序，
            public int compare(Map.Entry<Integer, Integer> entry1, Map.Entry<Integer, Integer> entry2) {
                return entry1.getValue().hashCode() - entry2.getValue().hashCode();
            }
        });

        for (int i = 0; i < list.size(); i++) {//删除重复的元素
            Integer key = list.get(i).getKey();
            Integer value = list.get(i).getValue();

            int j = i + 1;//map中的下一个
            if (j < list.size()) {
                Integer next_key = list.get(j).getKey();
                Integer next_value = list.get(j).getValue();

                if (value.equals(next_value)) {
                    if (key.hashCode() < next_key.hashCode()) {
                        map.remove(next_key);
                        list.remove(j);
                    } else {
                        map.remove(key);
                        list.remove(i);
                    }
                    i--;
                }
            }
        }
        return map;
    }

    /**
     * 功能描述：对图片裁剪，并把裁剪完蛋新图片保存 。
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
    private static String cutPicture(String suffix, String picturePath, int x, int y, int width,
                                     int height, String savePicturePath) {
        ImageInputStream imageInputStream = null;
        File file = new File(savePicturePath);
        try {
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix); // 迭代器读取 后缀,获取格式
            ImageReader reader = null; // 创建reader  读取
            while (it.hasNext()) {
                reader = it.next();
            }
            imageInputStream = ImageIO.createImageInputStream(new FileInputStream(picturePath)); // 获取图片流
            assert reader != null;
            reader.setInput(imageInputStream, true); // 设置读取属性

            ImageReadParam imageReadParam = reader.getDefaultReadParam();
            imageReadParam.setSourceRegion(new Rectangle(x, y, width, height)); // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            BufferedImage bi = reader.read(0, imageReadParam);
            ImageIO.write(bi, suffix, file); // 保存新图片  png形式保存


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (imageInputStream != null) {
                try {
                    imageInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return savePicturePath;
        }
    }

    /**
     * 使用 Map按value进行排序
     *
     * @param oriMap
     * @return Map<Integer                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Integer>
     */
    private static Map<Integer, Integer> sortMapByValue(Map<Integer, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<Integer, Integer> sortedMap = new IdentityHashMap<>();
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(
                oriMap.entrySet());
        entryList.sort(new MapValueComparator());

        Iterator<Map.Entry<Integer, Integer>> iter = entryList.iterator();
        Map.Entry<Integer, Integer> tmpEntry;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    /**
     * 把 double 转换成 int
     * @param number
     * @return int
     */

    private static int getInt(double number) {
        // 四舍五入把double转化int整型，0.5进一，小于0.5不进一
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }
}

/**
 * 重写Comparator compare方法
 * implements Comparator<Map.Entry<Integer, Integer>>
 */
class MapValueComparator implements Comparator<Map.Entry<Integer, Integer>> {
    // 重写 比较 map 方法
    @Override
    public int compare(Map.Entry<Integer, Integer> me1, Map.Entry<Integer, Integer> me2) {

        return me1.getValue().compareTo(me2.getValue());
    }
}