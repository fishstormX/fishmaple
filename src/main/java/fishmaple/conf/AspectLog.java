package fishmaple.conf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

//@Aspect        //声明切面类
//@Service
public class AspectLog {
    @Pointcut("execution (* fishmaple.api.BlogController.getBlogList(..))")
    //切入点，在其中定义将要拦截的类
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore() {
        System.out.println("doBefore advice");
    }

    @AfterReturning("pointcut()")
    public void doAfterReturning() {
        System.out.println("doAfterReturning advice");
    }

    @After("pointcut()")
    public void doAfter() {
        System.out.println("doAfter advice");
    }

    @AfterThrowing("pointcut()")
    public void doAfterThrowing() {
        System.out.println("doAfterThrowing advice");
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("doAround advice start");
        Object result = pjp.proceed();
        System.out.println("doAround advice end");
        return result;
    }
}
