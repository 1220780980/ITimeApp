package persistence;

import model.Event;
import model.TimeBlockList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Represents a reader that reads TimeBlockList from JSON data stored in file
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReaderTBL {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderTBL(String source) {
        this.source = source;
    }

    // EFFECTS: reads TimeBlockList from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public TimeBlockList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTimeBlockList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source)  throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TimeBlockList from JSON object and returns it
    private TimeBlockList parseTimeBlockList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        TimeBlockList list = new TimeBlockList(name);
        addTimeBlocks(list, jsonObject);
        return list;
    }

    // MODIFIES: list
    // EFFECTS: parses time blocks from JSON object and adds them to TimeBLockList
    private void addTimeBlocks(TimeBlockList list, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("timeBlocks");
        for (Object json: jsonArray) {
            JSONObject nextTimeBlock = (JSONObject) json;
            addTimeBlock(list, nextTimeBlock);
        }
    }

    // MODIFIES: list
    // EFFECTS: parses time block from JSON object and adds it to TimeBLockList
    private void addTimeBlock(TimeBlockList list, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String startingTime = jsonObject.getString("startingTime");
        String endingTime = jsonObject.getString("endingTime");
        String eventName = jsonObject.getJSONObject("event").getString("eventName");
        int num = jsonObject.getJSONObject("event").getInt("num");

        list.addTimeBlock(name, startingTime, endingTime, new Event(eventName, num));
    }

}
