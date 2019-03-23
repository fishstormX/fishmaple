package fishmaple.task;

import fishmaple.DAO.AnonymousMapper;
import fishmaple.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Date;

@Component
public class ClearDataService{
    private static final String path="/home/uftp/log.out";
    private static final String paths="/home/uftp/logt.out";

    Logger log= LoggerFactory.getLogger(ClearDataService.class);
    @Autowired
    private AnonymousMapper anonymousMapper;
    //定时任务 每6小时 整理名字数据库 释放超期限一天的名字
    @Scheduled(cron="0 0 0/6 * * ?")
    public void dateTask(){
        log.info("清理了一下过时匿名");
        anonymousMapper.clearOutData(System.currentTimeMillis()/1000);
    }


   @Scheduled(cron="0 0/5 * * * ?")
    public void logTask() throws FileNotFoundException {
            String logComment= FileUtil.getStr(path)
                    .replaceAll(" ","&ensp;")
                    .replaceAll("INFO","<span style='color:green'>INFO</span>")
                    .replaceAll("ERROR","<span style='color:red'>ERROR</span>")
                    .replaceAll("~","<span style='color:orange'>~</span>")
                    .replaceAll("WARN","<span style='color:red'>WARN</span>");
            FileUtil.writeStr(paths,"最后更新时间："+new Date()+"<br>"+logComment);
        }
}
