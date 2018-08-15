package com.baidu.ai.character.service;

import com.baidu.ai.character.pojo.AiToken;
import com.baidu.aip.ocr.AipOcr;
import com.nnnew.v1.cutImages.util.Log4jLog;
import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * token初始化，初始化数据<br/>
 * 2018年8月14日10:05:54
 *
 * @author 王保卫
 */
public class TokenBase {
    public static AiToken aiToken = new AiToken();  // 登录信息
    public static Map<String, Object> map = new HashMap<>();    // 存放json数据
    public static int errorCount = 0;  // 值统计
    public static FreeToken freeToken = new FreeToken();    // 免费 api请求
    public static int lock = 0;    // 计数锁
    public static AipOcr aipOcr = null;
    public static HashMap<String, String> options = new HashMap<>();
    public static PaidToken paidToken = new PaidToken();    // 付费

    // 免费秘钥初始化 有效期一个月
    static {
        if (!paidToken.setToken()) { // 初始化token
            Log4jLog.log.info("token初始化失败！\n" + aiToken.toString());
        } else {
            aiToken.setSucceed(true);
            Log4jLog.log.info("token初始化成功！\n" + aiToken.toString());
        }
        aiToken.setFreeSecretKey(freeToken.getTokenSecretKey());
        if (StringUtils.isBlank(aiToken.getFreeSecretKey())) {
            Log4jLog.log.info("免费秘钥初始化失败！！");
        }
    }

    // 数据还原
    public static void baseInit() {
        errorCount = 0;
        map.clear();
    }

    // 重新获取token秘钥
    public static void tokenInit() {
        aiToken.setFreeSecretKey(freeToken.getTokenSecretKey());
    }
}
