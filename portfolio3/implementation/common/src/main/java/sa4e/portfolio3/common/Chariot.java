package sa4e.portfolio3.common;

import com.google.gson.Gson;

public class Chariot {
    private String chariotId;
    private Integer roundsPassed;
    private Boolean hasGreetedCaesar;

    public Chariot(String chariotId) {
        this.chariotId = chariotId;
        this.roundsPassed = 0;
        this.hasGreetedCaesar = false;
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

    public Boolean getHasGreetedCaesar() {
        return hasGreetedCaesar;
    }

    public void setHasGreetedCaesar(Boolean hasGreetedCaesar) {
        this.hasGreetedCaesar = hasGreetedCaesar;
    }

    public static Chariot fromJson(String json) {
        return new Gson().fromJson(json, Chariot.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
