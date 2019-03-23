package fishmaple.Objects;

public class ResourceObject {
    private String name;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceObject(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
