package com.baidu.ai.character.service;

import com.baidu.ai.character.util.CharacterDiscernFinal;
import com.baidu.ai.character.util.LoadProperties;
import com.baidu.aip.ocr.AipOcr;
import com.nnnew.v1.cutImages.util.IncisionUtil;
import com.nnnew.v1.cutImages.util.Log4jLog;
import org.json.JSONObject;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.util.Properties;

/**
 * 收费 token 秘钥获取<><br/>
 * 2018年8月13日11:28:30
 *
 * @author 王保卫
 */
public class PaidToken {
    /**
     * 加载 百度ai 认证 属性文件<br/>
     * 返回基本认证信息
     *
     * @return map
     */
    public boolean setToken() {

        Properties properties = LoadProperties.loadProperties(CharacterDiscernFinal.BAI_DU_AI_TOKEN_PROPERTIES);
        // 也可使用 AipImageClassify aipImageClassify
        if (properties != null  // 必要参数
                && properties.containsKey(CharacterDiscernFinal.APP_ID)
                && properties.containsKey(CharacterDiscernFinal.SECRET_KEY)
                && properties.containsKey(CharacterDiscernFinal.API_KEY)) {
            // 设置 登录连接
            TokenBase.aiToken.setAppId(properties.getProperty(CharacterDiscernFinal.APP_ID));
            TokenBase.aiToken.setApiKey(properties.getProperty(CharacterDiscernFinal.API_KEY));
            TokenBase.aiToken.setSecretKey(properties.getProperty(CharacterDiscernFinal.SECRET_KEY));
            TokenBase.aipOcr = new AipOcr(TokenBase.aiToken.getAppId(), TokenBase.aiToken.getApiKey(), TokenBase.aiToken.getSecretKey());
            // 设置网络连接参数
            if (properties.containsKey(CharacterDiscernFinal.CONNECTION_TIMEOUT_IN_MILLIS)) {//建立连接的超时时间a
                TokenBase.aipOcr.setConnectionTimeoutInMillis(
                        Integer.parseInt(properties.getProperty(CharacterDiscernFinal.CONNECTION_TIMEOUT_IN_MILLIS)));
            }
            if (properties.containsKey(CharacterDiscernFinal.SOCKET_TIMEOUT_IN_MILLIS)) {//通过打开的连接传输数据的超时时间
                TokenBase.aipOcr.setSocketTimeoutInMillis(
                        Integer.parseInt(properties.getProperty(CharacterDiscernFinal.SOCKET_TIMEOUT_IN_MILLIS)));
            }
            return true;
        }
        return false;
    }

    /**
     * 迭代遍历图片获取信息
     *
     * @param picturePath
     */
    private void getJsonMsg(String picturePath) {

        if (StringUtils.isBlank(picturePath)) {
            Log4jLog.log.info("传入数据不是有效的:" + picturePath);
            TokenBase.map.put(picturePath, "图片路径不是有效的");
            return;
        }
        if (TokenBase.aipOcr == null && !TokenBase.aiToken.isSucceed()) {
            Log4jLog.log.info("登录秘钥初始化失败！");
            TokenBase.map.put("error_code", "登录秘钥初始化失败!");
            return;
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
        } else {
            Log4jLog.log.info("非有效图片:" + picturePath);
            return;
        }
        if (IncisionUtil.isFolderPath(picturePath)) {
            File[] file = new File(picturePath).listFiles();
            for (File pictureFile : file) {
                getJsonMsg(pictureFile.getAbsolutePath());//获得真实路径
            }
        }
    }

    /**
     * 获取json信息
     *
     * @param picturePath
     * @return
     */
    private JSONObject getJsonCharacterByPicture(String picturePath) {
        JSONObject jsonObject;
        if (TokenBase.aiToken.getRank() == 1) { // 高级信息 含位置
            // 传入可选参数调用接口
            TokenBase.options.put("recognize_granularity", "big");
            TokenBase.options.put("language_type", "CHN_ENG");
            TokenBase.options.put("detect_direction", "true");
            TokenBase.options.put("detect_language", "true");
            TokenBase.options.put("vertexes_location", "true");
            TokenBase.options.put("probability", "true");
            jsonObject = TokenBase.aipOcr.general(picturePath, TokenBase.options);
        } else {    // 普通信息
            jsonObject = TokenBase.aipOcr.basicGeneral(picturePath, TokenBase.options);
        }
        // 错误码处理
        int error_code = jsonObject.isNull("error_code")
                ? 0 : jsonObject.getInt("error_code");
        if (error_code != 0 && error_code != 17 && TokenBase.lock++ < 5) {
//            System.out.println(jsonObject.toString() + "\t" + "再次请求失败!请求次数：" + TokenBase.lock + "次。");
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
     * 获取json信息
     *
     * @param picturePath 图片路径
     */
    public void getFreeJsonMsg(String picturePath) {
        TokenBase.baseInit();
        getJsonMsg(picturePath);
        TokenBase.map.put("countSuccess", TokenBase.map.size() - TokenBase.errorCount);    // 成功数
        TokenBase.map.put("countError", TokenBase.errorCount);    // 失败数

    }
}
