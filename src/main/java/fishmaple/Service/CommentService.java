package fishmaple.Service;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import fishmaple.DAO.CommentMapper;
import fishmaple.DTO.Comment;
import fishmaple.Objects.CommentObject;
import fishmaple.Objects.PageResult;
import fishmaple.utils.QrCodeUtil;
import fishmaple.utils.TimeDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    public PageResult<CommentObject> getCommentsByRelatedId(int type, String relatedId, int page, int pageSize,int od)  {
        List<Comment> list = commentMapper.getCommentsByRelatedId(relatedId,type,page*pageSize,pageSize,od);
        List<CommentObject> newList = new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        PageResult pageResult = new PageResult<CommentObject>();
        pageResult.setTotalNum(commentMapper.getCommentsNumByRelatedId(relatedId,type));
        List<Integer> list2=new ArrayList<Integer>(22);
        for(int i=1;i<23;i++){
            list2.add(i);
        }
        Collections.shuffle(list2);
        int index=pageResult.getTotalNum();
        boolean flag=false;
        if(od==1){
            index=page*pageSize+1;
            flag=true;
        }
        for(Comment comment:list){
            CommentObject commentObject=new CommentObject();

            if(flag){
                commentObject.setIndex(index++);
            }else{
                commentObject.setIndex(index--);
            }
            BeanUtils.copyProperties(comment,commentObject);
            if(comment.isHideEmail()){
                commentObject.setEmail("");
            }
            if(map.containsKey(comment.getEmail())){
                commentObject.setRandAvatar(map.get(comment.getEmail()));
            }else{
                String ava="/avatar/comment/ran_avatar ("+list2.get(map.size())+").jpeg";
                map.put(comment.getEmail(),ava);
                commentObject.setRandAvatar(ava);
            }
            commentObject.setCreateTime(TimeDate.timestamp2time(comment.getCreateTime()*1000,0));
            newList.add(commentObject);
        }

        pageResult.setList(newList);

        return pageResult;
    }
}
