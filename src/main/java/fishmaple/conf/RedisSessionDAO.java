package fishmaple.conf;

import fishmaple.utils.JedisUtil;
import fishmaple.utils.SerializeUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_redis_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        Jedis jedis = JedisUtil.getJedis();
        String key = getKey(session.getId());
        String value = SerializeUtil.serialize(session);
        jedis.set(key,value,"NX","EX",7200);
        jedis.close();
    }

    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        Jedis jedis = JedisUtil.getJedis();
        jedis.del(this.getKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Jedis jedis = JedisUtil.getJedis();
        Set<String> keys = jedis.keys(this.keyPrefix + "*");
        jedis.close();
        if(keys != null && keys.size()>0){
            for(String key:keys){
                Session s = (Session)SerializeUtil.unserialize(jedis.get(key));
                sessions.add(s);
            }
        }

        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }
        Set<Session> sessions = new HashSet<Session>();
        Jedis jedis = JedisUtil.getJedis();
        Session s = (Session)SerializeUtil.unserialize(jedis.get(this.getKey(sessionId)));
        jedis.close();
        return s;
    }

    private String getKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;
        return preKey;
    }


    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }


}