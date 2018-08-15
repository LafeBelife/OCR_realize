package com.the_check.test002;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test01 {
    /**
     * 移除水印
     * @param srcPath 带水印pdf
     * @param buildPath 去除水印pdf
     * @return
     */
    public String removeWatermark(String srcPath, String buildPath){
//注意，这将破坏所有层的文档中，只有当你没有额外的层使用
        try {
            PdfReader reader =new PdfReader(srcPath);
//从文档中彻底删除的OCG组。
//占位符变量
            reader.removeUnusedObjects();
            int pageCount = reader.getNumberOfPages();
            PRStream prStream=null;
            PdfDictionary curPage;
            PdfArray contentarray;
//循环遍历每个页面
            for(int i=1; i<=pageCount; i++){
                //获取页面
                curPage = reader.getPageN(i);
                //获取原始内容
                contentarray = curPage.getAsArray(PdfName.CONTENTS);
                if(contentarray != null){
                    System.out.println("11111111111111111111112222222222222");
                    //循环遍历内容
                    for(int j=0; j<contentarray.size(); j++){
                        //获取原始字节流
                        prStream =(PRStream)contentarray.getAsStream(j);
                        // 0代表水印层
                        if (j == 0){
                            System.out.println("1111111111111111111111");
                            //给它零长度和零数据删除它
                            prStream.put(PdfName.LENGTH, new PdfNumber(0));
                            prStream.setData(new byte[0]);
                        }
                    }
                }
            }
//写出来的内容
            File file = new File(buildPath);
            if (file.exists()){
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            Document doc = new Document(prStream.getReader().getPageSize(1));
            PdfCopy copy = new PdfCopy(doc, fos);
            doc.open();
            for (int j = 1; j <= pageCount; j++) {
                doc.newPage();
                PdfImportedPage page = copy.getImportedPage(prStream.getReader(), j);
                copy.addPage(page);
            }
            doc.close();
            return "success";
        } catch (BadPdfFormatException e) {
            e.printStackTrace();
            return "0";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "1";
        } catch (IOException e) {
            e.printStackTrace();
            return "2";
        } catch (DocumentException e) {
            e.printStackTrace();
            return "3";
        }
    }

    public static void main(String[] args) {
        Test01 test01 = new Test01();

        test01.removeWatermark("C:\\Users\\Administrator\\Desktop\\pdf20001543.pdf","C:\\Users\\Administrator\\Desktop\\132.pdf");
    }
}
