package fishmaple.Objects;

import java.util.Objects;

public class SearchItem {
    String title;
    String content;
    String url;
    String type;
    Long timeline;

    public Long getTimeline() {
        return timeline;
    }

    public void setTimeline(Long timeline) {
        this.timeline = timeline;
    }

    public SearchItem(String title, String content, String url, String type, Long timeline) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.type = type;
        this.timeline = timeline;
    }

    /*public SearchItem(String title, String content, String url, String type) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.type = type;
    }*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchItem that = (SearchItem) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
