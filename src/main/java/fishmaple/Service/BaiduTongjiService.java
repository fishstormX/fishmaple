package fishmaple.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import fishmaple.DAO.TongjiMapper;
import fishmaple.DTO.Tongji;
import fishmaple.thirdPart.toutiaoWorm.ToutiaoObject;
import fishmaple.utils.HttpClientUtil;
import fishmaple.utils.JedisUtil;
import fishmaple.utils.SerizlizeUtil;
import fishmaple.utils.TimeDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import sun.net.www.http.HttpClient;

import java.util.*;

@Service
public class BaiduTongjiService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TongjiMapper tongjiMapper;

    @Value("${localConfig.tongji.name}")
    String name;
    @Value("${localConfig.tongji.pswd}")
    String pswd;
    @Value("${localConfig.tongji.token}")
    String token;
    @Value("${localConfig.tongji.siteId}")
    String siteId;

    private String formatStrDate(Integer date){
        if(date>9){
            return date.toString();
        }else{
            return "0"+date.toString();
        }
    }

    public static void main(String args[]){
        BaiduTongjiService baiduTongjiService=new BaiduTongjiService();
        baiduTongjiService.getResult();
        //System.out.println(baiduTongjiService.getJsonResult("20190209",TimeDate.getTimeStampNow()));
    }

    public Tongji getResult(){
        Jedis jedis= JedisUtil.getJedis();
        try{
            Tongji total = new Tongji();
            total.setDate(TimeDate.getTimeStampNow());
            total.setUv(0);
            total.setPv(0);
            total.setIp(0);
            if(null == jedis.get("tongji")){
                Map<String,Tongji> map = getJsonResult("20190304",TimeDate.getTimeStampNow());
                String index = tongjiMapper.getIndex();
                Boolean flag=false;
                for(Map.Entry<String,Tongji> entry:map.entrySet()){
                    total.setUv(total.getUv()+entry.getValue().getUv());
                    total.setPv(total.getPv()+entry.getValue().getPv());
                    total.setIp(total.getIp()+entry.getValue().getIp());
                    if(entry.getKey().equals(TimeDate.getTimeStampNow())){
                        tongjiMapper.update(entry.getValue());
                    }else if(flag){
                        tongjiMapper.add(entry.getValue());
                    } else if(entry.getKey().equals(index)){
                        if(entry.getKey().equals(TimeDate.getTimeStampNow())){
                            tongjiMapper.update(entry.getValue());
                        }
                        flag=true;
                        continue;
                    }
                }
                jedis.set("tongji",SerizlizeUtil.serialize(total),"NX","EX",1800);

                return total;
            }else{
                Object obj=SerizlizeUtil.unserizlize(jedis.get("tongji"));
                if(obj instanceof Tongji){
                    System.out.println((Tongji) obj);
                    return (Tongji) obj;
                }
            }
        }catch(Exception e){

        }finally{
            jedis.close();
        }
        return null;
    }

    private String  getJson(String start,String end){
        return "{\"header\": {" +
                "        \"username\": \""+name+"\"," +
                "        \"password\": \""+pswd+"\"," +
                "        \"token\": \""+token+"\"," +
                "        \"account_type\": 1" +
                "    }," +
                "   \"body\": {" +
                "        \"site_id\": \""+siteId+"\"," +
                "        \"start_date\": \""+start+"\"," +
                "        \"end_date\": \""+end+"\"," +
                "        \"metrics\": \"pv_count,visitor_count,ip_count\"," +
                "        \"method\": \"overview/getTimeTrendRpt\"" +
                "    }" +
                "}";
    }

    public List<String> dateSplit(String start,String end){
        List list = new ArrayList<String>();
        list.add(start);
        Integer startMonth=new Integer(start.substring(4,6));
        Integer startYear=new Integer(start.substring(0,4));
        String startDayStr=start.substring(6,8);

        while(TimeDate.dateDifference(start,end)>88){
            if(new Integer(startMonth)>10){
                startYear=startYear+1;
                startMonth=1;
                start=startYear+"01"+startDayStr;
            }else{
                startYear=startYear;
                startMonth=startMonth+2;
                start=startYear+formatStrDate(startMonth)+startDayStr;

            }
            list.add(start);
        }
        list.add(end);
        return list;
    }

    private Map<String,Tongji> getJsonResult(String start , String end) {
        List<String> list =new ArrayList<>();
        Map<String,Tongji> results=new LinkedHashMap<>();
        if(TimeDate.dateDifference(start,end)>88){
            //日期拆分
            list=dateSplit(start,end);
        }else{
            list.add(start);
            list.add(end);
        }
        for(int i=0;i<list.size()-1;i++){
        String result="";
            try {
                result = HttpClientUtil.doPost("https://api.baidu.com/json/tongji/v1/ReportService/getData",getJson(list.get(i),list.get(i+1)));
                Map<String,String> map=JSON.parseObject(result,new TypeReference<Map<String,String>>(){});
                map=JSON.parseObject(map.get("body").toString(),new TypeReference<Map<String,String>>(){});
                List<String> listtmp=JSON.parseObject(map.get("data"),new TypeReference<List<String>>(){});
                map=JSON.parseObject(listtmp.get(0),new TypeReference<Map<String,String>>(){});
                map=JSON.parseObject(map.get("result"),new TypeReference<Map<String,String>>(){});
                listtmp=JSON.parseObject(map.get("items"),new TypeReference<List<String>>(){});
                List<String> dates=JSON.parseObject(listtmp.get(0),new TypeReference<List<String>>(){});
                List<String> data=JSON.parseObject(listtmp.get(1),new TypeReference<List<String>>(){});
                for(int j=0;j<dates.size();j++){
                    List<String> datatmp=JSON.parseObject(data.get(j),new TypeReference<List<String>>(){});
                    Tongji tongji=new Tongji();
                    tongji.setDate(dates.get(j).substring(2,dates.get(j).length()-2));
                    tongji.setPv((datatmp.get(0).equals("--")?0:new Integer(datatmp.get(0))));
                    tongji.setUv((datatmp.get(1).equals("--")?0:new Integer(datatmp.get(1))));
                    tongji.setIp((datatmp.get(2).equals("--")?0:new Integer(datatmp.get(2))));
                    results.put(dates.get(j).substring(2,dates.get(j).length()-2),tongji);
                }

            } catch (Exception e) {
                System.out.println("ERROR");
                logger.error("error,{}",e.getMessage());
            }
        }
        return results;
    }


}
