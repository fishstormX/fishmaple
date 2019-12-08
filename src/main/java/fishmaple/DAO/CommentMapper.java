package fishmaple.DAO;

import fishmaple.DTO.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommentMapper {
    @Insert("<script>Insert comment(email,related_id,content,creator,create_time,type,hide_email<if test='rootId!=null'>,root_id</if>) " +
            "values(#{email},#{relatedId},#{content}," +
            "#{creator},#{createTime},#{type},#{hideEmail}<if test='rootId!=null'>,#{rootId}</if>)</script>")
    public void addComment(Comment comment);
    @Select("<script>Select * From comment where related_id=#{relatedId} and type = #{type} " +
            "<if test='ordered==2'>order by id desc</if> limit #{start},#{count}</script>")
    public List<Comment> getCommentsByRelatedId(@Param("relatedId") String relatedId, @Param("type") int type,
                                                @Param("start") int start, @Param("count") int count,@Param("ordered")int ordered);

    @Select("Select COUNT(*) From comment where related_id=#{relatedId} and type = #{type} ")
    public int getCommentsNumByRelatedId(@Param("relatedId") String relatedId, @Param("type") int type);
}
