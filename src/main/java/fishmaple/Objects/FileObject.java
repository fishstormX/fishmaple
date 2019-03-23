package fishmaple.Objects;

public class FileObject {
    String fileName;
    String timeline;
    //文件后缀 单独提取出来
    String suffix;
    //type： 0 文件 1 文件夹
    Integer type;

    public FileObject(String fileName, String timeline, String suffix, Integer type) {
        this.fileName = fileName;
        this.timeline = timeline;
        this.suffix = suffix;
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
