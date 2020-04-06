package fishmaple.api;


import fishmaple.DAO.BlMapper;

import fishmaple.DAO.EventLogMapper;
import fishmaple.DTO.Tongji;
import fishmaple.Objects.SearchResult;
import fishmaple.Service.BaiduTongjiService;
import fishmaple.Service.SearchService;
import fishmaple.thirdPart.bilibiliWebWorm.BlUserObject;
import fishmaple.thirdPart.bilibiliWebWorm.BlWormService;
import fishmaple.thirdPart.bilibiliWebWorm.Const;
import fishmaple.thirdPart.bilibiliWebWorm.TaskState;
import fishmaple.utils.FileUtil;
import fishmaple.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    EventLogMapper eventLogMapper;
    Logger log= LoggerFactory.getLogger(AdapterController.class);




    @RequestMapping("/dosearch")
    public SearchResult doSearch(HttpServletRequest request, @RequestParam(value="content",required=false) String content){
        String ip = request.getRemoteAddr();
        log.info("{} 基本搜索 {}",ip,content);
        ThreadPoolUtil.addTask(()->{
            eventLogMapper.insert2("搜索:-"+content,ip,11);
        });
        return searchService.doSearch(content);
    }
    @RequestMapping("/doNormalSearch")
    public SearchResult doMSearch(HttpServletRequest request,@RequestParam(value="content",required=false) String content){
        String ip = request.getRemoteAddr();
        log.info("{} normal搜索 {}",ip,content);
        ThreadPoolUtil.addTask(()->{
            eventLogMapper.insert2("normal搜索:"+content,ip,11);
        });
        return searchService.doMSearch(content);
    }
    @RequestMapping("/doHighSearch")
    public SearchResult doHighSearch(HttpServletRequest request,@RequestParam(value="content",required=false) String content){
        String ip = request.getRemoteAddr();
        log.info("{} high搜索 {}",ip,content);
        ThreadPoolUtil.addTask(()->{
            eventLogMapper.insert2("high搜索:"+content,ip,11);
        });
        return searchService.doHSearch(content);
    }
    @RequestMapping("/log")
    public String getLog(HttpServletResponse response) throws FileNotFoundException {
        String logComment=FileUtil.getStr("/home/uftp/logt.out");
        return logComment;
    }

    @RequestMapping("getIsBlWormOpen")
    public String getCount(){
        String IsOpen=stringRedisTemplate.opsForValue().get("bl-user-worm-open");
        return IsOpen;
    }
    @RequestMapping("getBlWebWormFail")
    public Set<String> getFail(){
        return stringRedisTemplate.opsForSet().members("failed-id");
    }
    @RequestMapping("nowaBlUser")
    public BlUserObject lastUser() throws IOException, ClassNotFoundException {
        return (BlUserObject)redisTemplate.opsForValue().get("bl-user");
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
        for(int i=0;i<100;i++){
            redisTemplate.delete(Const.redisTaskName+i);
        }
        Thread.sleep(100);
        for(int i=0;i<100;i++){
            redisTemplate.delete(Const.redisIndexName+i);
        }

        log.debug("清除所有爬虫数据");
    }
    @GetMapping("tongji")
    public Tongji Tongji(){
        return baiduTongjiService.getResult();
    }
}
