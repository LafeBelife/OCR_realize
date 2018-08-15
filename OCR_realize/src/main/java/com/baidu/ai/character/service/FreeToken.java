package com.baidu.ai.character.service;

import com.baidu.ai.character.util.CharacterDiscernFinal;
import com.baidu.ai.character.util.CharacterDiscernUtil;
import com.baidu.ai.character.util.LoadProperties;
import com.nnnew.v1.cutImages.util.IncisionUtil;
import com.nnnew.v1.cutImages.util.Log4jLog;
import org.json.JSONObject;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Properties;

/**
 * 免费 token 秘钥获取<><br/>
 * 2018年8月13日11:28:30
 *
 * @author 王保卫
 */
public class FreeToken {


    /**
     * 获取权限token
     *
     * @return 返回示例：
     * 24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567
     * 或者 null
     */
    public String getTokenSecretKey() {
        String tokenResult = null;

        Properties properties = LoadProperties.loadProperties(CharacterDiscernFinal.BAI_DU_AI_TOKEN_PROPERTIES);
        if (properties == null) {
            Log4jLog.log.info("加载" + CharacterDiscernFinal.BAI_DU_AI_TOKEN_PROPERTIES + "失败!");
            return null;
        }

        if (properties.containsKey(CharacterDiscernFinal.HTTP_URL + TokenBase.aiToken.getRank())) {
            /*// 官网获取的 API Key 更新为你注册的
            TokenBase.aiToken.setApiKey(properties.getProperty(CharacterDiscernFinal.API_KEY));
            // 官网获取的 Secret Key 更新为你注册的
            TokenBase.aiToken.setSecretKey(properties.getProperty(CharacterDiscernFinal.SECRET_KEY));*/
            JSONObject jsonObject = refreshToken();
            if (jsonObject == null) {
                Log4jLog.log.info("获取 json 秘钥 失败!");
                return null;
            }
            tokenResult = properties.getProperty(CharacterDiscernFinal.HTTP_URL + TokenBase.aiToken.getRank())
                    + jsonObject.getString(CharacterDiscernFinal.ACCESS_TOKEN); // 获取最新 token
        }
        if (tokenResult == null) {
            Log4jLog.log.info("获取免费 Token 失败:" + CharacterDiscernFinal.BAI_DU_AI_TOKEN_PROPERTIES
                    + "配置文件存在问题(" + CharacterDiscernFinal.API_KEY + "|"
                    + CharacterDiscernFinal.SECRET_KEY + ")");
            return null;
        }
        return tokenResult; // 返回Json秘钥
    }

    /**
     * 获取API访问token <br/>
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.<><br/>
     * 一个月失效。
     *
     * @param - 百度云官网获取的 API Key
     * @param - 百度云官网获取的 Secret Key
     * @return token 令牌
     * 列：24.c8331477821ba33d98eceaa595cdd022.2592000.1536722201.282335-11588901
     */
    private JSONObject refreshToken() {


        Properties properties = LoadProperties.loadProperties(CharacterDiscernFinal.BAI_DU_AI_TOKEN_PROPERTIES);
        if (properties == null) {
            Log4jLog.log.info("加载" + CharacterDiscernFinal.BAI_DU_AI_TOKEN_PROPERTIES + "失败!");
            return null;
        }
        if (!properties.containsKey(CharacterDiscernFinal.HTTP_TOKEN_AUTH_HOST)) {
            Log4jLog.log.info("加载token秘钥地址失败：" + CharacterDiscernFinal.HTTP_TOKEN_AUTH_HOST);
            return null;
        }
        // 获取token地址
        String accessTokenUrl = properties.getProperty(CharacterDiscernFinal.HTTP_TOKEN_AUTH_HOST)
                + CharacterDiscernFinal.CLIENT_ID + TokenBase.aiToken.getApiKey()
                + CharacterDiscernFinal.CLIENT_SECRET + TokenBase.aiToken.getSecretKey();
        HttpURLConnection httpURLConnection = CharacterDiscernUtil.getHttpRequest(accessTokenUrl);
        if (httpURLConnection == null) {
            Log4jLog.log.info("获取http请求信息失败...");
            return null;
        }
        String result;
        try {
            httpURLConnection.connect();
            result = CharacterDiscernUtil.readInputStream(httpURLConnection.getInputStream());
        } catch (IOException e) {
            Log4jLog.log.info("获取 httpURLConnection 请求信息失败:" + e.getMessage());
            return null;
        }
        if (StringUtils.isBlank(result)) {
            Log4jLog.log.info("HttpURLConnection 数据请求失败！");
            return null;
        }
        return new JSONObject(result);
    }

    private JSONObject jsonObject = null;

