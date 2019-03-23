package fishmaple.Service;


import fishmaple.DAO.AnonymousMapper;
import fishmaple.DAO.UserMapper;
import fishmaple.DTO.Anonymous;
import fishmaple.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.SERIALIZABLE,timeout=36000,rollbackFor=Exception.class)
public class AnonymousServiceImpl implements AnonymousService {
    @Autowired
    AnonymousMapper anonymousMapper;

    @Override
    public String getName(String sessionId) {
        String name=anonymousMapper.getNameIfExist(sessionId);
        anonymousMapper.setSessionIdByName(name,sessionId,System.currentTimeMillis()/1000);
        if(name==null){
           Anonymous anonymous =anonymousMapper.getRand();
           name=anonymous.getName();
           anonymousMapper.setSessionIdByName(name,sessionId,System.currentTimeMillis()/1000);
        }
        return name;
    }

    @Override
    public String changeName(String sessionId) {
        String name=getName(sessionId);
        anonymousMapper.setSessionIdByName(name,"0",0);
        Anonymous anonymous =anonymousMapper.getRand();
        anonymousMapper.setSessionIdByName(anonymous.getName(),sessionId,System.currentTimeMillis()/1000);
        return anonymous.getName();
    }
}
