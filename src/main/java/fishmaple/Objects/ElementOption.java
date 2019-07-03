package fishmaple.Objects;

import java.util.List;

public class ElementOption {
    String value;
    String label;
    List<ElementOption> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ElementOption> getChildren() {
        return children;
    }

    public void setChildren(List<ElementOption> children) {
        this.children = children;
    }
}
