# **Lys User Guide**

Welcome to **Lys**, your personal task management chatbot! This guide will help you understand how to use Lys effectively.

## Key Features
Lys allows you to:
- Add different types of tasks (`ToDo`, `Deadline`, `Event`).
- List all tasks.
- Mark tasks as done or not done.
- Delete tasks.
- Find tasks by keywords.


## Command List
### **Adding Tasks**
1. Adding todos
    Command: todo TASK
    Adding a task of 'todo' type that contains the task name
    Example: todo Assignment
    ____________________________________________________________
    Got it. I've added this task:
    [T][ ] Assignment
    Now you have 1 tasks in the list.
    ____________________________________________________________

2. Adding deadlines
    Command: deadline TASK /by DEADLINE
    Adding a task of 'deadline' type that contains the task name and its deadline
    Example: deadline Quiz
    ____________________________________________________________
    Got it. I've added this task:
    [D][ ] Quiz (by: Sunday 2359)
    Now you have 2 tasks in the list.
    ____________________________________________________________

3. Adding events
    Command: event TASK /from START /to END
    Adding a task of 'event' type that contains the task name and its starting and ending times
    Example: event Lecture /from Friday 1600 /to 1800
    ____________________________________________________________
    Got it. I've added this task:
    [E][ ] Lecture (from: Friday 1600 to: 1800)
    Now you have 3 tasks in the list.
    ____________________________________________________________

### **Deleting Tasks**
Command: delete INDEX 
Deleting a task of INDEX from the list
Example: delete 3
____________________________________________________________
Noted. I've removed this task:
[E][ ] Lecture (from: Friday 1600 to: 1800)
Now you have 2 tasks in the list.
____________________________________________________________

### **Marking/Unmarking Tasks**
1. Marking as Done
    command: mark INDEX
    Marking a task of INDEX as completed
    Example: mark 2
    ____________________________________________________________
    Nice! I've marked this task as done:
    [D][X] Quiz (by: Sunday 2359)
    ____________________________________________________________

2. Marking as Not Done
    Command: unmark INDEX
    Marking a task of INDEX as not completed
    Example: unmark 2
    ____________________________________________________________
    OK, I've marked this task as not done yet:
    [D][ ] Quiz (by: Sunday 2359)
    ____________________________________________________________

### **Finding Tasks**
Command: find TASK
Finding a task that contains TASK
Example: find Quiz
____________________________________________________________
Here are the matching tasks in your list:
1.[D][ ] Quiz (by: Sunday 2359)
____________________________________________________________

### **Viewing Task List**
Command: list
Displaying all the tasks in the list
Example: list
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] Assignment
2.[D][ ] Quiz (by: Sunday 2359)
____________________________________________________________

### **Existing Programme**
Command: bye
Ending the programme (The task list created will be stored)
Example: bye
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________

Process finished with exit code 0


