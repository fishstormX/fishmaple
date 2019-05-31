package fishmaple.thirdPart.bilibiliWebWorm;

import fishmaple.DAO.BlMapper;
import fishmaple.utils.JedisUtil;
import fishmaple.utils.SerizlizeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;


public class UserWormTask implements Runnable {
    private static BlMapper blMapper;
    private static BlWormService blWormService;
    private static Logger log= LoggerFactory.getLogger(BlWormService.class);
    private static UserWormTask userWormTask;
    private static boolean open=true;
    public static UserWormTask getUserWormTask(BlMapper blMapperT,BlWormService blWormServiceT){
        if (userWormTask == null) {
            synchronized (UserWormTask.class) {
                if (userWormTask == null) {
                    log.info("加载b站用户爬虫任务");
                    userWormTask = new UserWormTask();
                }
            }
        }
        blMapper=blMapperT;
        blWormService=blWormServiceT;
        return userWormTask;
    };
    private UserWormTask(){};

    /*public static UserWormTask getInstance(
            BlMapper blMapper,BlWormService blWormService) {
        if (userWormTask == null) {
            synchronized (UserWormTask.class) {
                if (userWormTask == null) {
                    log.info("加载b站用户爬虫任务");
                    userWormTask = new UserWormTask();
                }
            }
        }
        if(this.blMapper==null){
            this.blMapper=blMapper;
        }
        if()
        return userWormTask;
    }*/

    synchronized void resume(){
        userWormTask.notify();
    }
    synchronized  void pause(){
        try {
            wait();
        } catch (InterruptedException e) {
            log.error("wait的时候出了岔子");
        }
    }

    @Override
    public void run() {
        BlUserObject blUserObject = null;
        Jedis jedis = JedisUtil.getJedis();
        Long i = blMapper.getCount() + 1;
        jedis.set("bl-user-worm-open", "true");
        while (true) {
            synchronized(this) {
            blUserObject = blWormService.getBlUser(i);
            jedis.set("bl-user", SerizlizeUtil.serialize(blUserObject));
            blMapper.save(i++, blUserObject.getSex(), blUserObject.getFans()
                    , blUserObject.getName(), blUserObject.getFace(), blUserObject.getRank());
            try {
                Thread.sleep(300);
                    while(jedis.get("bl-user-worm-open").equals("false")) {
                        userWormTask.wait();
                    }
                }
             catch (InterruptedException e) {
                log.error("爬虫出现问题");
            }
            if(!open){
                break;
            }
            }
        }
    }
}
