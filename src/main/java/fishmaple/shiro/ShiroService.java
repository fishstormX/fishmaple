package fishmaple.shiro;

import fishmaple.DTO.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class ShiroService {

    public User getCurrentUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
    public Subject getCurrentSubject(){
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    public boolean isLogin(){
        if(getCurrentUser()!=null)
            return true;
        else
            return false;
    }
    //查看当前用户是否具有某个权限
    public boolean isUserAuthAble(String able){
        String[] auths=getUserAuth().split(",");
        for(String auth:auths){
            if(able.equals(auth)){
                return true;
            }
        }
        return false;
    }

    public String getUserAuth(){
        if(getCurrentUser()!=null)
            return getCurrentUser().getAuth()==null?"-1":getCurrentUser().getAuth();
        else
            return "-1";
    }
    public String getUserName(){
        if(getCurrentUser()!=null)
            return getCurrentUser().getName();
        else
            return "0";
    }
}
