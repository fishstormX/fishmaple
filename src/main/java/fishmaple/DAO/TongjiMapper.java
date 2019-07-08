package fishmaple.DAO;

import fishmaple.DTO.Tongji;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TongjiMapper {

    public String getIndex();

    public void add(Tongji tongji);
    public void update(Tongji tongji);
}
