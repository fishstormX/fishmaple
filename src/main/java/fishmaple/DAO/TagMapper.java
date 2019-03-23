package fishmaple.DAO;


import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * @author 鱼鱼
 * 全局标签表 tag
 * */
public interface TagMapper{
    @Select("select name from tag where type = #{id}")
    List<String> getTagsByType(int type);
    @Select("select type  from tag where name = #{name}")
    Integer getTagTypeByName(String name);
}
