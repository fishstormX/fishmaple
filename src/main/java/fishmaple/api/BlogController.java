package fishmaple.api;

import fishmaple.DTO.Blog;
import fishmaple.DTO.Tool;
import fishmaple.Objects.ElementOption;
import fishmaple.Objects.UploadState;
import fishmaple.Service.BlogService;
import fishmaple.Service.BlogTopicService;
import fishmaple.Service.UploadService;
import fishmaple.shiro.ShiroService;
import fishmaple.utils.HttpClientUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author 鱼鱼
 * 博客的相关接口
 * */
@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UploadService uploadService;
    @Autowired
    ShiroService shiroService;
    @Autowired
    BlogTopicService blogTopicService;

    @PostMapping("")
    public String blogEditor(@RequestBody Blog blog){
        if(shiroService.isLogin()){
            if(blog.getTitle().equals("")){
                return "请输入标题";
            }
            if(blog.getContent().equals("")){
                return "请输入正文";
            }

            if(!blog.getId().equals("0")){
                if(blogService.update(blog.getId(),blog.getContent(),blog.getTitle(),
                    shiroService.getUserName(),blog.getTags(),blog.getUseDictionary(),blog.getCover(),
                    blog.getIsOriginal(),blog.getTopicId())){

                    String id=blog.getId();
                    new Thread(
                            ()-> {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                HttpClientUtil.pushBlog(id);
                            }
                    ).start();

                return "success";
                }else{
                    return "您不能编辑别人的博文";
                }
            }else {
               blogService.save(blog.getContent(), blog.getTitle(),
                        shiroService.getCurrentUser().getName(), blog.getTags(),
                       blog.getUseDictionary(),blog.getCover(),blog.getIsOriginal(),blog.getTopicId());
                //推送消息
                    new Thread(
                            ()-> {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                HttpClientUtil.pushBlog(blogService.getLastBlog());
                            }
                ).start();
            }
            return "success";
        }else{
            return "用户无权限";
        }
    }

    @PostMapping("/{bid}/like")
    public String blogLike(@PathVariable String bid,HttpServletRequest request){
        blogService.addLike(bid,request.getRemoteAddr());
        return "success";
    }


    @DeleteMapping("/{bid}")
    public String deleteBlog (@PathVariable String bid){
            return  blogService.deleteBlog(bid);
    }

    @RequestMapping("/tag")
    public Map<Integer,List<String>> getTags(){
        Map<Integer,List<String>> map=new HashMap<>();
        for(int i=0;i<4;i++){
            List<String> tagGroup = blogService.getTagsByType(i);
            map.put(i,tagGroup);
        }
        return map;
    }
    @ApiOperation(value="更新信息", notes="根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "page", value = "用户实体user", required = true, dataType = "int"),
            @ApiImplicitParam(name = "page2", value = "用户实体user", required = true, dataType = "Tool")
    })
    @GetMapping("")
    public List<Blog> getBlogList(HttpServletResponse response,HttpServletRequest request, @RequestParam(defaultValue="1",required = false)int page, @RequestParam(required = false)String tag){
        List<Blog> list;
        if(tag==null){
            list=blogService.getBlogList(page);
        }
        else{
            list=blogService.getBlogList(page,tag);
        }

        return list;
    }

    @GetMapping("/count")
    public Integer getBlogNum(@RequestParam(required = false) String tag){
        if(tag==null)
            return blogService.getCount();
        else{
            return blogService.getCount(tag);
        }
    }

    @GetMapping("/detail")
    public Blog getBlogContent(@RequestParam String bid,@RequestParam(required = false,defaultValue="false")boolean appAbled){
        return blogService.getBlogById(bid,appAbled);
    }

    @PostMapping("/blogBak")
    public String autoSave(@RequestBody Blog blog) {
        if(shiroService.isLogin()){
            if(blog.getContent().equals("")){
                return "请输入正文";
            }
            boolean flag;
            if(null!=blog.getId()&&!blog.getId().equals("0")){
                flag = blogService.autoSave(blog.getId(),shiroService.getCurrentUser().getId(),blog.getContent());
            }else {
                flag = blogService.autoSave("",shiroService.getCurrentUser().getId(),blog.getContent());
            }
            if(flag){
                return "success";
            }else{
                return "用户权限不足";
            }
        }else{
            return "当前会话过期，请在新建窗口中重新登陆";
        }
    }

    @PostMapping("/uploadBlogCover")
    public UploadState upload (@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return uploadService.upload(file,request,"blog_cover");
    }

    @PostMapping("/blogTopic")
    public Map<String,Object> AddTopic(@RequestBody Map<String,String> map){
        String gid=map.get("gid");
        String group=map.get("group");
        String topic=map.get("topic");

        return blogTopicService.setNewTopic(gid,group,topic);
    }

    @GetMapping("/blogTopic")
    public List<ElementOption> getTopic(){
        return blogTopicService.getTopics();
    }

    @GetMapping("/blogTopic/group")
    public List<ElementOption> getTopicGroup(){
        return blogTopicService.getGroups();
    }

}
