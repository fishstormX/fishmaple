package fishmaple.DTO;

import java.io.Serializable;

public class Tongji implements Serializable {
    String date;
    Integer uv;
    Integer pv;
    Integer ip;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Tongji{" +
                "date='" + date + '\'' +
                ", uv=" + uv +
                ", pv=" + pv +
                ", ip=" + ip +
                '}';
    }
}
