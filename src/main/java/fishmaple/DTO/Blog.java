package fishmaple.DTO;

import fishmaple.Objects.TagObject;
import fishmaple.utils.TimeDate;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author 鱼鱼
 * 博客类
 * */
public class Blog implements Serializable {
    private String title;
    private String content;
    private Long timeline;
    private String author;
    private Integer isOriginal;
    private String id;
    private List<String> tags;
    /** 锚点列表*/
    private String anchors;
    /** 置顶优先级*/
    private int priority;
    /** 是否开启词典*/
    private int useDictionary;
    private String cover;
    private Integer todo;
    /************不属于库中的字段*********/
    private String code;
    private String tagTemp;
    private boolean isUpdate;
    private List<String> tagTypes;
    private String TimelineStr;

    public String getTimelineStr() {
        return TimelineStr;
    }

    public void setTimelineStr(String timelineStr) {
        TimelineStr = timelineStr;
    }

    public Integer getTodo() {
        return todo;
    }

    public void setTodo(Integer todo) {
        this.todo = todo;
    }

    public Integer getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Integer isOriginal) {
        this.isOriginal = isOriginal;
    }

    public String getOutLine(){
        String contentTmp=content.replaceAll("id=\"","id=\"tmp");

        return "<h2>"+title+"</h2><a href='/blog/d?bid="+id+"' >"+title+"</a>"+contentTmp+"<br>"+
                "<img src='"+cover+"'>"+ TimeDate.timestamp2time(timeline*1000,1)+author+"<br>";
    }
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getUseDictionary() {
        return useDictionary;
    }

    public void setUseDictionary(int useDictionary) {
        this.useDictionary = useDictionary;
    }

    public List<String> getTagTypes() {
        return tagTypes;
    }

    public void setTagTypes(List<String> tagTypes) {
        this.tagTypes = tagTypes;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTagTemp() {
        return tagTemp;
    }

    public void setTagTemp(String tagTemp) {
        this.tagTemp = tagTemp;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAnchors() {
        return anchors;
    }

    public void setAnchors(String anchors) {
        this.anchors = anchors;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public Long getTimeline() {
        return timeline;
    }

    public void setTimeline(Long timeline) {
        this.timeline = timeline;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
