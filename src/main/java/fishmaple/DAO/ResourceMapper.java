package fishmaple.DAO;

import fishmaple.DTO.Resource;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
/**
 * @author 鱼鱼
 * 资源表 resource
 * */
public interface ResourceMapper {

    @Insert("insert into resource(`id`, `name`,`describe`,`readonly`,`path`,`dateline`,`downloadCode`,`suffix`) " +
            "values (#{id},#{name},#{describe},#{readonly},#{path},#{dateline},#{downloadCode},#{suffix})")
    public void save(@Param("id") String id, @Param("describe") String sdescribe,
                     @Param("dateline")long dateline,@Param("readonly") int readonly,
                     @Param("name")String name,@Param("path")String path,
                     @Param("downloadCode")String downloadCode,@Param("suffix")String suffix );

    @Select("select * from resource where id = #{id}")
    public Resource getById(@Param("id") String id);

}
