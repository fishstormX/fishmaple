package fishmaple.utils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml 转换工具类
 *
 * @author morning
 * @date 2015年2月16日 下午2:42:50
 */
public class SerializeXmlUtil {


    /**
     * 将XML转换成Map集合
     */
    public static Map<String, String> xmlToMap(String text) throws IOException, DocumentException, ParserConfigurationException, DocumentException {


        Document doc = DocumentHelper.parseText(text);

        Element root = doc.getRootElement();         // 获取根元素
        List<Element> list = root.elements();        // 获取所有节
        Map<String,String> map=new HashMap<String,String>();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }

        return map;
    }

    public static void main(String args[]) throws DocumentException, ParserConfigurationException, IOException {
        Map<String,String> m=SerializeXmlUtil.xmlToMap("<xml><ToUserName><![CDATA[gh_0aac85747ea0]]></ToUserName>\n" +
                "<FromUserName><![CDATA[o99ulwLuVRv1SWlOrvxHhxxcUdjc]]></FromUserName>\n" +
                "<CreateTime>1554628051</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[是吗]]></Content>\n" +
                "<MsgId>22256921449002022</MsgId>\n" +
                "</xml>");
        System.out.print(m.toString());
    }

}
