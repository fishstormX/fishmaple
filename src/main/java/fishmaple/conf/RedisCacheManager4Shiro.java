package fishmaple.conf;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 实现用户角色、权限缓存管理
 * @author lh@erongdu.com
 *
 */
@Component
public class RedisCacheManager4Shiro implements CacheManager {

    @Resource
    RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager4Shiro.class);
    //@Resource RedisUtil redisUtil;

    @Override
    public <K, V> Cache<K, V> getCache(String arg0) throws CacheException {
        return new RedisCache<K, V>();
    }

    class RedisCache<K, V> implements Cache<K, V>{

        public RedisCache() {
            redisTemplate.boundHashOps(CACHE_KEY).expire(180, TimeUnit.MINUTES);
        }

        private static final String CACHE_KEY = "shiro_redis_subject";

        @Override
        public void clear() throws CacheException {
            redisTemplate.delete(CACHE_KEY);
        }

        private String toString(Object obj){
            if(obj instanceof String){
                return obj.toString();
            }else{
                return JSONObject.toJSONString(obj);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public V get(K k) throws CacheException {
            return (V)redisTemplate.boundHashOps(CACHE_KEY).get(k);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Set<K> keys() {
            return (Set<K>)redisTemplate.boundHashOps(CACHE_KEY).keys();
        }

        @Override
        public V put(K k, V v) throws CacheException {
            redisTemplate.boundHashOps(CACHE_KEY).put(k, v);
            return v;
        }

        @Override
        public V remove(K k) throws CacheException {
            V v = get(k);
            redisTemplate.boundHashOps(CACHE_KEY).delete(k);
            return v;
        }

        @Override
        public int size() {
            int size = redisTemplate.boundHashOps(CACHE_KEY).size().intValue();
            return size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<V> values() {
            return (Collection<V>)redisTemplate.boundHashOps(CACHE_KEY).values();
        }

        public String getCacheKey() {
            return "RedisCache";
        }

    }

}