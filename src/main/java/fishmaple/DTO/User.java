package fishmaple.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * @author 鱼鱼
 * 用户类
 * */
public class User implements Serializable {
    private String  id;
    private String name;
    private String auth;
    private String registertime;
    private String pswd;
    private String email;
    private String identifyingCode;
    private transient Boolean rememberMe;
    private Set<Role> roles=new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", auth='" + auth + '\'' +
                ", registertime='" + registertime + '\'' +
                ", pswd='" + pswd + '\'' +
                ", roles=" + roles +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getRegistertime() {
        return registertime;
    }

    public void setRegistertime(String registertime) {
        this.registertime = registertime;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
