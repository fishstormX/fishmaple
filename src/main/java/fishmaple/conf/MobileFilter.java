package fishmaple.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MobileFilter implements WebMvcConfigurer {
    @Autowired
    MobileFHandler mfHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(mfHandler).addPathPatterns("/blog*");
        //registry.addInterceptor(mfHandler).addPathPatterns("/blog/*");
    }

}