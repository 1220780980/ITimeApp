package ui;

import model.*;
import model.Event;
import persistence.JsonReaderEL;
import persistence.JsonReaderTBL;
import persistence.JsonWriterEL;
import persistence.JsonWriterTBL;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

// ITimeUI
public class ITimeUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 450;
    private static final String JSON_STORE_TBL = "./data/TimeBlockList.json";
    private static final String JSON_STORE_EL = "./data/EventList.json";

    private EventList eventList;
    private TimeBlockList blockList;
    private JsonWriterTBL jsonWriterTBL;
    private JsonReaderTBL jsonReaderTBL;
    private JsonWriterEL jsonWriterEL;
    private JsonReaderEL jsonReaderEL;

    private JPanel panel = new JPanel();

    private TextField fieldEvent;
    private TextField fieldST;
    private TextField fieldET;
    private TextArea textArea;
    private JComboBox<TimeBlock> comboBox = new JComboBox<>();


    // EFFECTS: initializes ITimeUI
    public ITimeUI() {
        super("ITime");

        eventList = new EventList();
        blockList = new TimeBlockList("2002/01/01");

        jsonWriterTBL = new JsonWriterTBL(JSON_STORE_TBL);
        jsonReaderTBL = new JsonReaderTBL(JSON_STORE_TBL);
        jsonWriterEL = new JsonWriterEL(JSON_STORE_EL);
        jsonReaderEL = new JsonReaderEL(JSON_STORE_EL);

        init();

        this.setSize(WIDTH, HEIGHT);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: sets up the components of the panel
    private void init() {
        panel.setLayout(null);
        panel.setBounds(0, 0, WIDTH, HEIGHT);
        panel.setBackground(new Color(208, 193, 198));
        this.add(panel);

        this.timeBlockDisplay(panel);    // display time block list
        this.addTimeBlock(panel);        // add new time block
        this.deleteTimeBlock(panel);     // delete an existing time block
        this.btnSave(panel);             // saves data to file
        this.btnLoad(panel);             // loads data from file

    }

    // MODIFIES: this
    // EFFECTS: displays the time block list that has been recorded to the user
    private void timeBlockDisplay(JPanel panel) {
        JLabel label = new JLabel("Time Blocks:");
        label.setLocation(40, 32);
        label.setSize(90, 15);

        textArea = new TextArea("Start record now!");
        textArea.setEditable(false);
        textArea.setLocation(35, 55);
        textArea.setSize(180, 240);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setSize(180, 240);
        scrollPane.setLocation(35, 55);

        panel.add(label);
        panel.add(textArea);
        panel.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECT: Add a new time block to the list
    private void addTimeBlock(JPanel panel) {
        JLabel label = new JLabel("Add time block:");
        label.setLocation(400, 32);
        label.setSize(120, 15);
        this.textEvent(panel);           // text box for entering event
        this.textStartingTime(panel);    // text box for entering starting time
        this.textEndingTime(panel);      // text box for entering ending time
        this.btnRecord(panel);           // records time block to the list

        panel.add(label);
    }

    // MODIFIES: this
    // EFFECTS: Text box enter the event name for the time block to be recorded
    private void textEvent(JPanel panel) {
        JLabel label = new JLabel("Event:");
        fieldEvent = new TextField(2);
        label.setBounds(400, 55, 40, 20);
        fieldEvent.setLocation(400, 75);
        fieldEvent.setSize(115, 20);

        panel.add(label);
        panel.add(fieldEvent);
    }

    // MODIFIES: this
    // EFFECTS: Text box for entering the starting time of the time block
    //          to be recorded
    private void textStartingTime(JPanel panel) {
        JLabel label1 = new JLabel("Starting Time:");
        fieldST = new TextField(2);
        label1.setBounds(400, 95, 100, 20);
        fieldST.setLocation(400, 115);
        fieldST.setSize(115,20);

        panel.add(label1);
        panel.add(fieldST);
    }

    // MODIFIES: this
    // EFFECTS: Text box for entering the ending time of the time block
    //          to be recorded
    private void textEndingTime(JPanel panel) {
        JLabel label2 = new JLabel("Ending Time:");
        fieldET = new TextField(2);
        label2.setBounds(400, 135, 100, 20);
        fieldET.setLocation(400, 155);
        fieldET.setSize(115, 20);

        panel.add(label2);
        panel.add(fieldET);
    }

    // MODIFIES: this
    // EFFECTS: records the newly added time block to the blockList
    private void btnRecord(JPanel panel) {
        JButton btn = new JButton("Record Time Block");
        btn.setBounds(400, 185, 130, 20);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordNewTimeBLock();
                playRecordSound();
            }
        });

        panel.add(btn);
    }

    // MODIFIES: this
    // EFFECTS: record the data for newly added time block to list,
    //          display on the scroll pane
    private void recordNewTimeBLock() {
        TimeBlock block = new TimeBlock();

        Event event = addNewEvent();
        blockList.getTimeBlockList().add(block);
        int num = blockList.getTimeBlockList().size() + 1;
        String name = getDate() + "." + num;
        block.setName(name);
        block.setEvent(event);
        block.setStartingTime(fieldST.getText());
        block.setEndingTime(fieldET.getText());
//        System.out.println(fieldEvent.getText());
//        System.out.println(fieldST.getText());
//        System.out.println(fieldET.getText());

        textArea.setText(displayTimeBlocks());
        comboBox.addItem(block);
    }

    // MODIFIES: this
    // EFFECTS: add a new event to the eventList, if the event
    //          doesn't already exist
    private Event addNewEvent() {
        String eventName = fieldEvent.getText();

        for (Event event : eventList.getEventList()) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }

        Event newEvent = new Event(eventName, eventList.getEventList().size() + 1);
        try {
            eventList.addEvent(newEvent);
        } catch (EventNameIsNullException e) {
            System.out.println("Event name is null.");
        }
        return newEvent;
    }

    // EFFECTS: displays time blocks on scroll pane
    private String displayTimeBlocks() {
        String display = "";
        for (TimeBlock block : blockList.getTimeBlockList()) {
            display = display + block.toString() + "\n";
        }
        return display;
    }

    // EFFECTS: returns the date of time block
    private String getDate() {
        String date = blockList.getName().substring(5, 6) + blockList.getName().substring(8, 9);
        return date;
    }

    // EFFECTS: plays a sound indicating the record of time block is complete
    private void playRecordSound() {
        try {
            InputStream in = new FileInputStream("./data/recordSound.au");
            AudioStream as = new AudioStream(in);
            AudioPlayer.player.start(as);
            //AudioPlayer.player.stop(as);
        } catch (Exception e) {
            System.out.println("File not found.");
        }
    }

    // MODIFIES: this
    // EFFECT: delete an existing time block from the list
    private void deleteTimeBlock(JPanel panel) {
        JLabel label = new JLabel("Delete time block:");
        label.setLocation(400, 235);
        label.setSize(120, 15);

        this.timeBlockList(panel);   // scroll pane of list of time blocks
        this.btnDelete(panel);       // delete the chosen time block

        panel.add(label);
    }

    // MODIFIES: this
    // EFFECTS: displays a list of time blocks to the user
    private void timeBlockList(JPanel panel) {
        comboBox.setBounds(400, 255, 150, 20);
        panel.add(comboBox);
    }

    // MODIFIES: this
    // EFFECTS: deletes the time block that has been chosen in the combo box
    private void btnDelete(JPanel panel) {
        JButton btn = new JButton("Delete");
        btn.setBounds(435, 285, 60, 20);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object timeBlock = comboBox.getSelectedItem();
                blockList.getTimeBlockList().remove(timeBlock);
                comboBox.removeItem(timeBlock);
                textArea.setText(displayTimeBlocks());
            }
        });

        panel.add(btn);
    }

    // MODIFIES: this
    // EFFECTS: saves data to file
    private void btnSave(JPanel panel) {
        JButton btn = new JButton("Save");
        btn.setBounds(68, 320, 50, 20);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTimeBlockList();
                saveEventList();
            }
        });

        panel.add(btn);
    }

    // MODIFIES: this
    // EFFECTS: saves the time blocks to file
    private void saveTimeBlockList() {
        try {
            jsonWriterTBL.open();
            jsonWriterTBL.write(blockList);
            jsonWriterTBL.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_TBL);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the events to file
    private void saveEventList() {
        try {
            jsonWriterEL.open();
            jsonWriterEL.write(eventList);
            jsonWriterEL.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_EL);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data from file
    private void btnLoad(JPanel panel) {
        JButton btn = new JButton("Load");
        btn.setBounds(133, 320, 50, 20);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTimeBlockList();
                loadEventList();

                textArea.setText(displayTimeBlocks());
                for (TimeBlock timeBlock : blockList.getTimeBlockList()) {
                    comboBox.addItem(timeBlock);
                }
            }
        });

        panel.add(btn);
    }

    // MODIFIES: this
    // EFFECTS: loads time blocks from file
    private void loadTimeBlockList() {
        try {
            blockList = jsonReaderTBL.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_TBL);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads events from file
    private void loadEventList() {
        try {
            eventList = jsonReaderEL.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_EL);
        }
    }

}
