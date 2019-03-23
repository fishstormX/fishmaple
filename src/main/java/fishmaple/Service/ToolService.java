package fishmaple.Service;

import fishmaple.DAO.ToolMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ToolService {

    public void addTool(String name, String describe, List<String> resourceIdList);
}
