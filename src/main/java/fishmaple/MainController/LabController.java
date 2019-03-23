package fishmaple.MainController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class LabController {
    @RequestMapping("/lab/json")
    public String blog(){
       return "lab/json";
    }

    @RequestMapping("/lab/siteMap")
    public String sitemap(){
        return "lab/siteMap";
    }

    @RequestMapping("/lab/webworm")
    public String worm(){
        return "lab/webworm";
    }

    @RequestMapping("/lab/multiThreadWebworm")
    public String multiThreadWebWorm(){
        return "lab/multiThreadWebworm";
    }

    @RequestMapping("/lab/qrCode")
    public String getQr(){
        return "lab/qrCode";
    }
}
