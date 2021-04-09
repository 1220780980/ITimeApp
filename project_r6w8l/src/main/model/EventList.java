package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of events that can be added as names when creating TimeBlock
public class EventList implements Writable {
    private List<Event> eventList;

    // EFFECTS: eventList is a list of events
    public EventList() {
        eventList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add an event to eventList, return "Event added successfully."
    //          if event name is null, throw EventNameIsNullException;
    //          if there is no repeated event already existing; otherwise return
    //          "Event already existed."
    public String addEvent(Event event) throws EventNameIsNullException {
        if (event.getName() == null) {
            throw new EventNameIsNullException();
        }

        for (Event e: eventList) {
            if (e.getName().equals(event.getName())) {
                return "Event already existed.";
            }
        }
        eventList.add(event);
        return "Event added successfully.";
    }

    // MODIFIES: this
    // EFFECTS: deletes the event with given name from eventList
    //          if there is a corresponding event, return "Event deleted.";
    //          otherwise return "Event does not exist."
    public String deleteEvent(String name) {
        for (Event event: eventList) {
            if (event.getName().equals(name)) {
                eventList.remove(event);
                return "Event deleted.";
            }
        }

        return "Event does not exist.";
    }

    // getter
    public List<Event> getEventList() {
        return this.eventList;
    }

    // EFFECTS: See interface Writable
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "Event List");
        json.put("events", eventsToJson());
        return json;
    }

    // EFFECTS: returns events in this list as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event event : eventList) {
            jsonArray.put(event.toJson());
        }

        return jsonArray;
    }
}
