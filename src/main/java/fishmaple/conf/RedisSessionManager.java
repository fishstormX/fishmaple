package fishmaple.conf;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重写DefaultWebSessionManager的retrieveSession方法，防止一个接口重复读取redis的session
 * @author lh@erongdu.com
 *
 */
public class RedisSessionManager extends DefaultWebSessionManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionManager.class);

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if(sessionKey instanceof WebSessionKey){
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        Session session = null;
        if(request != null && sessionId != null){
            session =  (Session) request.getAttribute(sessionId.toString());
        }
        if(session != null){
            return session;
        }
        try{
            session = super.retrieveSession(sessionKey);
        }catch(UnknownSessionException e){
            logger.debug(e.getMessage());
        }
        if(request != null && sessionId != null && session != null){
            request.setAttribute(sessionId.toString(), session);
        }

        return session;
    }

}