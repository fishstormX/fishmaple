package fishmaple.fisea;



import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import static com.alibaba.druid.util.Utils.getBoolean;

public class FiseaLogMonitor implements Referenceable, Closeable, Cloneable, MBeanRegistration {
    private String  log;

    public String isLog() {
        return log;
    }

    public void setLog(String log) {
        this.log=log;
    }

    public void configFromPropety(Properties properties) {
        {
            String property = properties.getProperty("log");
            if (property != null && property.length() > 0) {
                this.setLog(property);
            }
        }
    }


    public static Boolean getBoolean(Properties properties, String key) {
        String property = properties.getProperty(key);
        if ("true".equals(property)) {
            return Boolean.TRUE;
        } else if ("false".equals(property)) {
            return Boolean.FALSE;
        }
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
        return null;
    }

    @Override
    public void postRegister(Boolean registrationDone) {

    }

    @Override
    public void preDeregister() throws Exception {

    }

    @Override
    public void postDeregister() {

    }

    @Override
    public Reference getReference() throws NamingException {
        return null;
    }
}
