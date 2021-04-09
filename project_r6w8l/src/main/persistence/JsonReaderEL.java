package persistence;

import model.Event;
import model.EventList;
import model.EventNameIsNullException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Represents a reader that reads EventList from JSON data stored in file
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReaderEL {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderEL(String source) {
        this.source = source;
    }

    // EFFECTS: reads EventList from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public EventList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseEventList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses EventList from JSON object and returns it
    private EventList parseEventList(JSONObject jsonObject) {
        EventList list = new EventList();
        addEvents(list, jsonObject);
        return list;
    }

    // MODIFIES: list
    // EFFECTS: parses events from JSON object and adds them to EventList
    private void addEvents(EventList list, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json: jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(list, nextEvent);
        }
    }

    // MODIFIES: list
    // EFFECTS: parses time block from JSON object and adds it to EventList
    private void addEvent(EventList list, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int num = jsonObject.getInt("number");

        try {
            list.addEvent(new Event(name, num));
        } catch (EventNameIsNullException e) {
            System.out.println("Event name is null.");
        }
    }

}
