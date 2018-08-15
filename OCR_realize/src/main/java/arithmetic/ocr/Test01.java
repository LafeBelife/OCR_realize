package arithmetic.ocr;//package com.nnnew.v1.ocr;
//
//import com.sun.deploy.net.HttpResponse;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.client.utils.HttpClientUtils;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//public class Test01 {
//    /**
//     * 获取AccessToken
//     * 百度开发
//     * AppId:
//     * APIKey:
//     * SecretKey:
//     *
//     * @return
//     */
//    public static String getAccessToken() {
//        String accessToken = "";
//        HttpRequestData httpRequestData = new HttpRequestData();
//        HashMap<String, String> params = new HashMap<>();
//        params.put("grant_type", "client_credentials");
//        params.put("client_id", "xxxxxx");
//        params.put("client_secret", "xxxxxx");
//        httpRequestData.setRequestMethod("GET");
//        httpRequestData.setParams(params);
//        httpRequestData.setRequestUrl("https://aip.baidubce.com/oauth/2.0/token");
//        HttpResponse response = HttpClientUtils.execute(httpRequestData);
//        String json = "";
//        try {
//            json = IOUtils.toString(response.getEntity().getContent());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (response.getStatusLine().getStatusCode() == 200) {
//            JSONObject jsonObject = JSONObject.parseObject(json);
//            if (jsonObject != null && !jsonObject.isEmpty()) {
//                accessToken = jsonObject.getString("access_token");
//            }
//        }
//        return accessToken;
//    }
//    /**
//     * 获取识别验证码
//     * @param imageUrl
//     * @return
//     */
//    public static String OCRVCode(String imageUrl){
//        String VCode = "";
//
//        if (StringUtils.isBlank(ACCESS_TOKEN)) {
//            logger.error("accessToken为空");
//            return VCode;
//        }
//        OCRUrl = OCRUrl + "?access_token=" + ACCESS_TOKEN;
//
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded");
//
//        HashMap<String, String> params = new HashMap<>();
//        imageUrl = ImageBase64ToStringUtils.imageToStringByBase64(imageUrl);
//        params.put("image", imageUrl);
//
//        HttpRequestData httpRequestData = new HttpRequestData();
//        httpRequestData.setHeaders(headers);
//        httpRequestData.setRequestMethod("post");
//        httpRequestData.setParams(params);
//        httpRequestData.setRequestUrl(OCRUrl);
//        HttpResponse response = HttpClientUtils.execute(httpRequestData);
//        String json = "";
//        if (response.getStatusLine().getStatusCode() == 200) {
//            try {
//                json = IOUtils.toString(response.getEntity().getContent());
//                JSONObject jsonObject = JSONObject.parseObject(json);
//                JSONArray wordsResult = jsonObject.getJSONArray("words_result");
//                VCode = wordsResult.getJSONObject(0).getString("words");
//            } catch (IOException e) {
//                logger.error("请求识别失败！", e);
//            }
//        }
//        return VCode;
//    }
//    /**
//     * 将本地图片进行Base64位编码
//     * @param imageFile
//     * @return
//     */
//    public static String encodeImgageToBase64(String imageFile) {
//        // 其进行Base64编码处理
//        byte[] data = null;
//        // 读取图片字节数组
//        try {
//            InputStream in = new FileInputStream(imageFile);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 对字节数组Base64编码
//        return Base64Util.encode(data);
//    }
//}
