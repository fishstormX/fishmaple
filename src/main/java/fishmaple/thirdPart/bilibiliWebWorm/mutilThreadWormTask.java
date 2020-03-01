package fishmaple.thirdPart.bilibiliWebWorm;

import fishmaple.DAO.BlMapper;
import fishmaple.conf.ApplicationContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class mutilThreadWormTask implements Runnable{
    //线程的开关 false的时候break该线程
    private boolean open=true;
    private BlMapper blMapper;
    private BlWormService blWormService;
    //爬虫的mid索引位置
    private Long midIndex;
    //线程的索引
    private Integer threadIndex;
    private static Logger log= LoggerFactory.getLogger(BlWormService.class);
    public mutilThreadWormTask(BlMapper blMapperT, BlWormService blWormServiceT,Integer i){
        this.blMapper=blMapperT;
        this.blWormService=blWormServiceT;
        this.threadIndex=i;
    }
    public void close(){
        getStringRedisTemplate().opsForValue().set(Const.redisTaskName+threadIndex,"close");
    }
    @Override
    public void run() {
        getStringRedisTemplate().opsForValue().set(Const.redisTaskName+threadIndex,"open");
        midIndex=(getStringRedisTemplate().opsForValue().get(Const.redisIndexName+threadIndex)==null?0L:new Long(getStringRedisTemplate().opsForValue().get(Const.redisIndexName+threadIndex)));
        while(true){
            synchronized ("String") {
                blMapper.updateHandle(threadIndex, midIndex++);
                getStringRedisTemplate().opsForValue().set(Const.redisIndexName+threadIndex,midIndex.toString());
            }
           /* try {
                    Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            if(getStringRedisTemplate().opsForValue().get(Const.redisTaskName+threadIndex)==null||getStringRedisTemplate().opsForValue().get(Const.redisTaskName+threadIndex).equals("close")){
                break;
           }
        }
    }

    private RedisTemplate<String,String> getStringRedisTemplate() {
        RedisTemplate<String,String> redisTemplate =  ApplicationContextHandler.getBean("stringRedisTemplate",RedisTemplate.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return  redisTemplate;
    }

}
