package fishmaple.utils;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    public static Map<String,String> getInfo(HttpServletRequest request){
        //获取浏览器信息
        String ua = request.getHeader("User-Agent");
//转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
//获取浏览器信息
        Browser browser = userAgent.getBrowser();
//获取系统信息
        OperatingSystem os = userAgent.getOperatingSystem();
//系统名称
        String system = os.getName();
//浏览器名称
        String browserName = browser.getName();
        Map<String,String> map=new HashMap<>();
        map.put("browser",browserName);
        map.put("os",system);
        return map;
    }


}
