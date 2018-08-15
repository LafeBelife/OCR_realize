package com.baidu.ai.character.util;

import com.nnnew.v1.cutImages.util.Log4jLog;
import org.json.JSONObject;

/**
 * 文字识别常量类<br/>
 * 2018年8月10日11:37:12
 *
 * @author 王保卫
 */
public class CharacterDiscernFinal {

    public final static String APP_ID = "APP_ID";

    public final static String API_KEY = "API_KEY";

    public final static String SECRET_KEY = "SECRET_KEY";
    //   .properties 属性配置文件
    public final static String BAI_DU_AI_TOKEN_PROPERTIES = "baiduAiToken.properties";
    //   建立连接的超时时间（单位：毫秒）
    public final static String CONNECTION_TIMEOUT_IN_MILLIS = "CONNECTION_TIMEOUT_IN_MILLIS";
    //   通过打开的连接传输数据的超时时间（单位：毫秒）
    public final static String SOCKET_TIMEOUT_IN_MILLIS = "SOCKET_TIMEOUT_IN_MILLIS";
    // http 请求 url 普通版
    public final static String HTTP_URL = "HTTP_URL";

    // 获取token地址
    public final static String HTTP_TOKEN_AUTH_HOST = "HTTP_TOKEN_AUTH_HOST";

    // http 请求 连接参数
    public final static String CLIENT_ID = "&client_id=";
    public final static String CLIENT_SECRET = "&client_secret=";
    // 免费 token 信息
    public final static String ACCESS_TOKEN = "access_token";
    // 编码
    public final static String UTF_8 = "UTF-8";


    /**
     * api请求错误码处理
     *
     * @param error_code 错误状态码
     */
    public final static void apiErrorHanding(int error_code, JSONObject jsonObject) {

        switch (error_code) { // 输出错误信息
            case 1:
                Log4jLog.log.error("Unknown error - api服务器内部错误，再次请求!");
                jsonObject.put("error_details", "服务器内部错误");
                break;
            case 2:
                Log4jLog.log.error("Service temporarily unavailable - api服务暂不可用，再次请求!");
                jsonObject.put("error_details", "服务暂不可用");
                break;
            case 4:
                Log4jLog.log.error("Open api request limit reached - 集群超限额!");
                jsonObject.put("error_details", "集群超限额");
                break;
            case 6:
                Log4jLog.log.error("No permission to access data - api无权限访问该用户数据!");
                jsonObject.put("error_details", "无权限访问该用户数据");
                break;
            case 17:
                Log4jLog.log.error("Open api daily request limit reached - api每天请求量超限额!");
                jsonObject.put("error_details", "每天免费请求量超限额");
                break;
            case 18:
                Log4jLog.log.error("Open api qps request limit reached - apiQPS超限额,再次请求!");
                jsonObject.put("error_details", "QPS超限额");
                break;
            case 110 | 111:
                Log4jLog.log.error("Access token invalid or no longer valid | Access token expired - Access Token,失效或过期!");
                jsonObject.put("error_details", "Access Token,失效或过期");
                break;
            case 216201:
                Log4jLog.log.error("image format error - 支持的图片格式为：PNG、JPG、JPEG、BMP，请进行转码或更换图片!");
                jsonObject.put("error_details", "支持的图片格式为：PNG、JPG、JPEG、BMP");
                break;
            default:
                Log4jLog.log.error("api其他请求错误!");
                jsonObject.put("error_details", "其他请求错误");
                break;
        }
    }
}