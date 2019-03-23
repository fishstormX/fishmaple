package fishmaple.thirdPart.toutiaoWorm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import fishmaple.thirdPart.baiduWebWorm.BaiduSearchObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
public class ToutiaoService {
    public static ToutiaoObject  getToutiao(int i) throws IOException {
        URL url = new URL("http://ic.snssdk.com/api/news/feed/v88/?session_refresh_idx="+i);//爬取的网址
        URLConnection urlconn = url.openConnection();
        InputStream in=urlconn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String s;
        while((s=reader.readLine()) != null){
            sb.append(s.replaceAll("abstract","abstractt")+"\n");
        }
        reader.close();

        ToutiaoObject object= JSON.parseObject(sb.toString(),new TypeReference<ToutiaoObject>(){});
        return object;
    }
    public static void main(String args[]) throws IOException, InterruptedException {
        for(int i=1;i<1000;i++){
          /*  List<ToutiaoNewsObject> news=getToutiao(i).getData();
            for(ToutiaoNewsObject newso:news ){
                System.out.println(newso.getContent().getAbstractt());
            }*/
            List<String> news=getToutiao(i).getData();
            System.out.println(news);
            Thread.sleep(1000);
        }
    }
}
