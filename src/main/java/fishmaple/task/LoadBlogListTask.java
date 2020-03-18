package fishmaple.task;

import fishmaple.DTO.Blog;
import fishmaple.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

@Component
public class LoadBlogListTask {
    @Autowired
    BlogService blogService;

    public List<Blog> getBlogList() {
        return blogList;
    }

    private List<Blog> blogList=null;

    public String getOutLine() {
        return outLine;
    }

    private String outLine="";
    @PostConstruct
    public void init(){
       outLine=setBlogs();
    }

    //@Scheduled(cron="0 0/20 * * * ?")
    public void loadRandomBlogs(){

        outLine =setBlogs();
    }
    private String setBlogs(){
        String tmp="";
        blogList= blogService.getBlogList(100,0);
        Collections.shuffle(blogList);
        for(int i=0;i<12;i++){
            tmp += blogList.get(i).getOutLine();
        }
        return tmp;
    }

}
