package fishmaple.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class Redis4CacheService {
    public static final String BLOG_CONTENT_CACHE="cache:blog_content:";
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisTemplate redisTemplate;
    public void saveCache(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    public void saveCache(String cacheKey,String key,Object value){
        redisTemplate.opsForValue().set(cacheKey+key,value);
    }
    public <T> T  getCache(String key,Class<T> clazz){
        return clazz.cast(redisTemplate.opsForValue().get(key));
    }
    public <T> T getCache(String cacheKey,String key,Class<T> clazz){
        return clazz.cast(redisTemplate.opsForValue().get(cacheKey+key));
    }
    public void flushCache(String cacheKey,String key){
        logger.debug("flushCache:{},{}",cacheKey,key);
        Set<String> keys = redisTemplate.keys(cacheKey+key+"*");
        redisTemplate.delete(keys);
    }
}
