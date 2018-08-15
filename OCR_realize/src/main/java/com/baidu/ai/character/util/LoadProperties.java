package com.baidu.ai.character.util;

import com.baidu.ai.character.PictureCharacterDiscern;
import com.nnnew.v1.cutImages.util.Log4jLog;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 装在 .properties 配置文件属性<br/>
 * 2018年8月13日10:31:05
 *
 * @author 王保卫
 */
public class LoadProperties {

    /**
     * 加载 .properties 配置文件<br/>
     *
     * @param propertiesName
     * @return
     */
    public static Properties loadProperties(String propertiesName) {

        Properties properties = new Properties();
        if (StringUtils.isBlank(propertiesName)) {
            Log4jLog.log.error("装载文件名称不是有效的:" + propertiesName);
            return properties;
        }
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream inputStream = PictureCharacterDiscern.class.getClassLoader()
                .getResourceAsStream(propertiesName);
        try {
            properties.load(inputStream);// 使用properties对象加载输入流
        } catch (IOException e) {
            Log4jLog.log.error("读取配置文件异常：\n" + propertiesName);
            return properties;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log4jLog.log.error("关闭 IOException 异常:" + e.getMessage());
                }
            }
        }

        return properties;
    }
}
