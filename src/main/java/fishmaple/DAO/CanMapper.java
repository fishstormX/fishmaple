package fishmaple.DAO;


import fishmaple.DTO.Can;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CanMapper {

    @Select("select * from can where id=#{id}")
    Can getById(String id);
    @Insert("Insert can(id,title,content) values(#{id},#{title}，#{content})")
    void insertOne(@Param("id")String id,@Param("title")String title,@Param("content")String content);

}
