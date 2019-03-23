package fishmaple.utils;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class PswEncodeFacade {

    public String pswEncode(String text,String salt){
        return encode(text+salt,"SHA1",encodeCount(salt));
    }

    public boolean checkPsw(String text,String cipher,String salt){
        return checkcode(text+salt,cipher,"SHA1",encodeCount(salt));
    }

    /**
     * @param text 待加密明文
     * @param key 加密方式
     * @param x 递归加密次数
     */
    public String encode(String text,String key,int x){
        if (text == null) {
            return null;
        }
            if(x>1){
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance(key);
                    messageDigest.update(text.getBytes());
                    return encode(getFormattedText(messageDigest.digest()),key,x-1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else{
                return text;
            }
    }

    public String encode(String text,String key){
        return encode(text,key,1);
    }

    public boolean checkcode(String text,String cipher,String key,int x){
        if(encode(text,key,x).equals(cipher))
            return true;
        else
            return false;
    }

    /**
     * 按照既定的明文编码规则获取加密迭代次数 为1000——1999次不等
     * @param text key明文
     *
     */
    public int encodeCount(String text){
        int a=text.length()%10;
        int sum=0;
        for(int i=1;i<100;i++){
            sum += text.charAt(i%text.length());
        }
        a = a*10 + sum%10;
        for(int i=1;i<text.length();i++){
            sum += text.charAt(i);
        }
        a = a*10 + sum%10 +1000;
        return a;
    };

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



}
