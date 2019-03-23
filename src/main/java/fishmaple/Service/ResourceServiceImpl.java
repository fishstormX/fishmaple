package fishmaple.Service;

import fishmaple.DAO.ResourceMapper;
import fishmaple.DAO.ToolMapper;
import fishmaple.DTO.Resource;
import fishmaple.Objects.ResourceObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ToolMapper toolMapper;

    @Override
    public List<ResourceObject> getResourcesByToolId(String tid) {
        List<String> ResourceIds=toolMapper.getToolResources(tid);
        List<ResourceObject> list=new ArrayList<ResourceObject>();
        for(String resourceId:ResourceIds){
            Resource r=resourceMapper.getById(resourceId);
            list.add(new ResourceObject(r.getName(),"/api/resource/"+r.getDownloadCode()+"/download"));
        }
       return list;
    }
}
