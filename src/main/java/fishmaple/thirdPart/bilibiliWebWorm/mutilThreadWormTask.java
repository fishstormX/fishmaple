package fishmaple.thirdPart.bilibiliWebWorm;

import fishmaple.DAO.BlMapper;
import fishmaple.utils.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

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
        Jedis jedis=JedisUtil.getJedis();
        jedis.set(Const.redisTaskName+threadIndex,"close");
    }
    @Override
    public void run() {
        Jedis jedis=JedisUtil.getJedis();
        jedis.set(Const.redisTaskName+threadIndex,"open");
        midIndex=(jedis.get(Const.redisIndexName+threadIndex)==null?0L:new Long(jedis.get(Const.redisIndexName+threadIndex)));
        while(true){
            synchronized ("String") {
                blMapper.updateHandle(threadIndex, midIndex++);
                jedis.set(Const.redisIndexName+threadIndex,midIndex.toString());
            }
           /* try {
                    Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            if(jedis.get(Const.redisTaskName+threadIndex)==null||jedis.get(Const.redisTaskName+threadIndex).equals("close")){
                jedis.close();
                break;
           }
        }
    }

}
