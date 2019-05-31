package fishmaple.conf;

import fishmaple.utils.JedisUtil;
import fishmaple.utils.SerizlizeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

import static com.alibaba.fastjson.util.IOUtils.UTF8;


public class Redis4CacheConf implements Cache{
    private final long EXPIRE_TIME = 600L;
    private final String COMMON_CACHE_KEY = "COM:";

    private String id;

    public Redis4CacheConf(final String id){
        this.id=id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {

        Jedis jedis=JedisUtil.getJedis();



        jedis.set(id+key.toString(),new String(SerizlizeUtil.serialize(value)));
        jedis.close();
    }

    @Override
    public Object getObject(Object key) {

        Jedis jedis=JedisUtil.getJedis();
        String s=jedis.get(id+key.toString());
        jedis.close();
        Object x=null;
        if(s!=null) {
            try {
                x=SerizlizeUtil.unserizlize(s);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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
    }

    @Override
    public int getSize() {
        Jedis jedis=JedisUtil.getJedis();
        int result=0;
        //jedis.select(DB_INDEX);
        Set<byte[]> keys = jedis.keys((id+"*").getBytes());
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
