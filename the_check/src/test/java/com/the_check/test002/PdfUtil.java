package com.the_check.test002;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author zhangguangze
 * @version v1.0
 * @project: zgz
 * @description: 这里描述类的用处
 * @copyright: © 2018
 * @company:
 * @date 2018/5/16 14:23
 */
public class PdfUtil {

    private static String FILEPATH = "C:\\Users\\Administrator\\Desktop\\pdf";

    /**
     *
     * @param fileName
     *            生成pdf文件
     * @param imagesPath
     *            需要转换的图片路径的数组
     */
    public static void imagesToPdf(String fileName, String imagesPath) {
        try {
            fileName = FILEPATH+fileName+".pdf";
            File file = new File(fileName);
            // 第一步：创建一个document对象。
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            // 第二步：
            // 创建一个PdfWriter实例，
            PdfWriter.getInstance(document, new FileOutputStream(file));
            // 第三步：打开文档。
            document.open();
            // 第四步：在文档中增加图片。
            File files = new File(imagesPath);
            String[] images = files.list();
            int len = images.length;

            for (int i = 0; i < len; i++)
            {
                if (images[i].toLowerCase().endsWith(".bmp")
                        || images[i].toLowerCase().endsWith(".jpg")
                        || images[i].toLowerCase().endsWith(".jpeg")
                        || images[i].toLowerCase().endsWith(".gif")
                        || images[i].toLowerCase().endsWith(".png")) {
                    String temp = imagesPath + "\\" + images[i];
                    Image img = Image.getInstance(temp);
                    img.setAlignment(Image.ALIGN_CENTER);
                    // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
                    document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    document.newPage();
                    document.add(img);
                }
            }
            // 第五步：关闭文档。
            document.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        String name = "20001543";
        String imagesPath = "C:\\Users\\Administrator\\Desktop\\image";
        imagesToPdf(name, imagesPath);
    }
}