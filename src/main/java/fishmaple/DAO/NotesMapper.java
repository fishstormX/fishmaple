package fishmaple.DAO;

import fishmaple.DTO.Notes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NotesMapper {
    @Select("select * from notes where uid = #{uid}")
    List<Notes> getNotesByUid(String uid);
    @Insert("insert into notes(`id`, `uid`,`title`,`content`,`type`,`timestamp`,`param0`,`param1`) " +
            "values (#{id},#{uid},#{title},#{content},#{type},#{timestamp},#{param0},#{param1})")
    void setNotes(Notes notes);
}
