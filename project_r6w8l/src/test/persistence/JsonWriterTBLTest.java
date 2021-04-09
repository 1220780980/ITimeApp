package persistence;

import model.Event;
import model.TimeBlock;
import model.TimeBlockList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonWriterTBLTest extends JsonTestTBL {

    @Test
    public void testWriterTBLInvalidFile() {
        try {
            TimeBlockList list = new TimeBlockList("2021/02/05");
            JsonWriterTBL writer = new JsonWriterTBL("./data/my\0IllegalFileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterTBLEmptyTimeBlockList() {
        try {
            TimeBlockList list = new TimeBlockList("2021/02/05");
            JsonWriterTBL writer = new JsonWriterTBL("./data/testWriterTBLEmptyTimeBlockList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReaderTBL reader = new JsonReaderTBL("./data/testWriterTBLEmptyTimeBlockList.json");
            list = reader.read();
            assertEquals("2021/02/05", list.getName());
            assertEquals(0, list.getTimeBlockList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterTBLGeneralTimeBlockList() {
        try {
            TimeBlockList list = new TimeBlockList("2021/02/05");
            Event event1 = new Event("read", 1);
            Event event2 = new Event("write", 2);
            list.addTimeBlock("00.2", "09:00", "09:40", event1);
            list.addTimeBlock("00.3", "10:00", "10:15", event2);
            JsonWriterTBL writer = new JsonWriterTBL("./data/testWriterTBLGeneralTimeBlockList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReaderTBL reader = new JsonReaderTBL("./data/testWriterTBLGeneralTimeBlockList.json");
            list = reader.read();
            assertEquals("2021/02/05", list.getName());
            List<TimeBlock> timeBlocks = list.getTimeBlockList();
            assertEquals(2, timeBlocks.size());
            checkTimeBlock("00.2", "09:00", "09:40",
                    timeBlocks.get(0).getEvent(), 0, timeBlocks.get(0));
            checkTimeBlock("00.3", "10:00", "10:15",
                    timeBlocks.get(1).getEvent(), 0, timeBlocks.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
