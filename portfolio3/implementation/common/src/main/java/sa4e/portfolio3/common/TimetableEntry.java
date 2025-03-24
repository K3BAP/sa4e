package sa4e.portfolio3.common;

import com.google.gson.Gson;

public class TimetableEntry {
    private String type;
    private String chariotId;
    private long timestamp;

    public static final String TYPE_FAILED = "failed";
    public static final String TYPE_FINISHED = "finished";
    public static final String TYPE_STARTED = "started";

    public TimetableEntry(String type, String chariotId, long timestamp) {
        this.type = type;
        this.chariotId = chariotId;
        this.timestamp = timestamp;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static TimetableEntry fromJson(String json) {
        return new Gson().fromJson(json, TimetableEntry.class);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChariotId() {
        return chariotId;
    }

    public void setChariotId(String chariotId) {
        this.chariotId = chariotId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
