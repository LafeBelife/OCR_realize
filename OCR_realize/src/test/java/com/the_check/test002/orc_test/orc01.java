package com.the_check.test002.orc_test;

/*

public class orc01 {
    public static String getVerifyText(InputStream ins) throws MalformedURLException,
            IOException {
        OCRFacade facade = new OCRFacade();

        BufferedImage bi = ImageIO.read(ins);

        ImageFilter ifter = new ImageFilter(bi);

        bi = ifter.changeGrey(); //这里只用了一个二值化 可以再多加几个

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", bao);

        String text = facade.recognizeImage(bao.toByteArray(), "png", "eng");

        System.out.println("Text in the image is: ");
        System.out.println(text);
        return text;
    }
}
*/
