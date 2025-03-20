package sa4e.portfolio3.common;

import com.google.gson.Gson;

import java.util.List;

public class Segment {
    private String segmentId;
    private String type;
    private List<String> nextSegments;

    public String getSegmentId() {
        return segmentId;
    }
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getNextSegments() {
        return nextSegments;
    }
    public void setNextSegments(List<String> nextSegments) {
        this.nextSegments = nextSegments;
    }

    public static Segment fromJson(String json) {
        return new Gson().fromJson(json, Segment.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
