package fishmaple.conf;

import fishmaple.shiro.ShiroService;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class NFHandler implements HandlerInterceptor {
    @Autowired
    ShiroService shiroService;
    Logger logger = LoggerFactory.getLogger(NFHandler.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.getSession().setMaxInactiveInterval(12 * 60 * 60);
        //毫秒
        shiroService.getCurrentSubject().getSession().setTimeout(12 * 60 * 60 * 1000);
        logger.error("拦截器：修正用户session过时时间");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