    /**
     * 图片文字识别
     *
     * @param imageBase64
     * @return JSONObject
     */
    private JSONObject getPictureCharacterDiscern(String imageBase64) {

        if (StringUtils.isBlank(imageBase64)) {
            Log4jLog.log.info("传入值不是有效的...." + imageBase64);
            return null;
        }
        String httpUrl = TokenBase.aiToken.getFreeSecretKey(); // 获取请求头
        if (StringUtils.isBlank(httpUrl)) {
            Log4jLog.log.info("请求token错误。");
            return null;
        }
        String httpArg = "image=" + imageBase64;    // 设置图片路径

        String result;
        try {
            HttpURLConnection httpURLConnection = CharacterDiscernUtil.getHttpRequest(httpUrl);
            if (httpURLConnection == null) {
                Log4jLog.log.info("httpURLConnection 请求失败！");
                return null;
            }
            // 填入apiKey到HTTP header
            httpURLConnection.setRequestProperty(TokenBase.aiToken.getApiKey(), TokenBase.aiToken.getSecretKey());
            httpURLConnection.setRequestProperty("probability", "true");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().write(httpArg.getBytes(CharacterDiscernFinal.UTF_8));
            httpURLConnection.connect();

            result = CharacterDiscernUtil.readInputStream(httpURLConnection.getInputStream());
        } catch (IOException e) {
            Log4jLog.log.info("获取 httpURLConnection 请求信息失败:" + e.getMessage());
            return null;
        }
        if (StringUtils.isBlank(result)) {
            Log4jLog.log.info("HttpURLConnection 数据请求失败！");
            return null;
        }
        jsonObject = new JSONObject(result);
        // 错误码处理
        int error_code = jsonObject.isNull("error_code")
                ? 0 : jsonObject.getInt("error_code");
        if (error_code != 0 && error_code != 17 && TokenBase.lock++ < 5) {
//            System.out.println(jsonObject.toString() + "\t" + "再次请求失败!请求次数：" + TokenBase.lock + "次。");
            jsonObject = getPictureCharacterDiscern(imageBase64);
            if (jsonObject == null || !jsonObject.isNull("error_code")) {
                Log4jLog.log.error("再次请求失败!请求次数：" + TokenBase.lock + "次。");
            }
        }
        if (error_code != 0) {  // 添加错误信息
            CharacterDiscernFinal.apiErrorHanding(error_code, jsonObject);
        }
        if (TokenBase.lock > 0) {
            TokenBase.lock = 0;// 还原初始化
        }
        return jsonObject;
    }


    /**
     * 遍历路径，识别图片信息
     *
     * @param picturePath
     * @return Map
     */
    private void getJsonMsg(String picturePath) {

        if (StringUtils.isBlank(picturePath)) {
            Log4jLog.log.info("传入数据不是有效的:" + picturePath);
            TokenBase.map.put(picturePath, "");
        }
        if (IncisionUtil.isPictureFile(picturePath)) {
            JSONObject jsonObject = getJsonCharacterByPicture(picturePath);
            if (jsonObject == null) {
                Log4jLog.log.info("图片失败：" + picturePath);
            }
            if (!jsonObject.isNull("error_code")) {
                TokenBase.errorCount++;
            }
            TokenBase.map.put(picturePath, jsonObject);
            return;
        } else {
            Log4jLog.log.info("非有效图片:" + picturePath);
        }
        if (IncisionUtil.isFolderPath(picturePath)) {
            File[] file = new File(picturePath).listFiles();
            if (file != null && file.length > 0) {
                for (File pictureFile : file) {
                    getJsonMsg(pictureFile.getAbsolutePath());//获得真实路径
                }
            }
        }
    }

    /**
     * 根据图片路径获取json数据<><br/>
     *
     * @param picturePath
     * @return JSONObject
     */
    private JSONObject getJsonCharacterByPicture(String picturePath) {
        if (!IncisionUtil.isPictureFile(picturePath)) {
            Log4jLog.log.info("图片路径不是有效的:" + picturePath);
            return null;
        }
        String imageBase64 = CharacterDiscernUtil.encodeImageToBase64(picturePath);   // 转为base64编码
        JSONObject jsonObject = TokenBase.freeToken.getPictureCharacterDiscern(imageBase64);
        if (jsonObject == null) {
            Log4jLog.log.info("图片文字解析错误...");
        }
        return jsonObject;
    }

    /**
     * 获取json信息
     *
     * @param picturePath
     * @param rank
     */
    public void getFreeJsonMsg(String picturePath, int rank) {
        if (StringUtils.isBlank(TokenBase.aiToken.getFreeSecretKey())) {
            Log4jLog.log.info("免费秘钥初始化失败！！");
            return;
        }
        TokenBase.aiToken.setRank(rank);
        TokenBase.baseInit();
        TokenBase.freeToken.getJsonMsg(picturePath);
        TokenBase.map.put("countSuccess", TokenBase.map.size() - TokenBase.errorCount);    // 成功数
        TokenBase.map.put("countError", TokenBase.errorCount);    // 失败数

    }
}
