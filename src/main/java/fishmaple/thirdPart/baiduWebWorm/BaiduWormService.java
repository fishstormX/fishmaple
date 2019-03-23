package fishmaple.thirdPart.baiduWebWorm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import fishmaple.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class  BaiduWormService {
    public String[] getAssociational(String key) throws IOException {
        URL url = new URL("https://gsp0.baidu.com/8qUZeT8a2gU2pMbgoY3K/su?wd="+key+"&prod=baike");//爬取的网址
        URLConnection urlconn = url.openConnection();
        InputStream in=urlconn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
        StringBuffer sb = new StringBuffer();
        String s;
        while((s=reader.readLine()) != null){
            sb.append(s+"\n");
        }
        reader.close();
        BaiduSearchObject object=JSON.parseObject(sb.substring(sb.indexOf("(")+1,sb.indexOf(")")),
                new TypeReference<BaiduSearchObject>() {});
        return object.getS();
    }

    public static void  main(String args[]) throws IOException {
        URL url = new URL("https://gsp0.baidu.com/8qUZeT8a2gU2pMbgoY3K/su?wd="+"为什么"+"&prod=baike");//爬取的网址
        URLConnection urlconn = url.openConnection();
        InputStream in=urlconn.getInputStream();
        StringBuffer sb = new StringBuffer(FileUtil.getStr(in,"<br>"));
        BaiduSearchObject object=JSON.parseObject(sb.substring(sb.indexOf("(")+1,sb.indexOf(")")),
                new TypeReference<BaiduSearchObject>() {});
        //BaiduSearchAssObject stu=(BaiduSearchAssObject)JSONObject.toBean(jsonObject, Student.class);
       // BaiduSearchAssObject object= (BaiduSearchAssObject) JSON.parse(sb.substring(sb.indexOf("(")+1,sb.indexOf(")")));
       // System.out.println(object.getS()[0]+object.getS()[1]+object.getS()[8]);
    }
}
