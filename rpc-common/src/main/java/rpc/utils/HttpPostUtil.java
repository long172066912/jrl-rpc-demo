package rpc.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostUtil {

    /**
     * 发送http post json请求
     *
     * @param path
     * @param params
     * @return
     * @throws Exception
     */
    public static String postJson(String path, String params) throws Exception {
        // 创建连接
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setReadTimeout(2000);
        // 设置请求方式
        connection.setRequestMethod("POST");
        // 设置发送数据的格式
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();
        //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
        // utf-8编码
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        out.append(params);
        out.flush();
        out.close();
        // 读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        String res = "";
        while ((line = reader.readLine()) != null) {
            res += line;
        }
        reader.close();
        return res;
    }
}
