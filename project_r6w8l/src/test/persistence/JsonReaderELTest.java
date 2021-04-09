package persistence;

import model.Event;
import model.EventList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReaderELTest extends JsonTestEL {

    @Test
    public void testReaderELNonExistentFile() {
        JsonReaderEL readerEL = new JsonReaderEL("./data/fileNotFound.json");
        try {
            EventList list = readerEL.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderELEmptyEventList() {
        JsonReaderEL readerEL = new JsonReaderEL("./data/testReaderELEmptyEventList.json");
        try {
            EventList list = readerEL.read();
            assertEquals(0, list.getEventList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderELGeneralEventList() {
        JsonReaderEL readerEL = new JsonReaderEL("./data/testReaderELGeneralEventList.json");
        try {
            EventList list = readerEL.read();
            List<Event> events = list.getEventList();
            assertEquals(3, events.size());
            checkEvent("read", 1, events.get(0));
            checkEvent("study", 2, events.get(1));
            checkEvent("rest", 3, events.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
