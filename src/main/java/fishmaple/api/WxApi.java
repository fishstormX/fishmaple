package fishmaple.api;


import fishmaple.DAO.CanMapper;
import fishmaple.DTO.Can;
import fishmaple.Service.CanService;
import fishmaple.Service.WxService;
import fishmaple.utils.EncoderUtil;
import fishmaple.utils.PublicConst;
import fishmaple.utils.SerializeXmlUtil;
import fishmaple.utils.SerizlizeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx")
public class WxApi {
    @Autowired
    WxService wxService;
    @Autowired
    CanMapper canMapper;
    @Value("${localConfig.wx.token}")
    String WX_TOKEN;
    @Value("${localConfig.wx.app-id}")
    String APP_ID;
    @Value("${localConfig.wx.app-secret}")
    String APP_SECRET;

    Logger log= LoggerFactory.getLogger(WxService.class);
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
        @GetMapping("")
        public String signToken(@RequestParam String signature,@RequestParam String timestamp
        ,@RequestParam String nonce,@RequestParam String echostr) throws Exception {
            List<String> list=new ArrayList<String>();
            list.add(timestamp);list.add(nonce);list.add(WX_TOKEN);
            Collections.sort(list);
            String sign=list.get(0)+list.get(1)+list.get(2);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(sign.getBytes());
            sign=getFormattedText(messageDigest.digest());
            if(sign.equals(signature)){
                return echostr;
            }else{
                return ">>>";
            }
        }
    @PostMapping("")
    public String signToken(@RequestBody String signature) throws Exception {
        //log.error(signature);
        Map<String,String> map= SerializeXmlUtil.xmlToMap(signature);
        //canMapper.insertOne(EncoderUtil.getUUID(0),"",map.toString());
        return "<xml>\n" +
                "  <ToUserName><![CDATA["+map.get("FromUserName")+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+map.get("ToUserName")+"]]></FromUserName>\n" +
                "  <CreateTime>+"+map.get("CreateTime")+"+</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA["+wxService.getBaiduBaike(map.get("Content"))+"]]></Content>\n" +
                "</xml>";
    }


}
