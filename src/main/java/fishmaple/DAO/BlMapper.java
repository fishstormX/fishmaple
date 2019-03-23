package fishmaple.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BlMapper {
    @Insert("insert into bluser(`mid`, `name`,`sex`,`fans`,`face`,`rank`) " +
            "values (#{mid},#{name},#{sex},#{fans},#{face},#{rank})")
    public void save(@Param("mid") Long mid, @Param("sex") String sex,
                     @Param("fans")String fans, @Param("name") String name,
                     @Param("face")String face, @Param("rank")String rank);
    @Select("SELECT mid from bluser ORDER BY mid DESC limit 1")
    public Long getCount();
    @Update("UPDATE bluser SET handle = #{index} WHERE mid = #{mid}")
    public Integer updateHandle(@Param("index") Integer index,@Param("mid") Long mid);


}
