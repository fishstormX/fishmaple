package fishmaple.Service;

import fishmaple.utils.PublicConst;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MobileService {
    //根据设备类型选择站点
    public String toMobile(HttpServletRequest request,String url){
        if (request.getHeader("User-Agent") != null) {
            for (String mobileAgent : PublicConst.mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    return "m/"+url;
                }
            }
        }
        return url;
    }
}
