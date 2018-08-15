package com.nnnew.v1.baiduAi;

import com.nnnew.v1.cutImages.util.IncisionUtil;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GrayLevelPicture {
    @Test
    public void grayImage() throws IOException {
        File file = new File(path + "yBQCIFmFEkOAI3B6AAHRNlwWVTw712.jpg");
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }

        File newFile = new File(picPath);
        ImageIO.write(grayImage, "jpg", newFile);
    }

    static String path = "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\";
    static String picPath = path + "2_2.jpg";

    @Test
    public void cutPic() {
        IncisionUtil.cutPicture("jpg", picPath,
                0, 0, 206, 700, path + "2_2_1.jpg");
//        IncisionUtil.cutPicture("jpg", picPath,
//                206, 0, 387-206, 1400, path + "3_2.jpg");
//        IncisionUtil.cutPicture("jpg", picPath,
//                387, 0, 936 - 387, 1400, path + "3_3.jpg");
//        IncisionUtil.cutPicture("jpg", "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\2.jpg",
//                390, 0, 500 - 390, 1400, "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\2_4.jpg");
//        IncisionUtil.cutPicture("jpg", "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\2.jpg",
//                500, 0, 646 - 500, 1400, "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\2_5.jpg");
//        IncisionUtil.cutPicture("jpg", "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\2.jpg",
//                646, 0, 900 - 646, 1400, "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\2_6.jpg");
    }

}
