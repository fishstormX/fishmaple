package fishmaple.conf;


import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;
@Component
@DependsOn("redisTemplate")
public class RedisCache4BlogConf  extends ApplicationObjectSupport implements Cache{
    private final String COMMON_CACHE_KEY = "COM:";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean inited=false;
    RedisTemplate redisTemplate;
    @Autowired
    ApplicationContextHandler applicationContextHandler;


    private String id="COMMON:";

    public RedisCache4BlogConf(final String id){
        this.id=id;
    }
    public RedisCache4BlogConf(){
        this.id="";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        getRedisTemplate().opsForValue().set(id+key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        return getRedisTemplate().opsForValue().get(id+key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        getRedisTemplate().delete(id+key.toString());
        return null;
    }

    private String getKeys() {
        return  this.id + "*";
    }

    @Override
    public void clear() {
        getRedisTemplate().delete( getRedisTemplate().keys(getKeys()));
    }

    @Override
    public int getSize() {
        return  getRedisTemplate().keys(getKeys()).size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    private  RedisTemplate<String,Object> getRedisTemplate() {
        if(!inited) {
            redisTemplate = ApplicationContextHandler.getBean("redisTemplate",RedisTemplate.class);
            logger.info("-=-==-=-="+redisTemplate);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            inited=true;
        }
        return  redisTemplate;
    }
}
