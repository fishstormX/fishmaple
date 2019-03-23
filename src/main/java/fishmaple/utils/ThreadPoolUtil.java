package fishmaple.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolUtil {
    private ThreadPoolUtil(){

    }
    private static Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);

    private static ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
    static{
        //如果池中的实际线程数小于1，无论是否其中有空闲的线程,都会给新的任务产生新的线程
        taskExecutor.setCorePoolSize(10);
        //如果提交的线程数大于corePoolSize并且小于maxPoolSize，只会创建corePoolSize的线程数被创建，然后将任务提交到队列中，直到队列慢为止
        taskExecutor.setMaxPoolSize(10);
        //任务队列最大长度
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        //
        taskExecutor.setKeepAliveSeconds(2000);

        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        taskExecutor.initialize();
    }

    public static void addTask(Runnable task) {
        taskExecutor.execute(task);
    }

    public static Future addReturnedTask(Callable task){
        return taskExecutor.submit(task);
    }

    public static void shutdown() {
        try{
            taskExecutor.shutdown();
        }catch(Exception ex) {
            log.error("关闭ThreadPoolTaskUtil失败", ex);
        }
    }

}
