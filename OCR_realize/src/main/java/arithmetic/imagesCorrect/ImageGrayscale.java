package arithmetic.imagesCorrect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGrayscale {
    public void binaryImage() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\temp_img\\yBQCIFmHnj2AH_iJAAGvq6McuZQ644.jpg");
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_BINARY
        for(int i= 0 ; i < width ; i++){
            for(int j = 0 ; j < height; j++){
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }

        File newFile = new File("C:\\Users\\Administrator\\Desktop\\img\\1.jpg");
        ImageIO.write(grayImage, "jpg", newFile);
    }

    public void grayImage() throws IOException{
        File file = new File("C:\\Users\\Administrator\\Desktop\\temp_img\\yBQCIFmHnj2AH_iJAAGvq6McuZQ644.jpg");
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_GRAY
        for(int i= 0 ; i < width ; i++){
            for(int j = 0 ; j < height; j++){
                int rgb = image.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }

        File newFile = new File("C:\\Users\\Administrator\\Desktop\\img\\2.jpg");
        ImageIO.write(grayImage, "jpg", newFile);
    }

    public static void main(String[] args) throws IOException {
        ImageGrayscale demo = new ImageGrayscale();
        demo.binaryImage();
        demo.grayImage();
    }

}
