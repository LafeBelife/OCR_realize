package com.baidu.ai.character.retain.test;

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
public class SampleApi {

    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-9261443",
     * "session_key": "9mzdDxLPLXYfhlmFRBSGI8jOy19HNzFT6cle3RWf2m9HL8LdLxyVhEETkPS8gQtLbhw1TP/ly3+l59n4wknbmwPk6NC1",
     * "scope": "public vis-faceverify_faceverify vis-ocr_ocr vis-faceattribute_faceattribute vis-ocr_bankcard nlp_wordseg nlp_simnet nlp_wordemb nlp_comtag nlp_wordpos nlp_dnnlm_cn vis-antiporn_antiporn_v2 brain_ocr_scope wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian wangrantest_test wangrantest_test1 bnstest_test1 bnstest_test2 ApsMisTest_Test权限 vis-classify_flower",
     * "refresh_token": "25.bf1ec1814779878486cb9ba68f7defe5.315360000.1804763545.282335-9261443",
     * "session_secret": "b292e183bb394fb8d8065e7f8b137757",
     * "expires_in": 2592000
     * }
     */
    public static String getAuth() {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        // 官网获取的 API Key 更新为你注册的
        String clientId = "zhIRNI0TAR7vLhssegRLtEeu";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "8bb12babe00042a28314406d3af35dfe";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + clientId
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + clientSecret;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
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
            System.out.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.out.printf("获取token失败！");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        getAuth();
    }


}
