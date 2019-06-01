package fishmaple.api;

import fishmaple.DTO.Blog;
import fishmaple.DTO.Tool;
import fishmaple.Objects.UploadState;
import fishmaple.Service.BlogService;
import fishmaple.Service.UploadService;
import fishmaple.shiro.ShiroService;
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
                    blog.getIsOriginal())){
                return "success";
                }else{
                    return "您不能编辑别人的博文";
                }
            }else {
               blogService.save(blog.getContent(), blog.getTitle(),
                        shiroService.getCurrentUser().getName(), blog.getTags(),
                       blog.getUseDictionary(),blog.getCover(),blog.getIsOriginal());
            }
            return "success";
        }else{
            return "用户无权限";
        }
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



    @PostMapping("/uploadBlogCover")
    public UploadState upload (@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return uploadService.upload(file,request,"blog_cover");
    }
}
