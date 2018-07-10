package com.the_check.test002.orc03;


import java.io.File;
import java.io.IOException;

public class OcrTest {

    public static void main(String[] args) {
//        String path = "C:\\Users\\Administrator\\Desktop\\image\\yBQCH1mDI02AZ4zAAAH4P2R6HAA521.jpg";
        String path = "C:\\Users\\Administrator\\Desktop\\image\\1_170620092535_1.png";
        System.out.println("ORC Test Begin......");
        try {
            String valCode = new OCR().recognizeText(new File(path), "png");
            System.out.println(valCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ORC Test End......");
    }

}