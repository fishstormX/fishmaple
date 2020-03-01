package fishmaple;


import fishmaple.DAO.IssueMapper;
import fishmaple.thirdPart.bilibiliWebWorm.Const;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.nio.charset.Charset;

@SpringBootApplication
@MapperScan(value = "fishmaple.DAO")
@EnableCaching              //开启缓存
@EnableTransactionManagement    //开启事务
@EnableScheduling   //开启定时器
public class MainApplication {
    @Autowired
    IssueMapper issueMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    private static Logger log= LoggerFactory.getLogger(MainApplication.class);


    public static void main(String[] args)
    {
          SpringApplication.run(MainApplication.class, args);
    }


     //在某配置类中添加如下内容
    // 监听的http请求的端口,需要在application配置中添加http.port=端口号  如80
    @Value("${server.http-port}")
    Integer httpPort;

    /*@Bean
    public RedisTemplate<String,String> stringRedisTemplate(){
        RedisTemplate<String,String> redisTemplateTmp = redisTemplate;
        redisTemplateTmp.setKeySerializer(new StringRedisSerializer());
        redisTemplateTmp.setValueSerializer(new StringRedisSerializer());
        redisTemplateTmp.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateTmp.setHashValueSerializer(new StringRedisSerializer());
        return  redisTemplateTmp;
    }*/

    @Bean
    public ServletWebServerFactory servletContainer2() {
        stringRedisTemplate.delete("currentUsers");
        for(int i=0;i<100;i++){
            stringRedisTemplate.delete(Const.redisIndexName+i);
            stringRedisTemplate.delete(Const.redisTaskName+i);
        }
        log.info("清除用户记录存档");
        log.info("系统编码："+Charset.defaultCharset().name());
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
        return tomcat;
    }

    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);
        return connector;
    }


//    // springboot2 写法
//    //@Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
//
//   //@Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(httpPort);
//        connector.setSecure(false);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(httpsPort);
//        return connector;
//    }
}
