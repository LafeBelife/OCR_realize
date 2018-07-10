package com.the_check.test002.orc02;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class a {

    public static void main(String[] args) {
        File imageFile = new File("C:\\Users\\Administrator\\Desktop\\image\\yBQCH1mDI02AZ4zAAAH4P2R6HAA521.jpg");
        ITesseract instance = Tesseract.getInstance();
        //如果未将tessdata放在根目录下需要指定绝对路径
        //instance.setDatapath("the absolute path of tessdata");
        instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR");
        //如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中
        instance.setLanguage("chi_sim");

        // 指定识别图片
        File imgDir = new File("C:\\Users\\Administrator\\Desktop\\image\\yBQCH1mDI02AZ4zAAAH4P2R6HAA521.jpg");
        long startTime = System.currentTimeMillis();
        String ocrResult = null;
        try {
            ocrResult = instance.doOCR(imgDir);
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        // 输出识别结果
        System.out.println("OCR Result: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
