package com.baidu.ai.character.retain.test;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;

/**
 * apikey：API密钥 也就是您自己的apikey
 * fromdevice：来源，例如：Android、iPhone 默认是PC
 * clientip：客户端出口IP
 * detecttype：OCR接口类型
 * languagetype：要检测的文字类型
 * imagetype：图片资源类型
 * image：图片资源，目前仅支持jpg格式
 */
public class OCRTest {

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "LUGBatgyRGoerR9FZbV4SQYk");
            connection.setDoOutput(true);
            connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\temp_icon\\774.jpg");
        String access_token = "24.37271a8bb49ceb47150125f47cd9593c.2592000.1536480757.282335-11588901";
        String imageBase = encodeImgageToBase64(file);
//        imageBase = imageBase.replaceAll("\r\n", "");
        imageBase = imageBase.replaceAll("\\+", "%2B");
//        String httpUrl = "http://apis.baidu.com/apistore/idlocr/ocr";//'' + access_token
        String httpUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token=" + access_token;
        String httpArg = "image=" + imageBase;  //fromdevice=pc&clientip=172.16.1.103&detecttype=LocateRecognize&languagetype=CHN_ENG&imagetype=1&
        String jsonResult = request(httpUrl, httpArg);
        System.out.println("返回的结果--------->" + jsonResult);

    }

    /**
     * 将本地图片进行Base64位编码
     *
     * @param imageFile 图片的url路径，如d:\\中文.jpg
     * @return
     */
    public static String encodeImgageToBase64(File imageFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        // 其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }
}