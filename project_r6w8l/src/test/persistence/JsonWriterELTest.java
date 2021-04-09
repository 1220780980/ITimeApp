package persistence;

import model.Event;
import model.EventList;
import model.EventNameIsNullException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonWriterELTest extends JsonTestEL {

    @Test
    public void testWriterELInvalidFile() {
        try {
            EventList list = new EventList();
            JsonWriterEL writerEL = new JsonWriterEL("./data/my\0IllegalFileName.json");
            writerEL.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterELEmptyEventList() {
        try {
            EventList list = new EventList();
            JsonWriterEL writerEL = new JsonWriterEL("./data/testWriterELEmptyEventList.json");
            writerEL.open();
            writerEL.write(list);
            writerEL.close();

            JsonReaderEL readerEL = new JsonReaderEL("./data/testWriterELEmptyEventList.json");
            list = readerEL.read();
            assertEquals(0, list.getEventList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterELGeneralEventList() {
        try {
            EventList list = new EventList();
            Event event1 = new Event("read", 1);
            Event event2 = new Event("study", 2);
            Event event3 = new Event("rest", 3);
            try {
                list.addEvent(event1);
                list.addEvent(event2);
                list.addEvent(event3);
            } catch (EventNameIsNullException e) {
                fail();
            }

            JsonWriterEL writerEL = new JsonWriterEL("./data/testWriterELEmptyEventList.json");
            writerEL.open();
            writerEL.write(list);
            writerEL.close();

            JsonReaderEL readerEL = new JsonReaderEL("./data/testWriterELEmptyEventList.json");
            list = readerEL.read();
            List<Event> events = list.getEventList();
            assertEquals(3, events.size());
            checkEvent("read", 1, events.get(0));
            checkEvent("study", 2, events.get(1));
            checkEvent("rest", 3, events.get(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
