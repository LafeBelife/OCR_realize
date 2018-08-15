package arithmetic.ocr;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.util.HashMap;

public class Sample02 {
    //设置APPID/AK/SK
    public static final String APP_ID = "11588901";
    public static final String API_KEY = "l9zfG30iGLM3OqhPjWIZEAFr";
    public static final String SECRET_KEY = "jl3l3mjWGYsFHfSrzmVYGGuWtpwC3iRA";

    public static void main(String[] args) {
        // 初始化一个AipImageClassifyClient
//        AipImageClassifyClient client = new AipImageClassifyClient(APP_ID, API_KEY, SECRET_KEY);
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若+不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "C:\\Users\\Administrator\\Desktop\\temp_icon\\774.jpg";
        JSONObject res = client.objectDetect(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}