package fishmaple.Service;


import fishmaple.DTO.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


public interface UserService {
    User findUserByName(String name);


}
