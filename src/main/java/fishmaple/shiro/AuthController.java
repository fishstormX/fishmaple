package fishmaple.shiro;

import fishmaple.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    ShiroService shiroService;
    @RequestMapping("/who")
    public Map<String,String> getCurrentUser(){
        Map<String,String> user=new HashMap<>();
        if(null!=shiroService.getCurrentUser()){
            user.put("name",shiroService.getCurrentUser().getName());
            user.put("id",shiroService.getCurrentUser().getId());
            user.put("email",shiroService.getCurrentUser().getEmail());
        }else{
            user.put("name","");
            user.put("email","");
        }
        return user;
    }
}
