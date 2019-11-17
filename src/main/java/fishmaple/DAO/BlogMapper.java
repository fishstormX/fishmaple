package fishmaple.DAO;

import fishmaple.DTO.Blog;
import fishmaple.conf.RedisCache4BlogConf;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashSet;
import java.util.List;

@CacheNamespace(size=5,implementation = RedisCache4BlogConf.class)
public interface BlogMapper{

    @Delete("delete from blog where id=#{bid}")
    void deleteOneBlog(String bid);

    @Insert("insert into blog_recycle(`id`, `content`,`title`,`timeline`,`author`,`tags`,`anchors`) " +
            "values (#{id},#{content},#{title},#{timeline},#{author},#{tags},#{anchors})")
    void addRecycle(@Param("id") String id, @Param("content") String content,
            @Param("title")String title, @Param("timeline")Long timeline,
            @Param("author")String author,@Param("anchors")String anchors,
                    @Param("tags")String tags);

    @Insert("insert into blog(`id`, `content`,`title`,`timeline`,`author`,`anchors`,`useDictionary`,`cover`," +
            "`isOriginal`,`todo`,`topic_id`) " +
            "values (#{id},#{content},#{title},#{timeline},#{author},#{anchors},#{useDictionary},#{cover}," +
            "#{isOriginal},#{todo},#{topicId})")
    void save(@Param("id") String id, @Param("content") String content,
                     @Param("title")String title, @Param("timeline")Long timeline,
                     @Param("author")String Aid,@Param("anchors")String anchors,
              @Param("useDictionary")int useDictionary,@Param("cover")String cover,
              @Param("isOriginal")Integer isOriginal,@Param("todo")Integer todo,
              @Param("topicId")Integer topicId);

    @Update("update blog SET `title`=#{title},`content`=#{content}, " +
            "timeline=#{timeline},author=#{author},anchors=#{anchors}, " +
            "`useDictionary`=#{useDictionary},cover=#{cover},isOriginal=#{isOriginal}," +
            "todo=#{todo},topic_id=#{topicId} " +
            "WHERE `id`=#{id}")
    void updateOne(@Param("id") String id, @Param("content") String content,
              @Param("title")String title, @Param("timeline")Long timeline,
              @Param("author")String author,@Param("anchors")String anchors,
              @Param("useDictionary")int useDictionary,@Param("cover")String cover,
                   @Param("isOriginal")Integer isOriginal,@Param("todo")Integer todo,
                   @Param("topicId")Integer topicId);


    @Insert("insert into blog_tag(`id`, `blog_id`,`tag`) " +
            "values (#{id},#{bid},#{tag})")
    void saveBTags(@Param("id") String id, @Param("bid") String bid,
                     @Param("tag")String tag);

    @Select("select COUNT(*) from blog_bak WHERE author_id = #{uid} AND blog_id IS NULL")
    Integer getBakCount(@Param("uid")String uid);
    @Select("select `id` from blog_bak WHERE author_id = #{uid} AND blog_id IS NULL ORDER BY timeline LIMIT 1")
    Integer getLastBak(@Param("uid")String uid);
    @Select("Delete from blog_bak WHERE `id` = #{id}")
    Integer deleteLastBak(@Param("id")Integer id);


    @Select("select COUNT(*) from blog_bak WHERE author_id = #{uid} AND blog_id = #{bid}")
    Integer getNBakCount(@Param("bid") String bid, @Param("uid") String uid);
    @Update("update blog_bak SET content=#{content},timeline=#{timestamp} WHERE author_id = #{uid} AND blog_id = #{bid}")
    Integer updateBak(@Param("bid") String bid, @Param("uid") String uid,
                     @Param("content")String content,@Param("timestamp")Long timestamp);


    @Insert("insert into blog_bak(`author_id`,`content`,`timeline`) " +
            "values (#{uid},#{content},#{timestamp})")
    void saveBak(@Param("uid") String uid,
                 @Param("content")String content,@Param("timestamp")Long timestamp);
    @Insert("insert into blog_bak(`blog_id`,`author_id`,`content`,`timeline`) " +
            "values (#{bid},#{uid},#{content},#{timestamp})")
    void saveNBak(@Param("bid") String bid, @Param("uid") String uid,
                   @Param("content")String content,@Param("timestamp")Long timestamp);

    @Select("select * from blog where id = #{id}")
    @Results({@Result(id=true,property="id",column="id"),
            @Result(property = "tags",javaType = List.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.BlogMapper.getBlogTagsByBid"))})
    Blog getById(@Param("id") String id);

    @Select("select id,title,timeline,author,content,cover from blog")
    @Results({@Result(id=true,property="id",column="id"),
            @Result(property = "tags",javaType = List.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.BlogMapper.getBlogTagsByBid"))})
    List<Blog> getAll();


    @Select("select blog.id AS id, `content`,`title`,`timeline`,`author`,`anchors`,`useDictionary`,`cover`," +
            "`isOriginal`,`todo`,`topic_id`,priority,user.avatar AS avatar from blog JOIN user ON blog.author=user.name " +
            "ORDER BY  priority DESC" +
            ",timeline DESC LIMIT #{start},#{count}")
    @Results({@Result(id=true,property="id",column="id"),
            @Result(property = "tags",javaType = List.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.BlogMapper.getBlogTagsByBid"))})
    List<Blog> getByPage(@Param("start")int start, @Param("count")int count);
    @Select("select `id`,title,timeline,author,isOriginal,todo from blog ORDER BY timeline DESC")
    List<Blog> fastGetAll();

    @Select("select `id`,title,timeline,author,isOriginal,todo from blog WHERE topic_id = #{topicId} ORDER BY timeline DESC")
    List<Blog> fastGetByTopicId(@Param("topicId")Integer topicId);

    @Select("select blog.id AS id, `content`,`title`,`timeline`,`author`,`anchors`,`useDictionary`,`cover`,`isOriginal`,`todo`,priority,user.avatar AS avatar from blog_tag " +
            "left join blog on blog.id=blog_tag.blog_id JOIN user ON blog.author=user.name " +
            "where tag = #{tag} ORDER BY timeline DESC LIMIT #{start},#{count}")
    @Results({@Result(id=true,property="id",column="id"),
            @Result(property = "tags",javaType = List.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.BlogMapper.getBlogTagsByBid"))})
    List<Blog> getByPageAndTag(@Param("start")int start, @Param("count")int count,
                               @Param("tag")String tag);

    @Select("select tag from blog_tag where blog_id=#{bid}")
    List<String> getBlogTagsByBid(String bid);

    @Delete("delete from blog_tag where blog_id=#{bid}")
    void deleteBlogTags(String bid);

    @Select("select count(id) AS count from blog")
    int getCount();
    @Select("select count(blog_id) AS count from blog_tag where tag=#{tag}")
    int getCountByTag(String tag);

    @Select("select blog.id,blog.anchors,blog.content,blog.timeline,blog.title,cover, " +
            "IFNULL(GROUP_CONCAT(tag),'') AS tagTemp " +
            "from blog LEFT JOIN blog_tag ON blog.id=blog_tag.blog_id " +
            "where blog.content like CONCAT('%',#{content},'%') " +
            "OR blog.title like CONCAT('%',#{content},'%') "+
            "OR blog_tag.tag LIKE CONCAT('%',#{content},'%') " +
            "GROUP BY blog.id ORDER BY timeline DESC")
    LinkedHashSet<Blog> searchBlog(String content);


}
