package sa4e.portfolio3.common;

import java.util.List;

public class Track {
    private String trackId;
    private List<Segment> segments;

    public String getTrackId() {
        return trackId;
    }
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
    public List<Segment> getSegments() {
        return segments;
    }
    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
}
