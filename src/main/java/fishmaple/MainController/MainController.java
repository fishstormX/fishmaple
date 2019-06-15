package fishmaple.MainController;


import com.alibaba.fastjson.JSON;
import fishmaple.DAO.ConfigMapper;
import fishmaple.DAO.DictionaryMapper;
import fishmaple.DAO.IssueMapper;
import fishmaple.DAO.ToolMapper;
import fishmaple.DTO.Blog;
import fishmaple.DTO.Dictionary;
import fishmaple.DTO.Issue;
import fishmaple.DTO.Tool;
import fishmaple.Service.BlogService;
import fishmaple.shiro.ShiroService;
import fishmaple.Service.IssueService;
import fishmaple.Service.MobileService;
import fishmaple.Service.ToolService;
import fishmaple.utils.PublicConst;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;


@Controller
public class MainController {

    Logger log= LoggerFactory.getLogger(MainController.class);

    @Autowired
    BlogService blogService;
    @Autowired
    ToolMapper toolMapper;
    @Autowired
    DictionaryMapper dictionaryMapper;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    MobileService mobileService;
    @Autowired
    IssueService issueService;
    @Autowired
    ShiroService shiroService;

    @RequestMapping("/blogEditor")
    public String blogEditor(@RequestParam(required = false) String xx
    ,@RequestParam(required = false) String bid){
           return "blogEditor";
    }

    @RequestMapping("/uc")
    public String userCenter(Model model){
        model.addAttribute("uName",shiroService.getUserName());
        return "userCenter";
    }
    @RequestMapping("/search")
    public String blogEditor(){
        return "search";
    }
    @RequestMapping("/issues")
    public String issues(){
        return "issues";
    }
    @RequestMapping("/issues/new")
    public String issuesNew(){
        return "issuesEdit";
    }
    @RequestMapping("/issue/d")
    public String issuesDetail(@RequestParam String id,HttpServletRequest request, Model model){
        Issue issue= issueService.getDetail(id);
        model.addAttribute("issue",issue);
        return "issue/issueDetail";
    }

    @RequestMapping("/blog/d")
    public String blogDetail(HttpServletRequest request,@RequestParam String bid, Model model){
        Blog blog=blogService.getBlogById(bid,false);
        model.addAttribute("blog",blog);
        return mobileHandler(request,"blogdetail");
    }

    @RequestMapping("/blog/index")
    public String blogIndex(HttpServletRequest request, Model model, HttpServletResponse response) {
        String content="";
        List<Blog> list= blogService.getBlogList();

        model.addAttribute("blog",list);
        return "blogIndex";
    }

    @RequestMapping("/blog")
    public String blog(HttpServletRequest request, Model model, HttpServletResponse response) {
        String content="";
        List<Blog> list= blogService.getBlogList(25,0);
        Collections.shuffle(list);
        for(int i=0;i<11;i++){
              content += list.get(i).getOutLine();
        }
        model.addAttribute("content",content);
        model.addAttribute("ititle",configMapper.getValue("index_title"));
        model.addAttribute("icontent",configMapper.getValue("index_content"));
        model.addAttribute("page",1);
        model.addAttribute("pageD","");
        log.info(request.getRemoteAddr()+" "+request.getRequestURI()+" "+"访问博客");
        return mobileHandler(request,"blog");
    }

    @RequestMapping("/blog/{page}")
    public String blog(HttpServletRequest request,@PathVariable int page, Model model){
        String content="";
        List<Blog> list= blogService.getBlogList(page);
        for(Blog b:list){
            content += b.getOutLine();
        }
        model.addAttribute("content",content);
        model.addAttribute("page",page);
        model.addAttribute("pageD",(page>1)?"第"+page+"页-":"");
        return mobileHandler(request,"blog");
    }
    @RequestMapping("/editor")
    public String editor(HttpServletRequest request){
        log.info(request.getRemoteAddr()+" "+request.getRequestURI()+" "+"访问博客");
        return "editor";
    }


  @RequestMapping("/tool")
    public String main(HttpServletRequest request,Model model){
        String content="";
        List<Tool> list=toolMapper.getAllTools();
        for(Tool tool:list){
            content+=tool.getOutLine();
        }
        model.addAttribute("content",content);

        log.info(request.getRemoteAddr()+" "+request.getRequestURI()+" "+"访问工具箱");
        return "tools";
    }
    @RequestMapping("/dictionary")
    public String wiki(HttpServletRequest request,Model model){
        Long currentUserId = (Long) SecurityUtils.getSubject().getSession().getAttribute("currentUserId");
        String content="";
        List<Dictionary> list=dictionaryMapper.getDictionary();
        for(Dictionary dic:list){
            content+=dic.getOutLine();
        }
        model.addAttribute("content",content);
        log.info(request.getRemoteAddr()+" "+request.getRequestURI()+" "+"访问wiki");
        return "dictionary";
    }
  @RequestMapping("/markdown")
  public String markdown(){
      log.info("访问马克党");
      return "markdown";
  }

    @RequestMapping("/lab")
    public String lab(){
        log.info("访问实验室");
        return "lab";
    }

  @RequestMapping("/fishchat")
  public String chat(){
        log.info("访问聊天室");
        return "fishChat";
  }

    @RequestMapping("/document")
    public String document(){
        log.info("文件托管");
        return "document";
    }

  @RequestMapping("/")
  public String index2Blog(HttpServletRequest request,Model model,HttpServletResponse response){
      return blog(request,model,response);
  }
    @RequestMapping("/index")
    public String index(HttpServletRequest request,Model model,HttpServletResponse response){
        return blog(request,model,response);
    }

    @RequestMapping("/can")
    public String can(HttpServletRequest request){
        return mobileHandler(request,"can");
    }
    @RequestMapping("/beam")
    public String beam(HttpServletRequest request){
       return mobileService.toMobile(request,"beam");
    }



    @RequestMapping("/m/beam")
    public String mbeam(HttpServletRequest request){
                  return "m/beam";
    }


    private String mobileHandler(HttpServletRequest request,String toUrl){
        if (request.getHeader("User-Agent") != null) {
            if(request.getHeader("User-Agent").indexOf("Trident")>=0){
                return  "ieBan";
            }
            for (String mobileAgent : PublicConst.mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    return "m/"+toUrl;
                }
            }
        }
        return toUrl;
    }
}
