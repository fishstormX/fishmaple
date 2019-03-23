package fishmaple.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Html2StrFacade {
    public String html2String(String html){
        Document doc= Jsoup.parseBodyFragment(html);
        Jsoup.parse(Jsoup.parse(doc.html()).text()).text();
        return Jsoup.parse(Jsoup.parse(doc.html()).text()).text();
    }

}
