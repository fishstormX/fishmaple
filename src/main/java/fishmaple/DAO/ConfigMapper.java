package fishmaple.DAO;

import fishmaple.DTO.Can;
import org.apache.ibatis.annotations.Select;

public interface ConfigMapper {
    @Select("select `value` from `config` where `key`=#{key}")
    String getValue(String key);
}
