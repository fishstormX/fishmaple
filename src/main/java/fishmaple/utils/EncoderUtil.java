package fishmaple.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class EncoderUtil  {

    /**
     * html 编码
     * < &lt; > &gt;
     * */
    public static String htmlEncode(String html){
        html=html.replaceAll("<","&lt")
            .replaceAll(">","&gt");
        return html;
    }

    /**
     * 生成UUID
     * @param i flag值 1添加时间戳 0不加
     * @return
     */
    public static String getUUID(int i){
        switch(i){
            case 0:
                return UUID.randomUUID().toString().replace("-", "").toLowerCase();
            case 1:
                return System.currentTimeMillis()/1000+
                      UUID.randomUUID().toString().replaceAll("-", "");
        }
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
/*
    public String encode(String text,String key,String) {
        public static String md5(String text, String key) throws Exception {
            //加密后的字符串
            String encodeStr=DigestUtils.md5Hex(text + key);
            System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
            return encodeStr;
        }
    }
*/
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
