package fishmaple.DAO;


import fishmaple.DTO.Can;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CanMapper {

    @Select("select * from can where id=#{id}")
    Can getById(String id);
}
