package fishmaple.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Redis缓存配置类
 * @author szekinwin
 *
 */
@Configuration
@EnableCaching
public class JedisUtil {
        //最多总连接数 超过数量会无法获取连接
        private static Integer maxTotal=100;
        private static String host="localhost";
        //活跃连接
        private static Integer maxIdle=40;
        private static Integer port=6379;
        private static Integer maxWaitMillis=0;
        private static Integer timeout=10000;

    public static void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }

    private static JedisPool jedisPool=null;
        private static Logger log= LoggerFactory.getLogger(JedisUtil.class);

        public static Jedis getJedis(){
            return jedisPool.getResource();
        }

        static {
            if(null==jedisPool){
                log.debug("加载jedisPool");
                JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                jedisPoolConfig.setMaxIdle(maxIdle);
                jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
                jedisPoolConfig.setMaxTotal(maxTotal);
                jedisPool=new JedisPool(jedisPoolConfig,host,port,timeout);
            }
        }

}