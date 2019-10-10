package fishmaple.api;


import fishmaple.DAO.BlMapper;

import fishmaple.DTO.Tongji;
import fishmaple.Objects.SearchResult;
import fishmaple.Service.BaiduTongjiService;
import fishmaple.Service.SearchService;
import fishmaple.thirdPart.bilibiliWebWorm.BlUserObject;
import fishmaple.thirdPart.bilibiliWebWorm.BlWormService;
import fishmaple.thirdPart.bilibiliWebWorm.Const;
import fishmaple.thirdPart.bilibiliWebWorm.TaskState;
import fishmaple.utils.FileUtil;
import fishmaple.utils.JedisUtil;
import fishmaple.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author 鱼鱼
 * 搜索和日志查看的接口
 * */
@RestController
@RequestMapping("/api")
public class AdapterController {
    @Autowired
    SearchService searchService;
    @Autowired
    BlWormService blWormService;
    @Autowired
    BlMapper blMapper;
    @Autowired
    BaiduTongjiService baiduTongjiService;
    Logger log= LoggerFactory.getLogger(AdapterController.class);

    @RequestMapping("/dosearch")
    public SearchResult doSearch(@RequestParam(value="content",required=false) String content){
        return searchService.doSearch(content);
    }
    @RequestMapping("/doNormalSearch")
    public SearchResult doMSearch(@RequestParam(value="content",required=false) String content){
        return searchService.doMSearch(content);
    }
    @RequestMapping("/doHighSearch")
    public SearchResult doHighSearch(@RequestParam(value="content",required=false) String content){
        return searchService.doHSearch(content);
    }
    @RequestMapping("/log")
    public String getLog(HttpServletResponse response) throws FileNotFoundException {
        String logComment=FileUtil.getStr("/home/uftp/logt.out");
        return logComment;
    }

    @RequestMapping("getIsBlWormOpen")
    public String getCount(){
        Jedis jedis= JedisUtil.getJedis();
        String IsOpen=jedis.get("bl-user-worm-open");
        jedis.close();
        return IsOpen;
    }
    @RequestMapping("getBlWebWormFail")
    public String getFail(){
        Jedis jedis= JedisUtil.getJedis();
        String fail=jedis.smembers("failed-id").toString();
        jedis.close();
        return fail;
    }
    @RequestMapping("nowaBlUser")
    public BlUserObject lastUser() throws IOException, ClassNotFoundException {
        Jedis jedis= JedisUtil.getJedis();
        String s=jedis.get("bl-user");
        jedis.close();
        Object obj= SerializeUtil.unserialize(s);
        if(obj instanceof BlUserObject){
            return (BlUserObject) obj;
        }
        return null;
    }
    @RequestMapping("closeWorm")
    public void close() {
       blWormService.pauseUserTask();
    }
    @RequestMapping("openWorm")
    public void open() {
        blWormService.addUserTask();
        blWormService.runUserTask();
    }
    @RequestMapping("addBUTask")
    public void addTask(){
        blWormService.newThreadTask();
    }
    @RequestMapping("getThreadMidIndex")
    public Long getMidIndex(@RequestParam Integer threadIndex){
        return blWormService.getMidIndex(threadIndex);
    }
    @RequestMapping("stopTask")
    public String stopTask(@RequestParam Integer i){
        blWormService.stopTask(i);
        return "SUCCESS";
    }
    @GetMapping("threadTaskState")
    public List<TaskState> getThreadState(){
        return blWormService.getMidIndexs(0);
    }
    @GetMapping("threadNormalTaskState")
    public List<TaskState> getNormalThreadState(){
        return blWormService.getMidIndexs(1);
    }
    @GetMapping("opennew")
    public int openNew(){
        return blWormService.newThreadTask();
    }
    @GetMapping("closeAll")
    public void closeAll() throws InterruptedException {
        Jedis jedis=JedisUtil.getJedis();
        for(int i=0;i<100;i++){
            jedis.del(Const.redisTaskName+i);
        }
        Thread.sleep(100);
        for(int i=0;i<100;i++){
            jedis.del(Const.redisIndexName+i);
        }

        log.debug("清除所有爬虫数据");
        jedis.close();
    }
    @GetMapping("tongji")
    public Tongji Tongji(){
        return baiduTongjiService.getResult();
    }
}
