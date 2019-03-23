package fishmaple.thirdPart.baiduNLP.service;

/**
 * Baidu自然语言处理三方接口
 */

import com.alibaba.fastjson.JSON;
import com.baidu.aip.nlp.AipNlp;
import fishmaple.thirdPart.baiduNLP.DTO.NLPDepObject;
import org.json.JSONObject;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BaiduNLPHelperService {
    //设置APPID/AK/SK
    private static final String APP_ID = "14409067";
    private static final String API_KEY = "";
    private static final String SECRET_KEY = "";
    private static AipNlp client = null;

    // 依存句法分析
    public List<NLPDepObject> depar(String content){
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("mode", 1);
        //org原生
        JSONObject res = getClient().depParser(content, options);
        //阿里巴巴
        JSONArray jsonArray= JSON.parseArray(res.getJSONArray("items").toString());
        List<NLPDepObject> list = jsonArray.toJavaList(NLPDepObject.class);
        return list;
    }

    private AipNlp getClient(){
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        if (this.client == null){
            this.client=client;
        }
        return this.client;
    }
    public static void main(String[] args) {


        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        // client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口(文章标签)

        //String content = "基于Tomcat的服务器应酬列表管理方式";

        // 传入可选参数调用接口
       // HashMap<String, Object> options = new HashMap<String, Object>();

        // 句法分析(搜索引擎切词)
        //JSONObject res = client.depParser(content, options);
      //  System.out.println(res.toString(2));

       // JSONObject res = getClient().wordSimEmbedding("Tomcat", "服务器", options);
        //System.out.println(res.toString(2));
        //String word1 = "北京";
       // String word2 = "上海";

        // 传入可选参数调用接口
        //HashMap<String, Object> options = new HashMap<String, Object>();
       // options.put("mode", 0);

        // 词义相似度
       // JSONObject res = client.wordSimEmbedding(word1, word2, options);
       // System.out.println(res.toString(2));
        //res = client.wordSimEmbedding("数据库", "Mysql", options);
       // System.out.println(res.toString(2));
       // res = client.wordSimEmbedding("redis", "服务器", options);
       // System.out.println(res.toString(2));
    }
}
