package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a time block having name, starting time, ending time, event, and duration (in minutes)
public class TimeBlock implements Writable {
    private String name;
    private String startingTime;
    private String endingTime;
    private Event event;
    private double duration;

    // EFFECTS: name is a unique attribute to each time block, that is useful
    //          when deleting a timeBlock; startingTime is the start time;
    //          endingTime is the end time; duration is the length of time block;
    //          these will be set as default values when time block first created
    public TimeBlock() {
        name = "To be assigned";
        startingTime = "00:00";
        endingTime = "00:00";
        duration = 0;
    }


    // getters
    public String getName() {
        return this.name;
    }

    public String getStartingTime() {
        return this.startingTime;
    }

    public String getEndingTime() {
        return this.endingTime;
    }

    public Event getEvent() {
        return this.event;
    }

    public double getDuration() {
        return this.duration;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        String tb = getEvent().getName() + ": " + getStartingTime() + " - " + getEndingTime();
        return tb;
    }

    // EFFECTS: See interface Writable
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("startingTime", startingTime);
        json.put("endingTime", endingTime);
        json.put("event", eventToJson());
        json.put("duration", duration);
        return json;
    }

    // EFFECTS: returns the corresponding event
    private JSONObject eventToJson() {
        JSONObject json = new JSONObject();
        json.put("eventName", event.getName());
        json.put("num", event.getNum());
        return json;
    }

}
