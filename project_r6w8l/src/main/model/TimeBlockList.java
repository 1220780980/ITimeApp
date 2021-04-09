package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of time blocks with the date as its name
public class TimeBlockList implements Writable {
    private String name;
    private List<TimeBlock> timeBlockList;

    // REQUIRES: name is a proper date
    // EFFECTS: name is the date of the day time recording has been made;
    //          timeBlockList is a list of time blocks of that day
    public TimeBlockList(String name) {
        this.name = name;
        timeBlockList = new ArrayList<>();
    }

    // getters
    public String getName() {
        return this.name;
    }

    public List<TimeBlock> getTimeBlockList() {
        return this.timeBlockList;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: name is not null, startingTime and endingTime are in
    //           format "xx:xx" (x is integer from 0-9), endingTime must be later
    //           than startingTime
    // MODIFIES: this
    // EFFECTS: creates a new time block and adds it to timeBlockList, return
    //          "Time block recorded."
    public String addTimeBlock(String name, String startingTime, String endingTime, Event event) {
        TimeBlock newTB = new TimeBlock();
        newTB.setName(name);
        newTB.setStartingTime(startingTime);
        newTB.setEndingTime(endingTime);
        newTB.setEvent(event);
        timeBlockList.add(newTB);

        return "Time block recorded.";
    }

    // MODIFIES: this
    // EFFECTS: deletes the time block with given name from timeBlockList
    //          if there is a corresponding time block, return "Time block
    //          deleted."; otherwise return "Time block does not exist."
    public String deleteTimeBlock(String name) {
        for (TimeBlock block : timeBlockList) {
            if (block.getName() == name) {
                timeBlockList.remove(block);
                return "Time block deleted.";
            }
        }

        return "Time block does not exist";
    }

    // EFFECTS: See interface Writable
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("timeBlocks", timeBlocksToJson());
        return json;
    }

    // EFFECTS: returns time blocks in this list as a JSON array
    private JSONArray timeBlocksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TimeBlock block : timeBlockList) {
            jsonArray.put(block.toJson());
        }

        return jsonArray;
    }

}
