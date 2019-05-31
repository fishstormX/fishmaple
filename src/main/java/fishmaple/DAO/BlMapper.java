package fishmaple.DAO;

import fishmaple.thirdPart.bilibiliWebWorm.BlUserObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BlMapper {
    @Insert("insert into bluser(`mid`, `name`,`sex`,`fans`,`face`,`rank`) " +
            "values (#{mid},#{name},#{sex},#{fans},#{face},#{rank})")
    public void save(@Param("mid") Long mid, @Param("sex") String sex,
                     @Param("fans")String fans, @Param("name") String name,
                     @Param("face")String face, @Param("rank")String rank);
    @Select("SELECT mid from bluser ORDER BY mid DESC limit 1")
    public Long getCount();

    @Select("SELECT * from bluser limit #{index},#{count}")
    public List<BlUserObject> getBlUsers(@Param("index") Integer index, @Param("count") Integer count);
    @Update("UPDATE bluser SET handle = #{index} WHERE mid = #{mid}")
    public Integer updateHandle(@Param("index") Integer index,@Param("mid") Long mid);


}
