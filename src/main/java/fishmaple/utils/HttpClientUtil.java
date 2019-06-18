package fishmaple.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import fishmaple.thirdPart.baiduWebWorm.BaiduSearchObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpClientUtil {
    public static Map<String,String> getHttpreturnMap(String urlStr) throws IOException {
        URL url = new URL(urlStr);//爬取的网址
        URLConnection urlconn = url.openConnection();
        InputStream in=urlconn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String s;
        while((s=reader.readLine()) != null){
            sb.append(s+"\n");
        }
        reader.close();
        Map map=JSON.parseObject(sb.toString());
        return map;
    }

    public static String getHttpreturnStr(String urlStr) throws IOException {
        URL url = new URL(urlStr);//爬取的网址
        URLConnection urlconn = url.openConnection();
        InputStream in=urlconn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String s;
        while((s=reader.readLine()) != null){
            sb.append(s+"\n");
        }
        reader.close();
        return sb.toString();
    }


}
