package fishmaple.api;

import fishmaple.DAO.CommentMapper;
import fishmaple.DTO.Comment;
import fishmaple.Objects.CommentObject;
import fishmaple.Objects.PageResult;
import fishmaple.Service.CommentService;
import fishmaple.utils.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentService commentService;
    @PutMapping("")
    public String addComment(@RequestBody HashMap<String,String> map){
        String email=map.get("email");
        if(null==email){
            return "请填写邮箱";
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        boolean isMatched = matcher.matches();
        if(!isMatched){
            return "邮箱地址格式错误";
        }
        String text=map.get("text");
        if(null==text||text.equals("")){
            return "请填写评论";
        }else if(text.length()>500){
            return "评论字数过多";
        }
        Comment comment = new Comment();
        comment.setContent(text);
        comment.setCreateTime(System.currentTimeMillis()/1000);
        if(null!=map.get("rId")&&map.get("rId").isEmpty()){
            comment.setRootId(Long.parseLong(map.get("rId")));
        }
        comment.setCreator(map.get("name"));
        comment.setEmail(email);
        comment.setType(1);
        comment.setRelatedId(map.get("relatedId"));
        commentMapper.addComment(comment);
        String content = "感谢评论，我会及时处理并给予反馈，敬请关注邮件。<br>　　感谢您对本博客的关注<br>　　祝：生活愉快<br><br>" +
                "                 <a href=\"https://www.fishmaple.cn\"><img src=\"https://www.fishmaple.cn/pics/logo_m_m.png\" class=\"logo middle_pic\"> <img src=\"https://www.fishmaple.cn/pics/logo-fish-small.png\" class=\"logo middle_fish\"></a>"+
                "                 <br><br><br><span style='float:right'>from　</strong>鱼鱼的小站</strong></span>" +
                "                  <br><br><span style='float:right;color:darkgrey'>Copyright ©  fishmaple. </span>";

        SendEmail.send("评论已揽收(#^.^#)-鱼鱼的小站", content, email, SendEmail.REDIRECT);
        return "success";
    }

    @GetMapping("/{type}/{relatedId}")
    public PageResult<CommentObject> getComment(@PathVariable("type")int type, @PathVariable("relatedId")String relatedId,
                                                @RequestParam("pn")int page, @RequestParam("pz")int pageSize,
                                                @RequestParam("od")int od){

        return commentService.getCommentsByRelatedId(type,relatedId,page*pageSize,pageSize,od);
    }

}
