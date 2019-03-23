package fishmaple.Objects;

import fishmaple.DTO.Blog;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class SearchResult {
    int totalB;
    int totalT;
    HashSet<String> keys;
    LinkedHashSet<SearchItem> items;
    Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getTotalB() {
        return totalB;
    }

    public void setTotalB(int totalB) {
        this.totalB = totalB;
    }

    public int getTotalT() {
        return totalT;
    }

    public void setTotalT(int totalT) {
        this.totalT = totalT;
    }

    public HashSet<String> getKeys() {
        return keys;
    }

    public void setKeys(HashSet<String> keys) {
        this.keys = keys;
    }

    public LinkedHashSet<SearchItem> getItems() {
        return items;
    }

    public void setItems(LinkedHashSet<SearchItem> items) {
        this.items = items;
    }
}
