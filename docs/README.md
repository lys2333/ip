# Welcome to Lys - Your Personal Task Management Chatbot!  
This guide will help you understand how to use **Lys** effectively.

---

## **Key Features**  
Lys allows you to:
- ✅ Add different types of tasks (**ToDo, Deadline, Event**).  
- 📋 List all tasks.  
- ✔️ Mark tasks as **done** or **not done**.  
- ❌ Delete tasks.  
- 🔍 Find tasks by keywords.  

---

## **Command List**  

### **1️⃣ Adding Tasks**  

#### **➤ Adding a ToDo**  
- **Command:**  
todo TASK

- **Example:**  
todo Assignment

- **Output:**  
Got it. I've added this task: [T][ ] Assignment Now you have 1 task in the list.

#### **➤ Adding a Deadline**  
- **Command:**  
deadline TASK /by DEADLINE

- **Example:**  
deadline Quiz /by Sunday 2359

- **Output:**  
Got it. I've added this task: [D][ ] Quiz (by: Sunday 2359) Now you have 2 tasks in the list.

#### **➤ Adding an Event**  
- **Command:**  
event TASK /from START_TIME /to END_TIME

- **Example:**  
event Lecture /from Friday 1600 /to 1800

- **Output:**  
Got it. I've added this task: [E][ ] Lecture (from: Friday 1600 to: 1800) Now you have 3 tasks in the list.

---

### **2️⃣ Deleting Tasks**  
- **Command:**  
delete INDEX

- **Example:**  
delete 3

- **Output:**  
Noted. I've removed this task: [E][ ] Lecture (from: Friday 1600 to: 1800) Now you have 2 tasks in the list.

---

### **3️⃣ Marking / Unmarking Tasks**  

#### **➤ Marking a Task as Done**  
- **Command:**  
mark INDEX

- **Example:**  
mark 2

- **Output:**  
Nice! I've marked this task as done: [D][X] Quiz (by: Sunday 2359)

#### **➤ Marking a Task as Not Done**  
- **Command:**  
unmark INDEX

- **Example:**  
unmark 2

**output:**  
OK, I've marked this task as not done yet: [D][ ] Quiz (by: Sunday 2359)

---

### **4️⃣ Finding Tasks**  
- **Command:**  
find TASK

- **Example:**  
find Quiz

- **Output:**  
Here are the matching tasks in your list:

[D][ ] Quiz (by: Sunday 2359)

---

### **5️⃣ Viewing the Task List**  
- **Command:**  
list

- **Example:**  
list

- **Output:**  
Here are the tasks in your list:

[T][ ] Assignment
[D][ ] Quiz (by: Sunday 2359)

---

### **6️⃣ Exiting the Program**  
- **Command:**  
bye

- **Example:**  
bye

- **Output:**  
Bye. Hope to see you again soon! Process finished with exit code 0
