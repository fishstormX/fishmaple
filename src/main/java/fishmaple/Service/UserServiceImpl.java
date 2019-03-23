package fishmaple.Service;


import fishmaple.DAO.UserMapper;
import fishmaple.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByName(String name) {
        return userMapper.selectUserByName(name);
    }
}
