package com.nnnew.v1.cutImages.util;


import com.nnnew.v1.cutImages.IncisionPicture;
import org.apache.commons.logging.impl.Log4JLogger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志打印类，初始化日志信息
 *
 * @author 王保卫 2018-7-13
 */
public class Log4jLog {
    private static final String str = (IncisionPicture.class.getResource("/log4j.properties").getPath());
    public static Log4JLogger log = new Log4JLogger(str); //初始化日志驱动

    static {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.debug("-------------- l o g 记 录 --- " + sf.format(new Date()) + " ----------------------------------------");
    }
}