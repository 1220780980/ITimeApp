package ui;

import model.*;
import persistence.JsonReaderEL;
import persistence.JsonReaderTBL;
import persistence.JsonWriterEL;
import persistence.JsonWriterTBL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * iTime application
 * Citation: code obtained from JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class ITimeApp {
    private static final String JSON_STORE_TBL = "./data/TimeBlockList.json";
    private static final String JSON_STORE_EL = "./data/EventList.json";
    private EventList eventList;
    private TimeBlockList blockList;
    private Scanner input;
    private JsonWriterTBL jsonWriterTBL;
    private JsonReaderTBL jsonReaderTBL;
    private JsonWriterEL jsonWriterEL;
    private JsonReaderEL jsonReaderEL;

    // EFFECTS: runs the iTime application
    public ITimeApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriterTBL = new JsonWriterTBL(JSON_STORE_TBL);
        jsonReaderTBL = new JsonReaderTBL(JSON_STORE_TBL);
        jsonWriterEL = new JsonWriterEL(JSON_STORE_EL);
        jsonReaderEL = new JsonReaderEL(JSON_STORE_EL);

        runITime();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runITime() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("7")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("You are done your recording for the day! Congrats!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("s")) {
            saveTimeBlockList();
            saveEventList();
        } else if (command.equals("l")) {
            loadTimeBlockList();
            loadEventList();
        } else if (command.equals("1")) {
            addNewEvent();
        } else if (command.equals("2")) {
            deleteEvent();
        } else if (command.equals("3")) {
            viewEvents();
        } else if (command.equals("4")) {
            addNewTimeBlock();
        } else if (command.equals("5")) {
            deleteTimeBlock();
        } else if (command.equals("6")) {
            viewRecordedTimeBlock();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes evenList and blockList, and the iTime system
    private void init() {
        eventList = new EventList();
        blockList = new TimeBlockList("2002/01/01");
        input = new Scanner(System.in);

        setDate();
    }

    // MODIFIES: this
    // EFFECTS: sets the date for blockList
    private void setDate() {
        System.out.println("Enter today's date (yyyy/mm/dd): ");
        String date = input.next();
        if (date.length() != 10) {
            System.out.println("Invalid format, please enter again");
            setDate();
        } else {
            blockList.setName(date);
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> save time blocks/events to file");
        System.out.println("\tl -> load time blocks/events from file");
        System.out.println("\t1 -> add a new category of event");
        System.out.println("\t2 -> delete an existing category of event");
        System.out.println("\t3 -> view all categories of events");
        System.out.println("\t4 -> record a new time block");
        System.out.println("\t5 -> delete an existing time block");
        System.out.println("\t6 -> view all recorded time blocks");
        System.out.println("\t7 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds a new event to eventList
    private void addNewEvent() {
        System.out.println("Enter your new event name: ");
        input.nextLine();
        String eventName = input.nextLine();
        int eventNum = eventList.getEventList().size() + 1;
        Event event = new Event(eventName, eventNum);

        try {
            String result = eventList.addEvent(event);
            System.out.println(result);
        } catch (EventNameIsNullException e) {
            System.out.println("Please enter the event name.");
        }

    }

    // MODIFIES: this
    // EFFECTS: deletes the corresponding event from eventList
    private void deleteEvent() {
        System.out.println("Which event do you want to delete? Choose from the list ");
        int i = 1;
        for (Event event : eventList.getEventList()) {
            int num = i - 1;
            String name = eventList.getEventList().get(num).getName();

            System.out.println(i + " -> " + name);
            i++;
        }

        try {
            int choice = input.nextInt();
            eventList.getEventList().remove(choice - 1);
            changeEventNumber();
            System.out.println("Event deleted.");
        } catch (Exception e) {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes event number to the corresponding value
    private void changeEventNumber() {
        if (!eventList.getEventList().isEmpty()) {
            for (int i = 0; i < eventList.getEventList().size(); i++) {
                eventList.getEventList().get(i).setNum(i + 1);
            }
        }
    }

    // EFFECTS: displays all the categories of events
    private void viewEvents() {
        System.out.println("Here are the list of events you have added: ");
        printEvents();
    }

    // EFFECTS: prints all the categories of events
    private void printEvents() {
        for (Event event : eventList.getEventList()) {
            String name = event.getName();
            int number = event.getNum();
            System.out.println(number + ": " + name);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new time block to blockList
    private void addNewTimeBlock() {
        System.out.println("\t1 -> choose from an existing event");
        System.out.println("\t2 -> add a new event");
        System.out.println("\t3 -> quit");
        String choice = input.next();

        processCommandAddNewTB(choice);
    }

    // MODIFIES: this
    // EFFECTS: processes user command for adding a new time block
    private void processCommandAddNewTB(String command) {
        if (command.equals("1")) {
            printEvents();
            System.out.println("Enter a number to choose an event: ");
            int choice = input.nextInt();
            createNewTimeBlock(eventList.getEventList().get(choice - 1));
        } else if (command.equals("2")) {
            addNewEvent();
            int num = eventList.getEventList().size();
            createNewTimeBlock(eventList.getEventList().get(num - 1));
        } else if (command.equals("3")) {
            System.out.println("Quit successfully.");
        } else  {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new time block based on the event created
    private void createNewTimeBlock(Event event) {
        TimeBlock timeBlock = new TimeBlock();
        blockList.getTimeBlockList().add(timeBlock);
        int num = blockList.getTimeBlockList().size() + 1;
        String name = getDate() + "." + num;
        timeBlock.setName(name);
        timeBlock.setEvent(event);

        System.out.println("Enter starting time of this time block (xx:xx): ");
        String startingTime = input.next();
        timeBlock.setStartingTime(startingTime);
        System.out.println("Enter ending time of this time block (xx:xx): ");
        String endingTime = input.next();
        timeBlock.setEndingTime(endingTime);

        printRecordedTimeBlock(blockList.getTimeBlockList().size() - 1);
        System.out.println("Congrats! You have recorded your time!");
    }

    // EFFECTS: prints the most recently recorded time block
    private void printRecordedTimeBlock(int num) {
        String event = blockList.getTimeBlockList().get(num).getEvent().getName();
        String startingTime = blockList.getTimeBlockList().get(num).getStartingTime();
        String endingTime = blockList.getTimeBlockList().get(num).getEndingTime();

        System.out.println(event + ": " + startingTime + " - " + endingTime);
    }

    // EFFECTS: returns the date of time block
    private String getDate() {
        String date = blockList.getName().substring(5, 6) + blockList.getName().substring(8, 9);
        return date;
    }


    // MODIFIES: this
    // EFFECTS: deletes the corresponding time block from blockList
    private void deleteTimeBlock() {
        System.out.println("Which time block do you want to delete? Choose from the list: ");
        int i = 1;
        for (TimeBlock timeBlock : blockList.getTimeBlockList()) {
            int num = i - 1;
            String event = blockList.getTimeBlockList().get(num).getEvent().getName();
            String startingTime = blockList.getTimeBlockList().get(num).getStartingTime();
            String endingTime = blockList.getTimeBlockList().get(num).getEndingTime();

            System.out.println(i + " -> " + event + ": " + startingTime + " - " + endingTime);
            i++;
        }

        try {
            int choice = input.nextInt();
            blockList.getTimeBlockList().remove(choice - 1);
            System.out.println("Time block deleted.");
        } catch (Exception e) {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays all the recorded time blocks
    private void viewRecordedTimeBlock() {
        System.out.println("Here are the list of time blocks you have recorded: ");
        int i = 0;
        for (TimeBlock timeBlock : blockList.getTimeBlockList()) {
            printRecordedTimeBlock(i);
            i++;
        }
    }

    // EFFECTS: saves the time blocks to file
    private void saveTimeBlockList() {
        try {
            jsonWriterTBL.open();
            jsonWriterTBL.write(blockList);
            jsonWriterTBL.close();
            System.out.println("Saved " + blockList.getName() + " to " + JSON_STORE_TBL);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_TBL);
        }
    }

    // EFFECTS: saves the events to file
    private void saveEventList() {
        try {
            jsonWriterEL.open();
            jsonWriterEL.write(eventList);
            jsonWriterEL.close();
            System.out.println("Saved event list to " + JSON_STORE_EL);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_EL);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads time blocks from file
    private void loadTimeBlockList() {
        try {
            blockList = jsonReaderTBL.read();
            System.out.println("Loaded " + blockList.getName() + " from " + JSON_STORE_TBL);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_TBL);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads events from file
    private void loadEventList() {
        try {
            eventList = jsonReaderEL.read();
            System.out.println("Loaded event list from " + JSON_STORE_EL);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_EL);
        }
    }


}
