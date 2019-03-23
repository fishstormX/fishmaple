package fishmaple.DTO;

/**
 * @author 鱼鱼
 * 词典类
 * */
public class Dictionary {
    /** 词条名*/
    private String key;
    /** 词条值*/
    private String value;
    private String author;
    private String timeline;

    public String getOutLine(){
        return "<h2>"+key+"</h2>"+value+"<br>"+author+timeline+"<br>";
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
