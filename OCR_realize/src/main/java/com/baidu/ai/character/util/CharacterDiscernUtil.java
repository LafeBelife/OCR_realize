package com.baidu.ai.character.util;

import com.nnnew.v1.cutImages.util.IncisionUtil;
import com.nnnew.v1.cutImages.util.Log4jLog;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 图片识别工具类<br/>
 * 2018年8月10日09:56:24
 *
 * @author 王保卫
 */
public class CharacterDiscernUtil {


    /**
     * 将本地图片进行Base64位编码<br/>
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param imagePath 图片的url路径
     * @return String 返回Base64编码过的字节数组字符串
     */
    public static String encodeImageToBase64(String imagePath) {
        if (!IncisionUtil.isPictureFile(imagePath)) {
            Log4jLog.log.info("图片不是有效的:" + imagePath);
        }
        byte[] data;// 其进行Base64编码处理
        InputStream inputStream = null;// 读取图片字节数组
        try {
            inputStream = new FileInputStream(imagePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
        } catch (IOException e) {
            Log4jLog.log.error("读取 inputStream 异常：" + e.getMessage());
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log4jLog.log.error("关闭 inputStream 异常：" + e.getMessage());
            }
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String imageBase64 = encoder.encode(data);
        imageBase64 = imageBase64.replaceAll("\r\n", "");
        imageBase64 = imageBase64.replaceAll("\\+", "%2B");
        return imageBase64;
    }

    /**
     * 读取 inputStream
     *
     * @param inputStream
     * @return String
     */
    public static String readInputStream(InputStream inputStream) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CharacterDiscernFinal.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Log4jLog.log.error("关闭 inputStream 异常：" + e.getMessage());
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log4jLog.log.error("关闭 inputStream 异常：" + e.getMessage());
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取 http 请求结果。
     *
     * @param accessTokenUrl
     * @return HttpURLConnection
     */
    public static HttpURLConnection getHttpRequest(String accessTokenUrl) {

        HttpURLConnection connection = null;
        try {
            URL realURL = new URL(accessTokenUrl); // 打开和URL之间的连接
            connection = (HttpURLConnection) realURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            httpURLConnection.setUseCaches(false);    // 设置缓存
        } catch (IOException e) {
            Log4jLog.log.info("建立连接失败 HttpURLConnection :" + e.getMessage());
            return connection;
        }
        return connection;
    }

    /**
     * map存储到 文件里。
     *
     * @param map    数据
     * @param file   文件路径
     * @param append 是否追加数据
     * @return file
     */
    public static File mapTurnFile(Map<String, Object> map, String file, boolean append) {
        if (!IncisionUtil.isFile(file)) {
            Log4jLog.log.info("文件路径不是有效的:" + file);
            return null;
        }
        File mapPath = new File(file);
        FileOutputStream fileOutputStream = null;
        String newLine = System.getProperty("line.separator");// 换行符
        try {
            fileOutputStream = new FileOutputStream(mapPath, append);
            for (Map.Entry<String, Object> entry : map.entrySet()) { // 换行输出
                String write = entry.toString() + newLine;
                fileOutputStream.write(write.getBytes());
            }
        } catch (FileNotFoundException e) {
            Log4jLog.log.error("文件未找到:" + file);
            return null;
        } catch (IOException e) {
            Log4jLog.log.info("写入数据出现错误 IOException：" + e.getMessage());
            return null;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log4jLog.log.error("关闭流出现错误 IOException:" + e.getMessage());
                }
            }
        }
        return mapPath;
    }
}