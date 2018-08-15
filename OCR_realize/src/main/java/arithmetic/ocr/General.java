package arithmetic.ocr;


//import myce.casia.baidu.utils.Base64Util;
//import myce.casia.baidu.utils.FileUtil;
//import myce.casia.baidu.utils.HttpUtil;

/**
 * OCR 通用识别
 */
public class General {

    /**
     * 代码中所需工具类
     * FileUtil,Base64Util,HttpUtil请从
     * https://ai.baidu.com/file/BA73D199EED14D8AA5FC5A4BF4BDDA34
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/88C6E86FB5D141889391693FC84504B1
     * 下载
     */

//    public static void main(String[] args) {
//
//        String filePath = "./picture/htimg.jpg";
//        String s = BaiduImageOCR(filePath);
//        System.out.println("百度ocr的识别结果是：" + s);
//    }
//
//    public static String BaiduImageOCR(String filepath) {
//        // 通用识别url
//        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";
//        // 本地图片路径
//        //   String filePath = "./picture/ic.JPG";
//        String string = "";
//        try {
//            byte[] imgData = FileUtil.readFileByBytes(filepath);
//            String imgStr = Base64Util.encode(imgData);
//            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
//            /**
//             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//             */
//            String accessToken = "24.e1e1bc62d7884e602e9849e39bfb5979.2592000.1497071993.282335-9631501";
//            String result = HttpUtil.post(otherHost, accessToken, params);
//            System.out.println(result);
//            JSONObject json = new JSONObject(result);
//            string = (String) json.getJSONArray("words_result").getJSONObject(0).get("words");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return string;
//    }

}
