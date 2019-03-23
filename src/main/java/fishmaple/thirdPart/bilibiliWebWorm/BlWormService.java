package fishmaple.thirdPart.bilibiliWebWorm;

import fishmaple.DAO.BlMapper;
import fishmaple.utils.JedisUtil;
import fishmaple.utils.SerizlizeUtil;
import fishmaple.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import fishmaple.thirdPart.baiduWebWorm.BaiduSearchObject;
import fishmaple.utils.FileUtil;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlWormService {
    @Autowired
    private BlMapper blMapper;
    @Autowired
    BlWormService blWormService;
    static UserWormTask task=null;
    Logger log= LoggerFactory.getLogger(BlWormService.class);

    public BlUserObject getBlUser(Long mid) {
        Jedis jedis= JedisUtil.getJedis();
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
            jedis.sadd("failedId",mid.toString());
        }finally {
            jedis.close();
            return blUserObject;
        }
    }
    //添加新的新任务 不指定i
    public int newThreadTask(){
        Jedis jedis=JedisUtil.getJedis();
        int i=0;
        for(;i<100;i++){
            if(jedis.get(Const.redisTaskName+i)==null||jedis.get(Const.redisTaskName+i).equals("close")){
                ThreadPoolUtil.addTask(new mutilThreadWormTask(blMapper,blWormService,i));
                break;
            }
        }
        jedis.close();
        return i;
    }
    //获取指定的index
    public Long getMidIndex(Integer index){
        Jedis jedis=JedisUtil.getJedis();
        Long mid=new Long(jedis.get(Const.redisIndexName+index));
        jedis.close();
        return mid;
    }
    //获取线程任务列表 i=1 不获取close线程
    public List<TaskState> getMidIndexs(int tem){
        Jedis jedis=JedisUtil.getJedis();
        List<TaskState> indexs=new ArrayList<>();
        for(int i=0;i<100;i++) {
                if(tem==1&&jedis.get(Const.redisTaskName+i)!=null
                        &&jedis.get(Const.redisTaskName+i).equals("close")) {
                }else if(jedis.get(Const.redisTaskName+i)!=null) {
                    indexs.add(new TaskState(jedis.get(Const.redisTaskName+i),
                            jedis.get(Const.redisIndexName+i),i));
                }else{
                    break;
                }
        }
        jedis.close();
        return indexs;
    }

    public void stopTask(Integer index){
        Jedis jedis=JedisUtil.getJedis();
        jedis.set(Const.redisTaskName+index,"close");
        jedis.close();
    }

    /*************************************/
    public void runUserTask(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.set("bl-user-worm-open", "true");
        jedis.close();
        task.resume();
    }
    public void closeAll(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.set("bl-user-worm-open", "true");
        jedis.close();
        task.resume();
    }
    public void pauseUserTask(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.set("bl-user-worm-open", "false");
        jedis.close();
    }

    public void addUserTask(){
            if(task==null){
                Jedis jedis = JedisUtil.getJedis();
                log.info("添加线程任务");
                task= UserWormTask.getUserWormTask(blMapper,blWormService);
                ThreadPoolUtil.addTask(task);
                jedis.close();
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
