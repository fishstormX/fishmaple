package fishmaple.DTO;

public class DocMember {
    private String id;
    private String name;
    private String enUrl;
    private String zhUrl;
    private String describe;
    private String imgUrl;
    private String imgStyle;

    public String getImgStyle() {
        return imgStyle;
    }

    public void setImgStyle(String imgStyle) {
        this.imgStyle = imgStyle;
    }

    public String getEnUrl() {
        return enUrl;
    }

    public void setEnUrl(String enUrl) {
        this.enUrl = enUrl;
    }

    public String getZhUrl() {
        return zhUrl;
    }

    public void setZhUrl(String zhUrl) {
        this.zhUrl = zhUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
