package fishmaple.thirdPart.bilibiliWebWorm;

import fishmaple.DAO.BlMapper;
import fishmaple.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import fishmaple.utils.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlWormService {
    @Autowired
    private BlMapper blMapper;
    @Autowired
    BlWormService blWormService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    static UserWormTask task=null;
    Logger log= LoggerFactory.getLogger(BlWormService.class);

    public BlUserObject getBlUser(Long mid) {
        BlUserObject blUserObject=new BlUserObject();
        try {
            URL url = new URL("https://api.bilibili.com/x/web-interface/card?mid="+mid);//爬取的网址
            HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
            InputStream in= null;
            in = urlconn.getInputStream();
            StringBuffer sb = new StringBuffer(FileUtil.getStr(in,""));
            String s =sb.substring(sb.indexOf("card")+6,sb.indexOf("following")-2);
             blUserObject=JSON.parseObject(s,
                    new TypeReference<BlUserObject>() {});
        } catch (IOException e) {
            log.error("——————————————————");
            stringRedisTemplate.opsForSet().add("failedId",mid.toString());
        }
        return blUserObject;
    }
    //添加新的新任务 不指定i
    public int newThreadTask(){
        int i=0;
        for(;i<100;i++){
            if(stringRedisTemplate.opsForValue().get(Const.redisTaskName+i)==null||stringRedisTemplate.opsForValue().get(Const.redisTaskName+i).equals("close")){
                ThreadPoolUtil.addTask(new mutilThreadWormTask(blMapper,blWormService,i));
                break;
            }
        }
        return i;
    }
    //获取指定的index
    public Long getMidIndex(Integer index){
        Long mid=new Long(stringRedisTemplate.opsForValue().get(Const.redisIndexName+index));
        return mid;
    }
    //获取线程任务列表 i=1 不获取close线程
    public List<TaskState> getMidIndexs(int tem){
        List<TaskState> indexs=new ArrayList<>();
        for(int i=0;i<100;i++) {
                if(tem==1&& stringRedisTemplate.opsForValue().get(Const.redisTaskName+i)!=null
                        && stringRedisTemplate.opsForValue().get(Const.redisTaskName+i).equals("close")) {
                }else if( stringRedisTemplate.opsForValue().get(Const.redisTaskName+i)!=null) {
                    indexs.add(new TaskState( stringRedisTemplate.opsForValue().get(Const.redisTaskName+i),
                            stringRedisTemplate.opsForValue().get(Const.redisIndexName+i),i));
                }else{
                    break;
                }
        }
        return indexs;
    }

    public void stopTask(Integer index){
        stringRedisTemplate.opsForValue().set(Const.redisTaskName+index,"close");
    }

    /*************************************/
    public void runUserTask(){;
        stringRedisTemplate.opsForValue().set("bl-user-worm-open", "true");
        task.resume();
    }
    public void closeAll(){
        stringRedisTemplate.opsForValue().set("bl-user-worm-open", "true");
        task.resume();
    }
    public void pauseUserTask(){
        stringRedisTemplate.opsForValue().set("bl-user-worm-open", "false");
    }

    public void addUserTask(){
            if(task==null){
                log.info("添加线程任务");
                task= UserWormTask.getUserWormTask(blMapper,blWormService);
                ThreadPoolUtil.addTask(task);
            }

    }
  /* public static void main(String args[]) throws IOException {
       URL url = new URL("https://api.bilibili.com/x/web-interface/card?mid=730732");//爬取的网址
       HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
       InputStream in=urlconn.getInputStream();
       StringBuffer sb = new StringBuffer(FileUtil.getStr(in,""));
       String s =sb.substring(sb.indexOf("card")+6,sb.indexOf("following")-2);
       System.out.println(s);
       BlUserObject object=JSON.parseObject(s,
               new TypeReference<BlUserObject>() {});
       System.out.println(object.getFans()+""+object.getName());
   }*/
}
