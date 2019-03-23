package fishmaple.Service;

import fishmaple.DTO.Resource;
import fishmaple.Objects.ResourceObject;

import java.util.List;

public interface ResourceService {

    public List<ResourceObject> getResourcesByToolId(String tid);
}
