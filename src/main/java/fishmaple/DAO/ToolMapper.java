package fishmaple.DAO;

import fishmaple.DTO.Blog;
import fishmaple.DTO.Tool;
import fishmaple.Objects.ToolObject;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashSet;
import java.util.List;
/**
 * @author 鱼鱼
 * 工具表 tool
 * */
public interface ToolMapper {
    @Insert("insert into tool(`id`, `name`,`describe`,`createDate`,`author`) " +
            "values (#{id},#{name},#{describe},#{createDate},#{author})")
    public void save(@Param("name") String name,@Param("describe") String describe,
                     @Param("createDate") long createDate,@Param("id") String id,
                     @Param("author")String author);

    @Insert("insert into tool_resource(`toolId`,`resourceId`,`id`) " +
            "values (#{toolId},#{resourceId},#{id})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '') as id from dual")
    public void saveMap(@Param("resourceId")String resourceId,
                        @Param("toolId") String toolId);


    @Select("select `name`,`describe`,`id`,`author`,`createDate` from tool " +
            "order by createdate")
    public List<Tool> getAllTools();

    @Select("Select t.`name` AS `name`,t.`author`,t.`describe`,GROUP_CONCAT(tr.resourceId) " +
            "AS resourceIdGroup " +
            "FROM tool t left join tool_resource tr ON t.`id`=tr.toolId " +
            "GROUP BY t.id ")
    public List<ToolObject> getAllToolsResources();

    @Select("select resourceId from tool_resource where toolId=#{id}")
    public List<String> getToolResources(String id);

    @Select("select DISTINCT tool.id,tool.name,tool.describe,tool.createDate " +
            "from tool JOIN tool_resource ON tool.id=tool_resource.toolId " +
            "JOIN resource ON tool_resource.resourceId=resource.id " +
            "where tool.name like CONCAT('%',#{content},'%') "+
            "OR tool.describe like CONCAT('%',#{content},'%') "+
            "OR tool.author like CONCAT('%',#{content},'%') "+
            "OR resource.describe LIKE CONCAT('%',#{content},'%') " +
            "OR resource.suffix LIKE CONCAT('%',#{content},'%') " +
            "OR resource.name LIKE CONCAT('%',#{content},'%') ")
    LinkedHashSet<Tool> searchTool(String content);
}
