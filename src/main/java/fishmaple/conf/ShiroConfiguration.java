package fishmaple.conf;

import fishmaple.shiro.AuthRealm;
import fishmaple.shiro.CredentialsMatcher;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;

/**
 * shiro的配置类
 *
 */

@Configuration
public class ShiroConfiguration {




    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //filterChainDefinitionMap.put("/tool", "anon");//表示可以匿名访问
        filterChainDefinitionMap.put("/blogEditor", "user");
        filterChainDefinitionMap.put("/document", "authc");
        filterChainDefinitionMap.put("/uc", "authc");
        filterChainDefinitionMap.put("/logout", "logout");
        //filterChainDefinitionMap.put("/*", "anon");
        filterChainDefinitionMap.put("/templates/*.*", "authc");//表示需要认证才可以访问
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /*@Bean(name = "sessionManager")
    public DefaultWebSessionManager securityManager(@Qualifier("redisSessionDAO")RedisSessionDAO redisSessionDAO){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(7200000L);
        defaultWebSessionManager.setSessionDAO(redisSessionDAO);
        return defaultWebSessionManager;
    }*/

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(//@Qualifier("sessionManager")WebSessionManager webSessionManager,
                                           @Autowired RedisSessionDAO redisSessionDAO,
                                           @Qualifier("authRealm") AuthRealm authRealm,
                                           @Qualifier("redisCacheManager4Shiro") RedisCacheManager4Shiro redisCacheManager4Shiro) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
       /* DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(7200000L);
        defaultWebSessionManager.setSessionDAO(redisSessionDAO);*/
         RedisSessionManager redisSessionManager = new RedisSessionManager();
        redisSessionManager.setGlobalSessionTimeout(720000L);
        redisSessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        manager.setCacheManager(redisCacheManager4Shiro);
        manager.setSessionManager(redisSessionManager);
        manager.setRealm(authRealm);
        return manager;
    }


    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }



    //配置自定义的权限登录器
    @Bean(name = "authRealm")
    public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    //配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}