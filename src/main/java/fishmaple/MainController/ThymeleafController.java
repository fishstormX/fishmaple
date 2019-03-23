package fishmaple.MainController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {
    @RequestMapping(value="/hello")
    public String index(ModelMap map){
        map.addAttribute("name","你的名字是");
        return "indexThy";
    }


}
