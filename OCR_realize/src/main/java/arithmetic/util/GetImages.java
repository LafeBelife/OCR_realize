//package arithmetic.util;
//
//import com.baidu.aip.ocr.AipOcr;
//import org.json.JSONObject;
//
//import java.awt.*;
//import java.awt.event.InputEvent;
//import java.awt.event.KeyEvent;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Objects;
//
//public class GetImages {
//    private static File _file = new File("C:\\Users\\Administrator\\Desktop\\temp_icon");
//
//    /**
//     * 获取AccessToken
//     * 百度开发
//     * AppId:10028388
//     * APIKey:kdZU5aOeI7FguVfWzql7LOGM
//     * SecretKey:Xxcze1I2RLUhB8NFd7T4u4fHdBGundrn
//     *
//     * @return
//     */
//    //设置APPID/AK/SK
//    public static final String APP_ID = "10028388";
//    public static final String API_KEY = "kdZU5aOeI7FguVfWzql7LOGM";
//    public static final String SECRET_KEY = "Xxcze1I2RLUhB8NFd7T4u4fHdBGundrn";
//
//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        File file = new File("C:\\Users\\Administrator\\Desktop\\temp_icon");        //获取其file对象
//        StringBuilder stringBuilder = func(file);
//        System.out.println(stringBuilder);
//        System.out.println((System.currentTimeMillis() - start) / 1000 + "秒");
//    }
//
//    private static int i = 0;
//
//    private static StringBuilder func(File file) {
//        StringBuilder stringBuilder = new StringBuilder();
//        File ff;
//        File[] fs = file.listFiles();
//        for (File f : Objects.requireNonNull(fs)) {
//            if (f.isDirectory()) {    //若是目录，则递归打印该目录下的文件
//                func(f);
//            }
//            if (f.isFile()) {        //若是文件，直接打印
//                // 初始化一个OcrClient
//                AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//                // 可选：设置网络连接参数
//                client.setConnectionTimeoutInMillis(2000);
//                client.setSocketTimeoutInMillis(60000);
//                // 调用通用识别接口
//                String genFilePath = f.getPath();
//                JSONObject genRes = client.basicGeneral(genFilePath, new HashMap<String, String>());
////                System.out.println(genRes.toString(2));
//                stringBuilder.append(f.getName() + "\n" + genRes.toString(2) + "\n");
////                // 参数为本地图片路径
////                String imagePath = "C:\\Users\\Administrator\\Desktop\\temp_icon\\yBQCIFmHnj2AIy9ZAAG3zSSL6ck020_image_6.jpg";
////                JSONObject response = client.webImage(imagePath, new HashMap<String, String>());
////                System.out.println(response.toString(2));
//
////                System.out.println(f.getName());
//                /*copyFile(f, ff = new File(_file + "\\" + i++ + ".jpg"));
//                System.out.println(i + "\t" + ff);*/
//
//                i++;
//                if (i == 500) {
//                    return stringBuilder;
//                }
//            }
//        }
//        return stringBuilder;
//    }
//
//
//    /**
//     * 复制文件
//     *
//     * @param fromFile
//     * @param toFile   <br/>
//     *                 2016年12月19日  下午3:31:50
//     */
//    public static void copyFile(File fromFile, File toFile) {
////        if (!toFile.exists()) {
////            toFile.mkdir();
////        }
//        FileInputStream ins = null;
//        FileOutputStream out = null;
//        try {
//            ins = new FileInputStream(fromFile);
//            out = new FileOutputStream(toFile);
//            byte[] b = new byte[1024];
//            int n;
//            while ((n = ins.read(b)) != -1) {
//                out.write(b, 0, n);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
