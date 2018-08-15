package com.nnnew.v1.cutImages.test;

import com.nnnew.v1.cutImages.IncisionPicture;
import com.nnnew.v1.cutImages.util.IncisionUtil;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TestPicture02 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        boolean isBoolean;
        IncisionPicture picture = new IncisionPicture();
        // 根据目录
        isBoolean = picture.incisionPictureByFolder("C:\\Users\\Administrator\\Desktop\\temp_img",
                "C:\\Users\\Administrator\\Desktop\\temp");
//         单个图片切割
//        isBoolean = picture.incisionPicture("C:\\Users\\Administrator\\Desktop\\temp_img\\yBQCIFmHnjyAfeIRAAHKpAGduQo825.jpg",
//                "C:\\Users\\Administrator\\Desktop\\temp_icon");
////         图标切割
//        isBoolean = picture.incisionIcon("C:\\Users\\Administrator\\Desktop\\temp\\image_70\\temp_4c02b78ca4b14276a7a56de20b9caa5b_image_3.jpg"
//                ,"C:\\Users\\Administrator\\Desktop\\temp_icon");
        System.out.println(isBoolean);

        System.out.println("耗时：" + (System.currentTimeMillis() - startTime) / 1000 + "秒...");
    }

    @Test
    public void testPic() {
        BufferedImage bufferedImage = IncisionUtil.readImageIo("C:\\Users\\Administrator\\Desktop\\yBQCIFmHnj2AIvzbAAGs-5IR7kQ775.jpg");
        bufferedImage = IncisionUtil.rotateImage(bufferedImage, 360 - 1);
        try {
            ImageIO.write(bufferedImage, "jpg", new File("C:\\Users\\Administrator\\Desktop\\temp_" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test01() {
        String str = "asas";
        int strLength = str.length();
    }
}