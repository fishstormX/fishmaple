package fishmaple.api;

import fishmaple.Objects.UploadState;
import fishmaple.Service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author 鱼鱼
 * 富文本编辑器的相关接口
 * */
@RestController
    @CrossOrigin
    @RequestMapping("/api/ueditor")
public class UeditorController {
    @Autowired
    UploadService uploadService;

    @RequestMapping("/uploadvideo")
    public UploadState uploadR(@RequestParam("upfile") MultipartFile file, HttpServletRequest request){

        return uploadService.upload(file,request,"ueditor_video");
    }

    @RequestMapping("/uploadimage")
    public UploadState upload(@RequestParam("upfile") MultipartFile file, HttpServletRequest request){
        return uploadService.upload(file,request,"ueditor_image");
    }


    @RequestMapping("/uploadscrawl")
    public UploadState uploadS(@RequestParam("upfile") MultipartFile file, HttpServletRequest request){

        return uploadService.upload(file,request,"ueditor_scrawl");
    }


    @RequestMapping("/uploadfile")
    public UploadState uploadF(@RequestParam("upfile") MultipartFile file, HttpServletRequest request){

        return uploadService.upload(file,request,"ueditor_attachfile");
    }


    @RequestMapping("")
    public void main(@RequestParam String action,HttpServletRequest request,
                       @RequestParam(required = false) MultipartFile upfile,
                     HttpServletResponse response) throws ServletException, IOException {
        if(action.equals("config")) {
                response.sendRedirect("/ueditor/config.json");
        }else{
            request.getRequestDispatcher("/api/ueditor/"+action).forward(request,response);
        }

    }
}

