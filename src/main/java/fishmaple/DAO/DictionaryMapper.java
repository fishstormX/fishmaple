package fishmaple.DAO;

import fishmaple.DTO.Dictionary;
import fishmaple.Objects.DictionaryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * @author 鱼鱼
 * 词条目录表 dictionary
 * */
public interface DictionaryMapper {
    @Select("select * from dictionary LIMIT 0,60")
    List<Dictionary> getDictionary();

    @Select("select * from dictionary")
    List<Dictionary> getAll();

    @Select("select `key` as label,value from dictionary LIMIT 0,60")
    List<DictionaryObject> getDictionaryObjects();

    @Select("select * from dictionary WHERE `key`=#{key}")
    Dictionary getDictionaryByKey(@Param("key") String key);

    @Insert("insert into dictionary(`key`, `value`,`timeline`,`author`) " +
            "values (#{key},#{value},#{timeline},#{author})")
    void setDictionary(@Param("key") String key,@Param("value") String value,
                             @Param("author")String author,@Param("timeline")Long timeline
                             );
}
