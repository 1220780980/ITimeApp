package persistence;

import model.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonTestEL {
    protected void checkEvent(String name, int num, Event event) {
        assertEquals(name, event.getName());
        assertEquals(num, event.getNum());
    }
}
