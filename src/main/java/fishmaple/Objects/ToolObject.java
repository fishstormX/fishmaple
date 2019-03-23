package fishmaple.Objects;

import java.util.List;

public class ToolObject {
    private String name;
    private String describe;
    private String resourceName;
    private String author;
    private String path;
    private List<String> resourceIds;
    private String resourceIdGroup;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResourceIdGroup() {
        return resourceIdGroup;
    }

    public void setResourceIdGroup(String resourceIdGroup) {
        this.resourceIdGroup = resourceIdGroup;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
