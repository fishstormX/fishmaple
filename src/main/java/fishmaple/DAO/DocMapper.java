package fishmaple.DAO;

import fishmaple.DTO.DocMember;
import fishmaple.DTO.DocRepository;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DocMapper {
    @Select("SELECT `id`,`name`,`describe` FROM doc_rep")
    @Results({@Result(property = "docMembers",javaType = List.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.DocMapper.getMemberByRid"))})
    public List<DocRepository> getRespositories();

    @Select("SELECT `id`,`name`,`en_url`,`describe`,`rid`,`zh_url`,`img_url`,`img_style` FROM doc_mem WHERE rid=#{rid}")
    public List<DocMember> getMemberByRid(@Param("rid") String rid);
}
