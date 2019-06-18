package fishmaple.DTO;

public class FriendLink {
    String id;
    String name;
    String url;
    String avatar;
    String describe;
    int circled;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getCircled() {
        return circled;
    }

    public void setCircled(int circled) {
        this.circled = circled;
    }
}
