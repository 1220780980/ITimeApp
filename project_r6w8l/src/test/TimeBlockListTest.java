import model.Event;
import model.TimeBlockList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeBlockListTest {
    private TimeBlockList testTBList;
    private Event testEvent1;
    private Event testEvent2;

    @BeforeEach
    public void runBefore() {
        testTBList = new TimeBlockList("2021/02/14");
        testEvent1 = new Event("Reading", 1);
        testEvent2 = new Event("Watching movie", 2);
    }

    @Test
    public void testConstructor() {
        assertEquals("2021/02/14", testTBList.getName());
        assertEquals(0, testTBList.getTimeBlockList().size());
    }

    @Test
    public void testGetters() {
        assertEquals("2021/02/14", testTBList.getName());
        assertEquals(0, testTBList.getTimeBlockList().size());
    }

    @Test
    public void testSetName() {
        assertEquals("2021/02/14", testTBList.getName());
        assertEquals(0, testTBList.getTimeBlockList().size());
        testTBList.setName("2021/02/15");
        assertEquals("2021/02/15", testTBList.getName());

    }

    @Test
    public void testAddTimeBlock() {
        assertEquals("2021/02/14", testTBList.getName());
        assertEquals(0, testTBList.getTimeBlockList().size());

        testTBList.addTimeBlock("0214.1", "18:50", "19:20", testEvent1);
        assertEquals("0214.1", testTBList.getTimeBlockList().get(0).getName());
        assertEquals("18:50", testTBList.getTimeBlockList().get(0).getStartingTime());
        assertEquals("19:20", testTBList.getTimeBlockList().get(0).getEndingTime());
        testTBList.addTimeBlock("0214.2", "20:13", "21:08", testEvent2);
        assertEquals("0214.2", testTBList.getTimeBlockList().get(1).getName());
        assertEquals("20:13", testTBList.getTimeBlockList().get(1).getStartingTime());
        assertEquals("21:08", testTBList.getTimeBlockList().get(1).getEndingTime());
    }

    @Test
    public void testDeleteTimeBlockExist() {
        assertEquals("2021/02/14", testTBList.getName());
        assertEquals(0, testTBList.getTimeBlockList().size());
        testTBList.addTimeBlock("0214.1", "18:50", "19:20", testEvent1);
        testTBList.addTimeBlock("0214.2", "20:13", "21:08", testEvent2);

        testTBList.deleteTimeBlock("0214.1");
        assertEquals(1, testTBList.getTimeBlockList().size());
        assertEquals("0214.2", testTBList.getTimeBlockList().get(0).getName());
    }

    @Test
    public void testDeleteTimeBlockDoesNotExist() {
        assertEquals("2021/02/14", testTBList.getName());
        assertEquals(0, testTBList.getTimeBlockList().size());
        testTBList.addTimeBlock("0214.1", "18:50", "19:20", testEvent1);
        testTBList.addTimeBlock("0214.2", "20:13", "21:08", testEvent2);

        testTBList.deleteTimeBlock("0214.7");
        assertEquals(2, testTBList.getTimeBlockList().size());
        assertEquals("0214.1", testTBList.getTimeBlockList().get(0).getName());
        assertEquals("0214.2", testTBList.getTimeBlockList().get(1).getName());
    }

}
