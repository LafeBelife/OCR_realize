package com.nnnew.v1.baiduAi;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

public class Sample {

    /**
     * 获取AccessToken
     * 百度开发
     * AppId:10028388
     * APIKey:kdZU5aOeI7FguVfWzql7LOGM
     * SecretKey:Xxcze1I2RLUhB8NFd7T4u4fHdBGundrn
     *""
     * @return
     */
    //设置APPID/AK/SK
//    public static final String APP_ID = "10028388";
//    public static final String API_KEY = "kdZU5aOeI7FguVfWzql7LOGM";
//    public static final String SECRET_KEY = "Xxcze1I2RLUhB8NFd7T4u4fHdBGundrn";

    public static final String APP_ID = "11621776";
    public static final String API_KEY = "gCmQOeA5ls1cOyWC2oYXEnKc";
    public static final String SECRET_KEY = "7339e78033ce071923d1ffd49699a0df";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 初始化一个OcrClient
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
// 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
     /*   options.put("recognize_granularity", "big");
        options.put("detect_direction", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");*/


        // 调用通用识别接口
        String genFilePath = "C:\\Users\\Administrator\\Desktop\\img\\example\\img\\TMSDGG\\group1\\M00\\0A\\5D\\2.jpg";
        JSONObject genRes = client.basicGeneral(genFilePath, options);
        System.out.println(genRes.toString(2));

//        // 参数为本地图片路径
//        String imagePath = "C:\\Users\\Administrator\\Desktop\\temp_icon\\yBQCIFmHnj2AIy9ZAAG3zSSL6ck020_image_6.jpg";
//        JSONObject response = client.webImage(imagePath, new HashMap<String, String>());
//        System.out.println(response.toString(2));

        System.out.println((System.currentTimeMillis() - start) / 1000 + "秒");
    }
}