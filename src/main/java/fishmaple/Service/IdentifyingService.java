package fishmaple.Service;

import fishmaple.utils.IdentifyingCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class IdentifyingService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    public String getIdentifyingCode(String email){
        String code=stringRedisTemplate.opsForValue().get("email-"+email);
        if(code==null){
            code= IdentifyingCodeUtil.getIdentifyingCodeUtil();
            stringRedisTemplate.opsForValue().set("email-"+email,code,1200, TimeUnit.SECONDS);
        }
        return code;
    }
    public boolean checkCode(String code,String email){
        String codeE=stringRedisTemplate.opsForValue().get("email-"+email);
        if(codeE!=null) {
            return codeE.equals(code);
        }else{
            return false;
        }
    }
}
