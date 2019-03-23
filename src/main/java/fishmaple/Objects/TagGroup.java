package fishmaple.Objects;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagGroup {
    private String label;
    private Set<Map.Entry<String,String>> options;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Map.Entry<String, String>> getOptions() {
        return options;
    }

    public void setOptions(Set<Map.Entry<String, String>> options) {
        this.options = options;
    }
}
