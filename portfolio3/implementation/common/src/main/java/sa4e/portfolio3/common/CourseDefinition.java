package sa4e.portfolio3.common;

import java.util.List;

import com.google.gson.Gson;

public class CourseDefinition {
    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public static CourseDefinition fromJSON(String json) {
        return new Gson().fromJson(json, CourseDefinition.class);
    }
}
