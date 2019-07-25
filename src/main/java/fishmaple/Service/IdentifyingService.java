package fishmaple.Service;

import fishmaple.utils.IdentifyingCodeUtil;
import fishmaple.utils.JedisUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class IdentifyingService {

    public String getIdentifyingCode(String email){
        Jedis jedis = JedisUtil.getJedis();
        String code=jedis.get("email-"+email);
        if(code==null){
            code= IdentifyingCodeUtil.getIdentifyingCodeUtil();
            jedis.set("email-"+email,code,"nx","ex",1200);
        }
        jedis.close();
        return code;
    }
    public boolean checkCode(String code,String email){
        Jedis jedis = JedisUtil.getJedis();
        String codeE=jedis.get("email-"+email);
        jedis.close();
        if(codeE!=null) {
            return codeE.equals(code);
        }else{
            return false;
        }
    }
}
