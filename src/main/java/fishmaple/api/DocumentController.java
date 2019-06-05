package fishmaple.api;

import fishmaple.Objects.FileObject;
import fishmaple.Service.UserFileServiceImpl;
import fishmaple.shiro.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 鱼鱼
 * 文件上传下载接口
 * */
@RestController
@RequestMapping("/api/document")
public class DocumentController {
   @Autowired
    ShiroService shiroService;
   @Autowired
    UserFileServiceImpl userFileService;

    @GetMapping("list")
    public List<FileObject> listFiles(@RequestParam(defaultValue="")String path){
        return userFileService.getPathFile(shiroService.getCurrentUser().getId(),path);
    }

    @GetMapping("size")
    public Long sizeFiles(@RequestParam(defaultValue="")String path){
        return userFileService.getFileSize(shiroService.getCurrentUser().getId(),path);
    }

    @PostMapping("upload")
    public List<FileObject> uploadFiles(){
        return userFileService.getPathFile(shiroService.getCurrentUser().getId(),"");
    }

}
