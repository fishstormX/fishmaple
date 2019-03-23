package fishmaple;

import javafx.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder// implements ApplicationContextAware
{
/*
    private static ApplicationContext applicationContext;

    public  void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void printAllBeans(ApplicationContext applicationContext) {
        applicationContext=(applicationContext==null?getApplicationContext():applicationContext);
        String[] beans = applicationContext
                .getBeanDefinitionNames();  System.out.println("***********************包检查开始******************************");
        for (String beanName : beans) {
            Class<?> beanType = applicationContext
                    .getType(beanName);

            System.out.println("BeanName:" + beanName+"Bean的类型：" + beanType+
                    "Bean所在的包：" + beanType.getPackage());

        }System.out.println("***********************包检查完毕******************************");
    }
*/
}
