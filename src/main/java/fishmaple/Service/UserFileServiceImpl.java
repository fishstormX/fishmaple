package fishmaple.Service;

import fishmaple.Objects.FileObject;
import fishmaple.shiro.ShiroService;
import fishmaple.utils.FileUtil;
import fishmaple.utils.PublicConst;
import fishmaple.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserFileServiceImpl {
    @Autowired
    ShiroService shiroService;
    //权限管理
    private int getDocumentAuth(){
        if(shiroService.isUserAuthAble("document_upload")){
            return 2;
        }else if(shiroService.isUserAuthAble("document_download")){
            return 1;
        }
        return 0;
    }
    //获取用户文件托管资源路径
    public String getUserFilepath(String uid){
        if(getDocumentAuth()>0){
            if(SystemUtil.getOs()==PublicConst.LINUX){
                return PublicConst.ROOT_USER_LINUX_FILE_PATH+uid+"/";
            }else{
                return PublicConst.ROOT_USER_WINDOWS_FILE_PATH+uid+"/";
            }
        }
        return null;
    }
    //上传一个文件
    public Map<String,Object> uploadFile(byte[] file,String fileName,String uid,String space){
        Map<String,Object> returnMap=new HashMap<>();
        if(shiroService.getCurrentUser().getId().equals(uid)&&getDocumentAuth()>1){
            FileUtil.uploadFile(file,fileName,getUserFilepath(uid)+space);
            returnMap.put("success","true");
        }else{
            returnMap.put("success","false");
            returnMap.put("success","上传权限不足（暂时只支持个人文件上传）");
        }
        return returnMap;
    }
    //删除一个文件
    public Map<String,Object> deleteFile(String fileName,String uid,String space){
        Map<String,Object> returnMap=new HashMap<>();
        if(shiroService.getCurrentUser().getId().equals(uid)&&getDocumentAuth()>1){
            String status=FileUtil.deleteFiles(getUserFilepath(uid)+space+fileName);
            if("ok".equals(status)){
                returnMap.put("success","true");
            }else{
                returnMap.put("success","false");
                returnMap.put("success",status+",请刷新重试！");
            }
        }else{
            returnMap.put("success","false");
            returnMap.put("success","删除权限不足（暂时只支持个人文件上传）");
        }
        return returnMap;
    }
    //相对目录文件列表
    public List<FileObject> getPathFile(String uid,String space){
        if(getDocumentAuth()>0) {
            return FileUtil.getFileList(getUserFilepath(uid) + space);
        }else{return null;}
    }
    //获取目录大小
    public Long getFileSize(String uid,String space){
        if(getDocumentAuth()>0) {
            return FileUtil.getFileSize(getUserFilepath(uid) + space);
        }else{
            return null;
        }
    }
}
