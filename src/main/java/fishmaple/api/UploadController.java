package fishmaple.api;

import fishmaple.DAO.ResourceMapper;
import fishmaple.DAO.ToolMapper;
import fishmaple.DTO.Resource;
import fishmaple.DTO.Tool;
import fishmaple.Objects.ToolObject;
import fishmaple.Service.UploadService;
import fishmaple.utils.FileUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;
/**
 * @author 鱼鱼
 * 全局资源上传的接口（不含富文本编辑内容）
 * */
@RestController
@RequestMapping("/api")
public class UploadController {
    @Autowired
    private ToolMapper toolDAO;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ResourceMapper resourceMapper;

    @RequestMapping(value="/tool/upload",method=RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        String resourceId="";
        try {
            resourceId=uploadService.uploadTool(file.getBytes(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }
        return resourceId;
    }

    @RequestMapping(value="/tool/upload",method=RequestMethod.DELETE)
    public String delete(@RequestParam("id")String id){

        //TODO
         return "success";
    }

    @RequestMapping(value="/resource/{id}/download",method=RequestMethod.GET)
    public void download(@PathVariable String id, HttpServletResponse response){
        Resource r=resourceMapper.getById(id);
        try (
                InputStream inputStream = new FileInputStream(
                        new File(r.getPath(),r.getName()));
                OutputStream outputStream = response.getOutputStream()
        ) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + r.getName());   // 设置文件名
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
