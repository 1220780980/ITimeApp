import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event testEvent1;
    private Event testEvent2;

    @BeforeEach
    public void runBefore() {
        testEvent1 = new Event("Reading", 1);
        testEvent2 = new Event("Watching movie", 2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Reading", testEvent1.getName());
        assertEquals("Watching movie", testEvent2.getName());
        assertEquals(1, testEvent1.getNum());
        assertEquals(2, testEvent2.getNum());
    }

    @Test
    public void testGetters() {
        assertEquals("Reading", testEvent1.getName());
        assertEquals("Watching movie", testEvent2.getName());
        assertEquals(1, testEvent1.getNum());
        assertEquals(2, testEvent2.getNum());
    }

    @Test
    public void testSetName() {
        assertEquals("Reading", testEvent1.getName());
        testEvent1.setName("Studying...CPSC210");
        assertEquals("Studying...CPSC210", testEvent1.getName());
    }

    @Test
    public void testSetNumber() {
        assertEquals(2, testEvent2.getNum());
        testEvent2.setNum(3);
        assertEquals(3, testEvent2.getNum());
    }

}
