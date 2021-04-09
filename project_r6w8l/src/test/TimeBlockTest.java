import model.Event;
import model.TimeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeBlockTest {
    private TimeBlock testBlock;
    private Event testEvent;

    @BeforeEach
    public void runBefore() {
        testBlock = new TimeBlock();
        testEvent = new Event("Studying...CPSC210", 1);
    }

    @Test
    public void testConstructor() {
        assertEquals("To be assigned", testBlock.getName());
        assertEquals("00:00", testBlock.getStartingTime());
        assertEquals("00:00", testBlock.getEndingTime());
        assertEquals(0, testBlock.getDuration());
    }

    @Test
    public void testGetters() {
        assertEquals("To be assigned", testBlock.getName());
        assertEquals("00:00", testBlock.getStartingTime());
        assertEquals("00:00", testBlock.getEndingTime());
        assertEquals(0, testBlock.getDuration());
    }

    @Test
    public void testSetters() {
        assertEquals("To be assigned", testBlock.getName());
        assertEquals("00:00", testBlock.getStartingTime());
        assertEquals("00:00", testBlock.getEndingTime());

        testBlock.setName("Reading");
        assertEquals("Reading", testBlock.getName());
        testBlock.setStartingTime("08:00");
        assertEquals("08:00", testBlock.getStartingTime());
        testBlock.setEndingTime("08:45");
        assertEquals("08:45", testBlock.getEndingTime());
        testBlock.setEvent(testEvent);
        assertEquals(testEvent, testBlock.getEvent());
    }

    @Test
    public void testToString() {
        testBlock.setName("Reading");
        testBlock.setEvent(testEvent);
        testBlock.setStartingTime("08:00");
        testBlock.setEndingTime("08:45");
        assertEquals("Studying...CPSC210: 08:00 - 08:45", testBlock.toString());
    }

}
