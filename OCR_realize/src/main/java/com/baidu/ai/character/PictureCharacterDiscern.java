package com.baidu.ai.character;

import com.baidu.ai.character.service.TokenBase;
import com.baidu.ai.character.util.CharacterDiscernUtil;
import com.nnnew.v1.cutImages.util.IncisionUtil;
import com.nnnew.v1.cutImages.util.Log4jLog;

import java.io.File;
import java.util.Map;

/**
 * 图片商标识别<br/>
 * <p>
 * 2018年8月10日09:55:01
 *
 * @author 王保卫
 */
public class PictureCharacterDiscern {


    /**
     * 图片付费文字识别<br/>
     * <p>
     * 通用文字识别
     * 需要 .properties配置支付登录安全秘钥。
     *
     * @param picturePath  识别图片的路径
     *                     图片支持格式：PNG、JPG、JPEG、BMP，其他格式 可能请求不成功。
     * @param saveJsonFile 保存json文件数据路径
     * @param append       是否追加数据,false:覆盖,true:追加
     * @return
     */
    public static Map<String, Object> paidCharacterRecognition(String picturePath, String saveJsonFile, boolean append) {
        TokenBase.aiToken.setRank(0);
        TokenBase.paidToken.getFreeJsonMsg(picturePath);
        if (!saveMapToFile(TokenBase.map, saveJsonFile, append)) {
            Log4jLog.log.error("数据写入文件失败:" + saveJsonFile);
        }
        return TokenBase.map;
    }

    public static Map<String, Object> paidCharacterRecognition(String picturePath) {
        TokenBase.aiToken.setRank(0);
        TokenBase.paidToken.getFreeJsonMsg(picturePath);
        return TokenBase.map;
    }

    /**
     * 图片付费文字识别<br/>
     * <p>
     * 高级文字识别
     * 需要 .properties配置支付登录安全秘钥。
     *
     * @param picturePath  识别图片的路径
     *                     图片支持格式：PNG、JPG、JPEG、BMP，其他格式 可能请求不成功。
     * @param saveJsonFile 保存json文件数据路径
     * @param append       是否追加数据,false:覆盖,true:追加
     * @return
     */
    public static Map<String, Object> paidAdvancedCharacterRecognition(String picturePath, String saveJsonFile, boolean append) {
        TokenBase.aiToken.setRank(1);
        TokenBase.paidToken.getFreeJsonMsg(picturePath);
        if (!saveMapToFile(TokenBase.map, saveJsonFile, append)) {
            Log4jLog.log.error("数据写入文件失败:" + saveJsonFile);
        }
        return TokenBase.map;
    }

    public static Map<String, Object> paidAdvancedCharacterRecognition(String picturePath) {
        TokenBase.aiToken.setRank(1);
        TokenBase.paidToken.getFreeJsonMsg(picturePath);
        return TokenBase.map;
    }

    /**
     * 图片免费文字识别介绍<><br/>
     * 百度AI社区--文字识别官方版块：http://ai.baidu.com/forum/topic/list/164<br/>
     * 具有免费调用额度的接口，超过每天的免费额度后会返回错误码：17，错误信息：Open api daily request limit reached（每天流量超限额）；<br/>
     * 所有图片均需要base64编码、去掉编码头后再进行urlEncode。<br/>
     * 请注意：上传的图片使用JPG格式可以一定程度上提高识别准确率！<br/>
     * <p>
     * 图片免费文字识别<br/>
     * 通用识别版(识别图片文字信息)
     *
     * @param picturePath  识别图片的路径
     *                     图片支持格式：PNG、JPG、JPEG、BMP，其他格式 可能请求不成功。
     * @param saveJsonFile 保存json文件数据路径
     * @param append       是否追加数据,false:覆盖,true:追加
     * @return
     */
    public static Map<String, Object> freeCharacterRecognition(String picturePath, String saveJsonFile, boolean append) {
        TokenBase.freeToken.getFreeJsonMsg(picturePath, 0);
        if (!saveMapToFile(TokenBase.map, saveJsonFile, append)) {
            Log4jLog.log.error("数据写入文件失败:" + saveJsonFile);
        }
        return TokenBase.map;
    }

    public static Map<String, Object> freeCharacterRecognition(String picturePath) {
        TokenBase.freeToken.getFreeJsonMsg(picturePath, 0);
        return TokenBase.map;
    }

    /**
     * 图片免费文字识别<br/>
     * 精准识别（含位置信息版）
     *
     * @param picturePath  识别图片的路径
     *                     图片支持格式：PNG、JPG、JPEG、BMP，其他格式 可能请求不成功。
     * @param saveJsonFile 保存json文件数据路径
     * @param append       是否追加数据,false:覆盖,true:追加
     * @return
     */
    public static Map<String, Object> freeAdvancedCharacterRecognition(String picturePath, String saveJsonFile, boolean append) {
        TokenBase.freeToken.getFreeJsonMsg(picturePath, 1);
        if (!saveMapToFile(TokenBase.map, saveJsonFile, append)) {
            Log4jLog.log.error("数据写入文件失败:" + saveJsonFile);
        }
        return TokenBase.map;
    }

    public static Map<String, Object> freeAdvancedCharacterRecognition(String picturePath) {
        TokenBase.freeToken.getFreeJsonMsg(picturePath, 1);
        return TokenBase.map;
    }


    /**
     * 保存map数据到文件<><br/>
     * 文件路径必须是合法的，不存在会创建。
     *
     * @param map
     * @return
     */
    public static boolean saveMapToFile(Map<String, Object> map, String filePath, boolean append) {
        if (map == null || !IncisionUtil.isFile(filePath)) {// 文件路径不存在会会创建
            Log4jLog.log.info("传入数据不是有效的!");
            return false;
        }
        File file = CharacterDiscernUtil.mapTurnFile(map, filePath, append);
        if (file == null) {
            Log4jLog.log.info("map 转 文件失败!");
            return false;
        }
        return true;
    }
}