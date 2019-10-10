package fishmaple.api;

import fishmaple.DAO.CommentMapper;
import fishmaple.DTO.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    @PutMapping("")
    public String addComment(@RequestBody HashMap<String,String> map){
        Comment comment = new Comment();
        comment.setContent(map.get("content"));
        comment.setCreateTime(System.currentTimeMillis()/1000);
        comment.setRootId(Long.parseLong(map.get("rId")));
        comment.setCreator(map.get("creator"));
        comment.setEmail(map.get("email"));
        comment.setRelatedId(map.get("relatedId"));
        commentMapper.addComment(comment);
        return "okay";
    }

    @GetMapping("/{type}/{relatedId}")
    public List<Comment> getComment(@PathVariable("type")int type, @PathVariable("relatedId")String relatedId,
                                    @RequestParam("pageNum")int page, @RequestParam("pageSize")int pageSize){
        return commentMapper.getCommentsByRelatedId(relatedId,type,page,pageSize);
    }

}
