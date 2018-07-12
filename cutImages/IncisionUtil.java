package com.the_check.cutImages;

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
import java.util.List;

public class IncisionUtil {


    /**
     * 获取只出现一次的值
     *
     * @param array
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> findNumAppearOnce(Object[] array) {

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
     * @return map
     */
    public Map<Integer, Integer> removeRepFromMap(Map<Integer, Integer> map) {

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
    public String cutPicture(String suffix, String picturePath, int x, int y, int width,
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
     * @return Map<Integer       ,       Integer>
     */
    public Map<Integer, Integer> sortMapByValue(Map<Integer, Integer> oriMap) {
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
     *
     * @param number
     * @return int
     */

    public int getInt(double number) {
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