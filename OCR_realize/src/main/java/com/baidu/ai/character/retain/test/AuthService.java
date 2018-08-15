package com.baidu.ai.character.retain.test;


import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 */
public class AuthService {
    public static void main(String[] args) {
        System.out.println(getAuth());
    }

    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "l9zfG30iGLM3OqhPjWIZEAFr";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "jl3l3mjWGYsFHfSrzmVYGGuWtpwC3iRA";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
//        AipImageClassify aipImageClassify = new AipImageClassify();


//        String getAccessTokenUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/general?access_token=24.37271a8bb49ceb47150125f47cd9593c.2592000.1536480757.282335-11588901";
        try {
            URL realUrl = new URL(getAccessTokenUrl);
//            realUrl.sameFile();
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
//            connection.setRequestProperty("image","C:\\Users\\Administrator\\Desktop\\temp_icon\\774.jpg");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
//            httpURLConnection.setUseCaches(false);
//            httpURLConnection.setConnectTimeout(30000); //30秒连接超时
//            httpURLConnection.setReadTimeout(30000);    //30秒读取超时

            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            System.out.println(jsonObject.toString(2));
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}
