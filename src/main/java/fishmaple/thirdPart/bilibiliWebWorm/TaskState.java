package fishmaple.thirdPart.bilibiliWebWorm;

public class TaskState {
    private String state;
    private String mid;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public TaskState(String state, String mid, int id) {
        this.state = state;
        this.mid = mid;
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
