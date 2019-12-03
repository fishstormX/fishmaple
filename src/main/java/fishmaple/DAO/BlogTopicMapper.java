package fishmaple.DAO;

import fishmaple.DTO.Anonymous;
import fishmaple.DTO.BlogTopic;
import fishmaple.conf.RedisCache4BlogConf;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@CacheNamespace(size=5,implementation = RedisCache4BlogConf.class)
public interface BlogTopicMapper {
    public List<BlogTopic> getTopicsByUserId(@Param("userId")String userId);
    public List<BlogTopic> getAllTopics();
    public String getTopicName(@Param("id")Integer topicId);
    public BlogTopic getTopicById(@Param("id")Integer topicId);
    public List<BlogTopic> getSubTopicById(@Param("id")Integer topicId);
    public List<BlogTopic> getTopicsByUserIdAndTopicId(@Param("userId")String userId,@Param("topicId")Integer topicId);
    public Integer addTopic(BlogTopic blogTopic);
    public Integer verifyTopic(BlogTopic blogTopic);
}
