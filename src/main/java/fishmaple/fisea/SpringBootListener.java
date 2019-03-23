package fishmaple.fisea;

import fishmaple.conf.ApplicationContextProvider;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

//@Component
public class SpringBootListener implements ApplicationListener<ApplicationStartedEvent> {




    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("开始啦");
        ApplicationContextProvider.printAllBeans(null);
    }
}
