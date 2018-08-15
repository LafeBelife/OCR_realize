package com.baidu.ai.character.test;

import com.baidu.ai.character.PictureCharacterDiscern;
import com.nnnew.v1.cutImages.util.IncisionUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

/**
 * 百度识别 测试类<br/>
 * 2018年8月10日13:44:06
 *
 * @author 王保卫
 */
public class PictureDiscernTest {

    public static void main(String[] args) {
        Map<String, Object> map = PictureCharacterDiscern.freeAdvancedCharacterRecognition("C:\\Users\\Administrator\\Desktop\\tep_icon\\3.jpg");
        System.out.println(map.toString());
    }

    @Test
    public void test() {
        File file = new File("C:\\Users\\Administrator\\Desktop\\new 2.txt");

        boolean boo = IncisionUtil.isFile(file.getPath());
    }
}