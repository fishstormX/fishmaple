package fishmaple.DAO;

import fishmaple.DTO.Can;
import fishmaple.DTO.FriendLink;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FriendLinksMapper {
    @Select("select * from friend_link")
    List<FriendLink> getAll();
}
