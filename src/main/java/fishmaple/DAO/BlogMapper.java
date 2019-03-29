package fishmaple.DAO;

import fishmaple.DTO.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;

@Component
public interface BlogMapper {

    @Delete("delete from blog where id=#{bid}")
    void deleteOneBlog(String bid);

    @Insert("insert into blog_recycle(`id`, `content`,`title`,`timeline`,`author`,`tags`,`anchors`) " +
            "values (#{id},#{content},#{title},#{timeline},#{author},#{tags},#{anchors})")
    void addRecycle(@Param("id") String id, @Param("content") String content,
                    @Param("title") String title, @Param("timeline") Long timeline,
                    @Param("author") String author, @Param("anchors") String anchors,
                    @Param("tags") String tags);

    @Insert("insert into blog(`id`, `content`,`title`,`timeline`,`author`,`anchors`,`useDictionary`,`cover`," +
            "`isOriginal`,`todo`) " +
            "values (#{id},#{content},#{title},#{timeline},#{author},#{anchors},#{useDictionary},#{cover}," +
            "#{isOriginal},#{todo})")
    void save(@Param("id") String id, @Param("content") String content,
              @Param("title") String title, @Param("timeline") Long timeline,
              @Param("author") String Aid, @Param("anchors") String anchors,
              @Param("useDictionary") int useDictionary, @Param("cover") String cover,
              @Param("isOriginal") Integer isOriginal, @Param("todo") Integer todo);

    @Update("update blog SET `title`=#{title},`content`=#{content}, " +
            "timeline=#{timeline},author=#{author},anchors=#{anchors}, " +
            "`useDictionary`=#{useDictionary},cover=#{cover},isOriginal=#{isOriginal}," +
            "todo=#{todo} " +
            "WHERE `id`=#{id}")
    void updateOne(@Param("id") String id, @Param("content") String content,
                   @Param("title") String title, @Param("timeline") Long timeline,
                   @Param("author") String author, @Param("anchors") String anchors,
                   @Param("useDictionary") int useDictionary, @Param("cover") String cover,
                   @Param("isOriginal") Integer isOriginal, @Param("todo") Integer todo);


    @Insert("insert into blog_tag(`id`, `blog_id`,`tag`) " +
            "values (#{id},#{bid},#{tag})")
    void saveBTags(@Param("id") String id, @Param("bid") String bid,
                   @Param("tag") String tag);

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

    @Select("select id,title,timeline,author,content,priority,useDictionary,cover,isOriginal,todo from blog " +
            "ORDER BY  priority DESC" +
            ",timeline DESC LIMIT #{start},#{count}")
    @Results({@Result(id=true,property="id",column="id"),
            @Result(property = "tags",javaType = List.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.BlogMapper.getBlogTagsByBid"))})
    List<Blog> getByPage(@Param("start") int start, @Param("count") int count);
    @Select("select `id`,title,timeline,author,isOriginal,todo from blog")
    List<Blog> fastGetAll();

    @Select("select blog.id,title,timeline,author,content,useDictionary,cover,isOriginal,todo from blog_tag " +
            "left join blog on blog.id=blog_tag.blog_id " +
            "where tag = #{tag} ORDER BY timeline DESC LIMIT #{start},#{count}")
    @Results({@Result(id=true,property="id",column="id"),
            @Result(property = "tags",javaType = List.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.BlogMapper.getBlogTagsByBid"))})
    List<Blog> getByPageAndTag(@Param("start") int start, @Param("count") int count,
                               @Param("tag") String tag);

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
