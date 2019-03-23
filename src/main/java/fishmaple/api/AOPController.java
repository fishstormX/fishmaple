package fishmaple.api;

import fishmaple.DAO.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AOPController {
    @Autowired
    private ConfigMapper configMapper;
    @PostMapping("reloadSer")
    public String restartService(@RequestBody Map<String,String> map){

        if(map.get("pwd").equals(configMapper.getValue("re_key"))){
            try {
                String shpath="/home/ubuntu/restart.sh";
                Process ps = Runtime.getRuntime().exec(shpath);
                ps.waitFor();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return "success";
        }
        return "fail";
    }
}
