## scheduler/set_timer exercise

### description of components

* CB.java - callback abstraction, it has a field called `name`, and a function `f` that will print its name to `stdout`
* OS.java - operative system abstraction, implements a Set_timer function with one memory slot;
* Task.java - convenient wrapper class to define a task being scheduled;
* Scheduler.java - implements custom Set_timer2 function that uses the OS's implementation as required in the exercise;
* Tests.java - unit tests to test the custom scheduler;
 
 