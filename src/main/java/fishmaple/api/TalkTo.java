package fishmaple.api;

import fishmaple.Objects.FileObject;
import fishmaple.utils.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class TalkTo {

    @PostMapping("sendTalk")
    public String sendTalk(@RequestBody Map<String,String> map) throws GeneralSecurityException {
        if(map.get("content").equals("")||map.get("email").equals("")||map.get("name").equals("")){
            return "请完善必填项";
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(map.get("email"));
        boolean isMatched = matcher.matches();
        if(!isMatched){
            return "邮箱地址格式错误";
        } else{
            try {
                SendEmail.send("新留言(#^.^#)-鱼鱼的博客", "收到新留言<br>" + map.get("content") + "<br>" +
                                map.get("email") + "<br>" + map.get("name") + "<br>" + map.get("website") + "<br>",
                        "admin@fishmaple.cn", SendEmail.REDIRECT);

                String content = "你好 ,我已经收到你在我网站的留言噜，我会及时处理并给予反馈，敬请关注邮件。<br>　　感谢您对本博客的关注<br>　　祝：生活愉快<br><br>" +
                        "                 <a href=\"https://www.fishmaple.cn\"><img src=\"https://www.fishmaple.cn/pics/logo_m_m.png\" class=\"logo middle_pic\"> <img src=\"https://www.fishmaple.cn/pics/logo-fish-small.png\" class=\"logo middle_fish\"></a>"+
                        "                 <br><br><br><span style='float:right'>from　</strong>鱼鱼的博客</strong></span>" +
                        "                  <br><br><span style='float:right;color:darkgrey'>Copyright ©  fishmaple. </span>";

                SendEmail.send("留言已揽收(#^.^#)-鱼鱼的博客", content, map.get("email"), SendEmail.REDIRECT);
            }catch(Exception e){
                    e.printStackTrace();
            }finally{
                return "博主已经收到留言，请等待反馈并检查你的邮箱，如果没有收到邮件请稍后重试";
            }
        }
    }
}
