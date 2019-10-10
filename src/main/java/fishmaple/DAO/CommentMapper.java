package fishmaple.DAO;

import fishmaple.DTO.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommentMapper {
    @Insert("Insert comment(related_id,content,creator,create_time,type,root_id) values(#{relatedId},#{content}，" +
            "#{creator},#{createTime},#{type},#{rootId})")
    public void addComment(Comment comment);
    @Select("Select * From comment where related_id=#{relatedId} and type = #{type} orderBy create_time" +
            "limit #{start},#{count}")
    public List<Comment> getCommentsByRelatedId(@Param("relatedId") String relatedId, @Param("type") int type,
                                                @Param("start") int start, @Param("count") int count);
}
