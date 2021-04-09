# iTime

## Track your time, guide your life

iTime will allow you to create your own time recording "notebook".
By using iTime, you will become more aware of your everyday time usage, and eventually *spend
less time while completing more tasks*!

Nowadays, with all the prevailing technologies, people unconsciously spend too much of 
their time browsing webs or just playing with their phones, without thinking about the elapsing 
time. It seems that people are often side-tracked by unimportant things, not realizing that they 
are losing control of their time! 
Students and workers, especially those who are bothered by procrastination and often unable to 
finish their tasks in time, would see a great improvement in their time management if they spent
just **a few minutes every day to keep their daily activities up to date.** 
I have been tracking my time since the beginning of this year, but I haven't found an application or website that
suites me. Some applications are too fancy to be my "notebook", so I decided to create one that's succinct yet powerful.

The application will keep your recorded time blocks, and will provide you with the total "unrecorded time" of each day. 
It will allow the user to:
- keep track of categories of events that can be added to the time blocks
- add/delete recorded time blocks
- add categories of events
- view the recorded time blocks in a list

"The bad news is time flies. The good news is you're the pilot." Start today with iTime to plan your life better!


## User Stories

- As a user, I want to be able to add/delete a category of event 
- As a user, I want to be able to view the categories of events that can be added to the time slots
- As a user, I want to be able to add/delete blocks of recorded time
- As a user, I want to be able to see the recorded time blocks in a list
- As a user, I want to be able to save my recorded time blocks & events to file
- As a user, I want to be able to load my recorded time blocks & events from file


## Phase 4: Task 2

- Test and design a class in your model package that is robust.  You must have at least one method that throws 
  a checked exception.  You must have one test for the case where the exception is expected and another where 
  the exception is not expected.
  
 The class with a robust design is model.EventList class, the method that throws a checked exception is addEvent method.
 
 
## Phase 4: Task 3

If there was more time to work on the project, I would refactor my project so that TimeBlockList keeps EventList as its
field. After this change is implemented, I can remove the association between TimeBlock and Event class. I can also 
remove the implementation between Interface Writable and regular Event class. ITimeUI would therefore only need 
to be associated with TimeBlockList, as this list would contain information about the EventList. ITimeUI would no 
longer needs keep another field of EventList. This change would also allow me to remove the JsonReader and JsonWriter 
classes for the EventList, thus reducing the duplication of code in the persistence package.
 


