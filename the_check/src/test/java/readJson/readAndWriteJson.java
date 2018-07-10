package readJson;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class readAndWriteJson {

    /**
     * @param args
     * @throws JSONException
     * @throws IOException
     */
    public static void main(String[] args) throws JSONException, IOException {
        InputStream in = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\json\\1298.json"));
        String result = IOUtils.toString(in, "UTF-8");
        //一个JsonObject文本数据
        System.out.println(result);
        // 1.先根据    ]}    拆分字符串。 数组接收。
        String[] string= result.split("]}");
        result.substring(result.indexOf("{["),result.indexOf("]}"));
        // 2.根据    {[     拆分字符串。 数组接收
        // 3.拆分json 数据格式，拼接字符串生成新的json.
    }
}