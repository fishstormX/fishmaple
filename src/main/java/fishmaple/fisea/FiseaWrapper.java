package fishmaple.fisea;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@ConfigurationProperties(prefix = "spring.fisea")
class FiseaWrapper extends FiseaLogMonitor implements InitializingBean {
    @Autowired
    private DataSourceProperties basicProperties;
    private String log;

    public String isLog() {
        return log;
    }

    public void setLog(String log) {
        super.setLog(log);
    }

    FiseaWrapper() {
    }
    @Override
    public void afterPropertiesSet() throws Exception {

    }
}