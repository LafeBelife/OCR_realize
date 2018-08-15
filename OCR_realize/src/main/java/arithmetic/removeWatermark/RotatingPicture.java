package arithmetic.removeWatermark;

import java.awt.image.BufferedImage;

public class RotatingPicture {

    public static void main(String[] args) {
        BufferedImage bufferedImage = null;
//        System.out.println(Math.sqrt(906 * 906 + 9 * 9));
        System.out.println(((906 * 906) + (9 * 9) - (907 * 907)) / (2 * 9 * 906));
       /* try {
            bufferedImage = ImageIO.read(new FileInputStream("C:\\Users\\Administrator\\Desktop\\yBQCIFmHnj2AIvzbAAGs-5IR7kQ775.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedImage = rotateImage(bufferedImage,360-1);
        try {
            ImageIO.write(bufferedImage,"jpg", new File("C:\\Users\\Administrator\\Desktop\\aa.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 旋转图片为指定角度
     *
     * @param bufferedimage 目标图像
     * @param degree        旋转角度
     * @return
     */
  /*  public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }*/

    /**
     * 变更图像为指定大小
     *
     * @param bufferedimage
     *            目标图像
     * @param w
     *            宽
     * @param h
     *            高
     * @return
     */
   /* public static BufferedImage resizeImage(final BufferedImage bufferedimage,
                                            final int w, final int h) {
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = createImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage
                .getWidth(), bufferedimage.getHeight(), null);
        graphics2d.dispose();
        return img;
    }

    *//**
     * 水平翻转图像
     *
     * @param bufferedimage 目标图像
     * @return
     *//*
    public static BufferedImage flipImage(final BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = createImage(w, h, bufferedimage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(bufferedimage, 0, 0, w, h, w, 0, 0, h, null);
        graphics2d.dispose();
        return img;
    }*/
}
