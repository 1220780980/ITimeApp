package persistence;

import model.TimeBlock;
import model.TimeBlockList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReaderTBLTest extends JsonTestTBL {

    @Test
    public void testReaderTBLNonExistentFile() {
        JsonReaderTBL reader = new JsonReaderTBL("./data/fileNotFound.json");
        try {
            TimeBlockList list = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderTBLEmptyTimeBlockList() {
        JsonReaderTBL reader = new JsonReaderTBL("./data/testReaderTBLEmptyTimeBlockList.json");
        try {
            TimeBlockList list = reader.read();
            assertEquals("2021/02/05", list.getName());
            assertEquals(0, list.getTimeBlockList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderTBLGeneralTimeBlockList() {
        JsonReaderTBL reader = new JsonReaderTBL("./data/testReaderTBLGeneralTimeBlockList.json");
        try {
            TimeBlockList list = reader.read();
            assertEquals("2021/02/05", list.getName());
            List<TimeBlock> timeBlocks = list.getTimeBlockList();
            assertEquals(2, timeBlocks.size());
            checkTimeBlock("00.2", "09:00", "09:40",
                    timeBlocks.get(0).getEvent(), 0, timeBlocks.get(0));
            checkTimeBlock("00.3", "10:00", "10:15",
                    timeBlocks.get(1).getEvent(), 0, timeBlocks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
