import model.Event;
import model.EventList;
import model.EventNameIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EventListTest {
    private Event testEvent1;
    private Event testEvent2;
    private EventList testEventList;

    @BeforeEach
    public void runBefore() {
        testEvent1 = new Event("Reading", 1);
        testEvent2 = new Event("Watching movie", 2);
        testEventList = new EventList();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testEventList.getEventList().size());
    }

    @Test
    public void testAddEventToListWithoutDuplicate() {
        assertEquals(0, testEventList.getEventList().size());
        try {
            testEventList.addEvent(testEvent1);
        } catch (EventNameIsNullException e) {
            fail();
        }
        assertEquals(1, testEventList.getEventList().size());
        assertEquals(testEvent1, testEventList.getEventList().get(0));

        try {
            testEventList.addEvent(testEvent2);
        } catch (EventNameIsNullException e) {
            fail();
        }
        assertEquals(2, testEventList.getEventList().size());
        assertEquals(testEvent2, testEventList.getEventList().get(1));
    }

    @Test
    public void testAddEventToListWithDuplicate() {
        assertEquals(0, testEventList.getEventList().size());
        try {
            testEventList.addEvent(testEvent1);
            testEventList.addEvent(testEvent2);
        } catch (EventNameIsNullException e) {
            fail();
        }
        assertEquals(2, testEventList.getEventList().size());
        assertEquals(testEvent1, testEventList.getEventList().get(0));
        assertEquals(testEvent2, testEventList.getEventList().get(1));

        try {
            testEventList.addEvent(testEvent2);
        } catch (EventNameIsNullException e) {
            fail();
        }
        assertEquals(2, testEventList.getEventList().size());
        assertEquals(testEvent1, testEventList.getEventList().get(0));
        assertEquals(testEvent2, testEventList.getEventList().get(1));
    }

    @Test
    public void testDeleteEventExist() {
        assertEquals(0, testEventList.getEventList().size());
        try {
            testEventList.addEvent(testEvent1);
            testEventList.addEvent(testEvent2);
        } catch (EventNameIsNullException e) {
            fail();
        }

        assertEquals(2, testEventList.getEventList().size());

        testEventList.deleteEvent("Reading");
        assertEquals(1, testEventList.getEventList().size());
    }

    @Test
    public void testDeleteEventDoesNotExist() {
        assertEquals(0, testEventList.getEventList().size());
        try {
            testEventList.addEvent(testEvent1);
            testEventList.addEvent(testEvent2);
        } catch (EventNameIsNullException e) {
            fail();
        }

        assertEquals(2, testEventList.getEventList().size());

        testEventList.deleteEvent("Studying");
        assertEquals(2, testEventList.getEventList().size());
    }

    @Test
    public void testAddEventNameIsNull() {
        Event event = new Event(null, 10);

        assertEquals(0, testEventList.getEventList().size());
        try {
            testEventList.addEvent(event);
            fail();
        } catch (EventNameIsNullException e) {
            // pass
        }

        assertEquals(0, testEventList.getEventList().size());
    }

}
