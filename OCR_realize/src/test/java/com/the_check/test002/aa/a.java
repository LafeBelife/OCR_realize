package com.the_check.test002.aa;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class a {
    public static void main(String[] args) {
        try {
            PdfReader pdfReader = new PdfReader("C:\\Users\\Administrator\\Desktop\\pdf20001543.pdf");
            FileOutputStream os = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\132.pdf");
            PdfStamper stamper = new PdfStamper(pdfReader,os);
            PdfContentStreamEditor editor = new PdfContentStreamEditor(){
                @Override
                protected void write(PdfContentStreamProcessor processor, PdfLiteral operator, List<PdfObject> operands)
                        throws IOException {
                    String operatorString = operator.toString();
                    //Tj 操作通过当前的字体和其他文字相关的图形状态参数来取走一串操作和绘制相应的字形
                    //Tr操作设置的文本渲染模式
                    //一个文本对象开始于BT，结束于ET
                    final List<String> TEXT_SHOWING_OPERATORS = Arrays.asList("Tj","'","\\","TJ");
                    System.out.println(operatorString);
                    if(TEXT_SHOWING_OPERATORS.contains(operatorString)){
                        PdfDictionary dic = gs().getFont().getFontDictionary();
                        if(gs().getFont().getPostscriptFontName().endsWith("BoldMT")){//BoldMT字体的名称
                            return;
                        }
                    }
                    super.write(processor, operator, operands);
                }
            };
            for(int i = 1;i <= pdfReader.getNumberOfPages();i++){
                editor.editPage(stamper, i);
            }
            stamper.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
