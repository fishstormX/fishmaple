package fishmaple.DTO;

import java.util.List;

/**
 * 仓库类
 * */
public class DocRepository {
    private String name;
    private String id;
    private List<DocMember> docMembers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DocMember> getDocMembers() {
        return docMembers;
    }

    public void setDocMembers(List<DocMember> docMembers) {
        this.docMembers = docMembers;
    }
}
