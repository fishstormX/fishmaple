package fishmaple.conf;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisSessionDAO extends EnterpriseCacheSessionDAO implements SessionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheManager4Shiro.class);
   /* @Autowired
    RedisCacheManager4session redisCacheManager4session;
    @PostConstruct
    public void init() {
        setCacheManager(redisCacheManager4session);
    }*/

   /* protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("2223331");
        return null;
    }

    @Override
    public Serializable create(Session session) {
        return doCreate(session);
    }


    @Override
    public void update(Session session) throws UnknownSessionException {
        System.out.println("333444");
    }

    @Override
    protected void doUpdate(Session session) {
        System.out.println("222333");
    }

    @Override
    protected void doDelete(Session session) {
        System.out.println("444555");
    }*/



}
