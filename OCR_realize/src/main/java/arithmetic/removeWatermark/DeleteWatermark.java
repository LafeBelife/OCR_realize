package arithmetic.removeWatermark;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 图片根据 rgb 去除水印
 *
 * @author 保卫 2018-7-16
 */
public class DeleteWatermark {
    private void setAlpha(String os) {
        /**
         * 增加测试项
         * 读取图片，绘制成半透明,修改像素
         */
        try {
            ImageIcon imageIcon = new ImageIcon(os);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight()
                    , BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0,
                    imageIcon.getImageObserver());
            //循环每一个像素点，改变像素点的Alpha值
            int alpha = 100;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int pixel = bufferedImage.getRGB(j2, j1);
                    int[] rgb = new int[3];
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    System.out.println("i=" + j1 + ",j=" + j2 + ":(" + rgb[0] + ","
                            + rgb[1] + "," + rgb[2] + ")");

                    pixel = ((alpha + 1) << 24) | (pixel & 0x00ffffff);
                    bufferedImage.setRGB(j2, j1, pixel);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

            //生成图片为PNG
            ImageIO.write(bufferedImage, "jpg", new File("D:\\xiao.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        int x = 0;
        DeleteWatermark rc = new DeleteWatermark();
        rc.setAlpha("D:\\xiao.jpg");
        rc.setAlpha("D:\\xiao.jpg");
    }
}
