package fishmaple.Service;

import fishmaple.utils.Html2StrFacade;
import fishmaple.utils.HttpClientUtil;
import fishmaple.utils.JedisUtil;
import fishmaple.utils.PublicConst;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Service
public class WxService {
    @Value("${localConfig.wx.token}")
    String WX_TOKEN;
    @Value("${localConfig.wx.app-id}")
    String APP_ID;
    @Value("${localConfig.wx.app-secret}")
    String APP_SECRET;
    Logger log= LoggerFactory.getLogger(WxService.class);
    public void getAccessToken() throws IOException {
        Jedis jedis = JedisUtil.getJedis();
        String temp = HttpClientUtil.getHttpreturnMap("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + APP_ID+"&secret="+APP_SECRET).get("access_token");
        if(temp==null) {
            log.error("获取微信授权错误");
            return;
        }
        jedis.set(PublicConst.WX_TOKEN_KEY, temp);
        jedis.close();
    }
    public String getBaiduBaike(String key) throws IOException {
        String temp = HttpClientUtil.getHttpreturnStr("https://baike.baidu.com/item/"+key);
        Document doc= Jsoup.parseBodyFragment(temp);
        String c="";
        try {
            c = doc.select("meta[name=description]").get(0).attr("content");
        }catch(Exception e){
            if(key.substring(0,2).equals("##"))
                return key;
            else
                return "没有词条";
        }

        return c;

    }

}
