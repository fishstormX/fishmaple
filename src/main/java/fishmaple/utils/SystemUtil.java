package fishmaple.utils;

public class SystemUtil {
    public static Integer getOs(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            return PublicConst.WINDOWS;
        }else{
            return PublicConst.LINUX;
        }
    }
}
