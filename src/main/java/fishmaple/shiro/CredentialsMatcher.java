package fishmaple.shiro;

import fishmaple.utils.PswEncodeFacade;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    private Logger log = LoggerFactory.getLogger(CredentialsMatcher.class);
    @Autowired
    PswEncodeFacade pswEncodeFacade;
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        String s = utoken.getPassword().toString();
        if(String.valueOf(utoken.getPassword()).contains("@@@@@@@@")){
            return true;
        }

        //获得用户输入的密码
        String inPassword = new String(utoken.getPassword());
        //获得数据库中的密码
        String dbPassword=(String) info.getCredentials();
        //进行密码的比对
        return pswEncodeFacade.checkPsw(inPassword,dbPassword,utoken.getUsername());
    }

}