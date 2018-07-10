package com.the_check.cutImages;

import java.io.File;

public class TestMain {
    public static void main(String[] args) {
        File file;
        int num = 526;
        for (int i = 0; i < 10; i++) {
            IncisionPicture incisionPicture = new IncisionPicture();
            file = new File("C:\\Users\\Administrator\\Desktop\\aaaaa\\aa_" + (num + i));//获取其file对象
            if (!file.exists()) {
                file.mkdirs();
            }
            incisionPicture.incisionPicture("C:\\Users\\Administrator\\Desktop\\_img\\" + (num + i) + ".jpg", file.getPath());
            File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
            for (File f : fs) {                //遍历File[]数组
                if (!f.isDirectory()) {        //若非目录(即文件)，则打印
                    incisionPicture.incisionIcon(f.getPath(), f.getParent() + "\\icon_" + f.getName());
                }
            }
        }
    }
}
