package sa4e.portfolio3.common;

import com.google.gson.Gson;

public class Chariot {
    private String chariotId;
    private Integer roundsPassed;

    public Chariot(String chariotId) {
        this.chariotId = chariotId;
        this.roundsPassed = 0;
    }

    public String getChariotId() {
        return chariotId;
    }

    public void setChariotId(String chariotId) {
        this.chariotId = chariotId;
    }

    public Integer getRoundsPassed() {
        return roundsPassed;
    }

    public void setRoundsPassed(Integer roundsPassed) {
        this.roundsPassed = roundsPassed;
    }

    public static Chariot fromJson(String json) {
        return new Gson().fromJson(json, Chariot.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
