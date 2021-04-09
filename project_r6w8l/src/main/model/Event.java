package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an event that can be added as a name when creating TimeBlock
public class Event implements Writable {
    private String name;
    private int num;

    // REQUIRES: name is not null
    // EFFECTS: name is the event name, num represents the order that event
    //          has been created in
    public Event(String name, int num) {
        this.name = name;
        this.num = num;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public int getNum() {
        return this.num;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int number) {
        this.num = number;
    }

    // EFFECTS: See interface Writable
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("number", num);
        return json;
    }
}
