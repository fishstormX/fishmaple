package fishmaple.DAO;

import fishmaple.DTO.Anonymous;
import fishmaple.Objects.ToolObject;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @author 鱼鱼
 * 匿名名字表 anonymous
 * */
public interface AnonymousMapper {
    @Select("select * from anonymous WHERE session='0' order by rand() limit 1")
    public Anonymous getRand();
    @Select("select `name` from anonymous WHERE session=#{sessionId}")
    public String getNameIfExist(String sessionId);
    @Update("update anonymous SET session=#{session},timeline=#{time} WHERE name=#{name}")
    public void setSessionIdByName(@Param("name")String name,@Param("session")String sessionId,@Param("time") long timeLine);
    @Update("update anonymous SET session='0',timeline=0 WHERE timeline<#{time}-21600")
    public void clearOutData(long time);
}
