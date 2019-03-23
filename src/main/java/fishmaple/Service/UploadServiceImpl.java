package fishmaple.Service;

import fishmaple.DAO.ResourceMapper;
import fishmaple.Objects.UploadState;
import fishmaple.utils.EncoderUtil;
import fishmaple.utils.FileUtil;
import fishmaple.utils.TimeDate;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService{

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public String uploadTool(byte[] file, String fileName) throws UnsupportedEncodingException {
        long timenow=System.currentTimeMillis()/1000;
        String id=getTimelineId(timenow);
        String tStamp= TimeDate.getTimeStampNow();
        String rootPath=getRootPath();
        String filePath = rootPath+"/static/tool/"+tStamp+"/"+id+"/";
        String suffix = FileUtil.getSuffix(fileName);
        resourceMapper.save(id,"工具",timenow,1,fileName,filePath,id,suffix);
        try {
            FileUtil.uploadFile(file,fileName,filePath);
        } catch (Exception e) {
            return "0";
        }
        return id;
    }

    private  String getTimelineId(Long timenow){
        return System.currentTimeMillis()/1000+
                UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String upload2File(byte[] file,String fileName,String type) throws UnsupportedEncodingException {
        long timenow=System.currentTimeMillis()/1000;
        String id= EncoderUtil.getUUID(0);
        String tStamp= TimeDate.getTimeStampNow();
        String rootPath=getRootPath();
        String filePath = rootPath+"/static/"+type+"/"+tStamp+"/";

        String suffix=FileUtil.getSuffix(fileName);
        String realName = id+"."+suffix;
        resourceMapper.save(id,type,System.currentTimeMillis()/1000
                ,1,fileName,filePath,"/"+type+"/"+tStamp+"/"+realName,suffix);
        try {
            FileUtil.uploadFile(file,realName,filePath);
        } catch (Exception e) {
            return "0";
        }
        return "/"+type+"/"+tStamp+"/"+realName;
    }

    @Override
    public UploadState upload(MultipartFile file, HttpServletRequest request,String type) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String resourcePath= null;
        UploadState state =new UploadState();
        try {
            resourcePath = upload2File(file.getBytes(),fileName,type);
            state.setState("SUCCESS");
            state.setUrl(resourcePath);
        } catch (IOException e) {
            state.setState("FAIL");
        }
        return state;
    }

    private String getRootPath() throws UnsupportedEncodingException {
        String rootPath = FileUtil.getParents(ClassUtils.getDefaultClassLoader().getResource("").getPath(),3);
        if(rootPath.substring(0,5).equals("file:"))     //兼容Linux
            rootPath=rootPath.substring(5);

        return URLDecoder.decode(rootPath,"UTF-8");

    }
}
