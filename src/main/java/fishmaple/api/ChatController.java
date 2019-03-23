package fishmaple.api;

import fishmaple.Service.AnonymousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
/**
 * @author 鱼鱼
 * 匿名聊天的相关接口
 * */
@RestController
public class ChatController {
   @Autowired
    private AnonymousService anonymousService;

    @RequestMapping("/api/getAnonymous")
    public String getAnonymous(HttpSession session){
        return anonymousService.getName(session.getId());
    }
    @RequestMapping("/api/changeAnonymous")
    public String reetAnonymous(HttpSession session){
        return anonymousService.changeName(session.getId());
    }

}
