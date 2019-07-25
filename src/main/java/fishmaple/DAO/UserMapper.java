package fishmaple.DAO;

import fishmaple.DTO.Role;
import fishmaple.DTO.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;
/**
 * @author 鱼鱼
 * 用户表 user
 * */
public interface UserMapper {
    @Select("SELECT `user`.id,`user`.name,`user`.pswd,`user`.registertime,`user`.auth " +
            "FROM user " +
            "WHERE `user`.name=#{name}")
    @Results({ @Result(id=true,property="id",column="id"),
            @Result(property = "roles",javaType = Set.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.UserMapper.getRolesByUserId"))})
    //一对多查询
    public User selectUserByName(String name);

    @Select("Select name from role join user_role on " +
            "user_role.role_id=role.id where user_role.user_id=#{id}")
    public Set<Role> getRolesByUserId(String id);

    @Select("SELECT `user`.id,`user`.name,`user`.pswd,`user`.registertime,`user`.auth " +
            "FROM user " +
            "WHERE `user`.id=#{id}")
    @Results({ @Result(id=true,property="id",column="id"),
            @Result(property = "roles",javaType = Set.class,column ="id",
                    many = @Many(select = "fishmaple.DAO.UserMapper.getRolesByUserId"))})
    public  User selectUserById(String id);

    @Select("Select COUNT(*) from user WHERE `user`.name =#{name} OR  `user`.email =#{name}")
    public int selectNameCount(String name);
    @Select("Select name from user WHERE  `user`.email =#{email}")
    public String getNameByEmail(String email);

    @Insert("insert into user(`id`, `name`,`email`,`registerTime`,`pswd`) " +
            "values (#{id},#{name},#{email},#{registerTime},#{pswd})")
    void setUser(@Param("id")String id,@Param("name")String name,@Param("email")String email,
                 @Param("registerTime")Long timeline,@Param("pswd")String pswd);
}
