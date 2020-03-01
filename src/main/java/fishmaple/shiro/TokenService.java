package fishmaple.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import fishmaple.DAO.LoginLogMapper;
import fishmaple.DAO.UserMapper;
import fishmaple.utils.*;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenService {
    // 过期时间60分钟
    private static final long EXPIRE_TIME = 60*60*1000;
    @Autowired
    private PswEncodeFacade pswEncodeFacade;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    LoginLogMapper loginLogMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(TokenService.class);

    /**
     * 临时二维码插入redis中
     * */
    public boolean setTempQrId(String uuid, String token, HttpServletRequest request){
        String name = getUsername(token);
        if(name!=null&&userMapper.selectUserByName(name)!=null){
            Map<String,String> map= RequestUtil.getInfo(request);
            loginLogMapper.insertLog(EncoderUtil.getUUID(1),name,
                    TimeDate.timestamp2time(0L,0),map.get("browser"),map.get("os"),
                    "login-app-qr",request.getRemoteAddr());

            stringRedisTemplate.opsForValue().set("qrid-"+uuid ,token,600, TimeUnit.SECONDS);
            return true;
        }else{
            return false;
        }
    }
    /**
     * 查看二维码对应的token
     * */
    public String getToken(String uuid){
        String s=stringRedisTemplate.opsForValue().get("qrid-"+uuid);
        return s;
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(pswEncodeFacade.pswEncode(secret,username));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,60min后过期
     * @param username 用户名
     * @param secret 用户的密码
     * @return 超级加密的token
     */
    public String sign(String username, String secret,String id) {
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(pswEncodeFacade.pswEncode(secret,username));
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withClaim("id",id)
                .withExpiresAt(date)
                .sign(algorithm);
    }


}
