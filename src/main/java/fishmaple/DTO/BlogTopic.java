package fishmaple.DTO;

public class BlogTopic {
    Integer id;
    String topic;
    String userId;
    Integer fTopicId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getfTopicId() {
        return fTopicId;
    }

    public void setfTopicId(Integer fTopicId) {
        this.fTopicId = fTopicId;
    }
}
