package fishmaple.conf;

import fishmaple.utils.JedisUtil;
import fishmaple.utils.SerializeUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;


public class RedisCache4BlogConf implements Cache{
    private final long EXPIRE_TIME = 600L;
    private final String COMMON_CACHE_KEY = "COM:";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String id;

    public RedisCache4BlogConf(final String id){
        this.id=id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {

        Jedis jedis=JedisUtil.getJedis();
        try{
            jedis.set(id+key.toString(),new String(SerializeUtil.serialize(value)));
        }catch(Exception e){
            logger.error(e.getMessage()+id);
        }finally{
            jedis.close();
        }
    }

    @Override
    public Object getObject(Object key) {

        Jedis jedis=JedisUtil.getJedis();
        String s=jedis.get(id+key.toString());
        jedis.close();
        Object x=null;
        if(s!=null) {
                x= SerializeUtil.unserialize(s);
        }
        return x;
    }

    @Override
    public Object removeObject(Object key) {
        Jedis jedis=JedisUtil.getJedis();
        jedis.del(key.toString());
        jedis.close();
        return null;
    }

    private String getKeys() {
        return COMMON_CACHE_KEY + this.id + ":*";
    }

    @Override
    public void clear() {
        Jedis jedis=JedisUtil.getJedis();
        int result=0;
        //jedis.select(DB_INDEX);
        Set<byte[]> keys = jedis.keys((id+"*").getBytes());
        for (byte[] key : keys) {
            jedis.del(key);
        }
        jedis.close();
    }

    @Override
    public int getSize() {
        Jedis jedis=JedisUtil.getJedis();
        int result=0;
        //jedis.select(DB_INDEX);
        Set<byte[]> keys = jedis.keys((id+"*").getBytes());
        jedis.close();
        if (null != keys && !keys.isEmpty()) {
            result = keys.size();
        }
        return result;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
