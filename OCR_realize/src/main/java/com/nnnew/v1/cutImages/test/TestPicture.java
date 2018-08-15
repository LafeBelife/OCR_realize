package com.nnnew.v1.cutImages.test;


import com.nnnew.v1.cutImages.IncisionPicture;

import java.io.File;

public class TestPicture {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
       /* File file;
        int num = 700; //563

        for (int i = 0; i < 1; i++) {
            IncisionPictures incisionPicture = new IncisionPicture();
            file = new File("C:\\Users\\Administrator\\Desktop\\aaaaa");
            incisionPicture.incisionPicture("C:\\Users\\Administrator\\Desktop\\_img_2\\yBQCH1mDI4aABdBQAAG5f4Nk7jw674.jpg", "C:\\Users\\Administrator\\Desktop\\aaaaa");
            File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
            for (File f : fs) {                //遍历File[]数组
                if (!f.isDirectory()) {        //若非目录(即文件)，则打印
                    incisionPicture.incisionIcon(f.getPath(), f.getParent() + "\\icon_" + f.getName());
                }
            }
        }
        */

       IncisionPicture picture = new IncisionPicture();
//       picture.incisionPicture("C:\\Users\\Administrator\\Desktop\\img\\img(8)\\TMZCSQ\\group2\\M00\\54\\1E\\yBQCIFmHnDCAE3cbAAGL0FBYaOM932.jpg",
//               "C:\\Users\\Administrator\\Desktop\\temp");
        picture.incisionIcon("C:\\Users\\Administrator\\Desktop\\temp\\yBQCIFmHnDCAE3cbAAGL0FBYaOM932__image_7.jpg","C:\\Users\\Administrator\\Desktop\\temp");
//       picture.incisionPictureByFolder("C:\\Users\\Administrator\\Desktop\\img\\img(12)\\TMZCSQ","C:\\Users\\Administrator\\Desktop\\img_temp");
       System.out.println("耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "秒...");
    }

}