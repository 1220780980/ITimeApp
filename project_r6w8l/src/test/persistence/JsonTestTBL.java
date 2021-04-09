package persistence;

import model.Event;
import model.TimeBlock;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonTestTBL {
    protected void checkTimeBlock(String name, String sTime, String eTime, Event event, double dur, TimeBlock tb) {
        assertEquals(name, tb.getName());
        assertEquals(sTime, tb.getStartingTime());
        assertEquals(eTime, tb.getEndingTime());
        assertEquals(event, tb.getEvent());
        assertEquals(dur, tb.getDuration());
    }
}
