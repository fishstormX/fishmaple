package fishmaple.DAO;

import org.apache.ibatis.annotations.Param;

public interface LoginLogMapper {
    public void insertLog(@Param("id") String id,@Param("username") String username,@Param("time")String  time,@Param("browser")String browser,
                          @Param("os")String os,@Param("action") String action,@Param("ip")String ip);
}
