package fishmaple.Service;

import fishmaple.DAO.ToolMapper;
import fishmaple.shiro.ShiroService;
import fishmaple.utils.EncoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolServiceImpl implements ToolService{
    @Autowired
    ToolMapper toolDAO;
    @Autowired
    ShiroService shiroService;
    private Logger log= LoggerFactory.getLogger(ToolService.class);

    @Override
    public void addTool(String name, String describe, List<String> resourceIdList) {
        long timenow=System.currentTimeMillis()/1000;
        String tid= EncoderUtil.getUUID(0);
        String author="匿名";
        if(shiroService.getCurrentUser()!=null){
            author=shiroService.getCurrentUser().getName();
        }
        toolDAO.save(name,describe,timenow,tid,author);

        for(String resourceId:resourceIdList){
            toolDAO.saveMap(resourceId,tid);
        }

    }
}
