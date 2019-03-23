package fishmaple.api;

import fishmaple.DAO.ToolMapper;
import fishmaple.DTO.Resource;
import fishmaple.Objects.ResourceObject;
import fishmaple.Objects.ToolObject;
import fishmaple.Service.ResourceService;
import fishmaple.Service.ToolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import fishmaple.DTO.Tool;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * @author 鱼鱼
 * 工具箱的相关接口
 * */
@RestController
@RequestMapping("/api")
public class ToolController {
    @Autowired
    ToolService toolService;
    @Autowired
    ToolMapper toolDAO;
    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "/tools", method = RequestMethod.GET)
    public List<Tool> tools() {
        return toolDAO.getAllTools();
    }

    @RequestMapping(value = "/listtoolresources")
    public List<ToolObject> toolResources() {
        return toolDAO.getAllToolsResources();
    }

    @RequestMapping(value = "/{field}/search")
    public String getSearch(@PathVariable String field,
                            @RequestParam("searchvalue") String searchvalue) {
        if (field.equals("tool")&&searchvalue.equals("Heyfish")) {
                return "getupload";
        }
        return "";
    }

    @RequestMapping(value = "/tool", method = RequestMethod.POST)
    public String addNew(@RequestBody ToolObject tools) {
        toolService.addTool(tools.getName(), tools.getDescribe(), tools.getResourceIds());
        return "success";
    }

    @RequestMapping(value = "/tool/{id}/resource", method = RequestMethod.GET)
    public List<ResourceObject> getTResources(
            @PathVariable String id) {
        List<ResourceObject> list = resourceService.getResourcesByToolId(id);
        return list;
    }


}
