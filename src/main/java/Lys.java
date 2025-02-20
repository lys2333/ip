import java.util.Scanner;

abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public abstract String getTaskType();

    @Override
    public String toString() {
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description;
    }
}

class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getTaskType() {
        return "T";
    }
}

class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}

class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

public class Lys {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm [YOUR CHATBOT NAME]");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();
            String[] words = input.split(" ", 2);
            String command = words[0].toLowerCase();

            try {
                switch (command) {
                    case "bye":
                        System.out.println("____________________________________________________________");
                        System.out.println("Bye. Hope to see you again soon!");
                        System.out.println("____________________________________________________________");
                        scanner.close();
                        return;

                    case "list":
                        System.out.println("____________________________________________________________");
                        if (taskCount == 0) {
                            System.out.println("Your task list is empty.");
                        } else {
                            System.out.println("Here are the tasks in your list:");
                            for (int i = 0; i < taskCount; i++) {
                                System.out.println((i + 1) + "." + tasks[i]);
                            }
                        }
                        System.out.println("____________________________________________________________");
                        break;

                    case "mark":
                    case "unmark":
                        int index = Integer.parseInt(words[1]) - 1;
                        if (index < 0 || index >= taskCount) {
                            throw new IndexOutOfBoundsException("Invalid task number.");
                        }
                        if (command.equals("mark")) {
                            tasks[index].markAsDone();
                            System.out.println("____________________________________________________________");
                            System.out.println("Nice! I've marked this task as done:");
                        } else {
                            tasks[index].unmarkAsDone();
                            System.out.println("____________________________________________________________");
                            System.out.println("OK, I've marked this task as not done yet:");
                        }
                        System.out.println("  " + tasks[index]);
                        System.out.println("____________________________________________________________");
                        break;

                    case "todo":
                        if (words.length < 2 || words[1].trim().isEmpty()) {
                            throw new IllegalArgumentException("The description of a todo cannot be empty.");
                        }
                        tasks[taskCount++] = new ToDo(words[1]);
                        break;

                    case "deadline":
                        if (words.length < 2 || !words[1].contains(" /by ")) {
                            throw new IllegalArgumentException("Deadline must be in format: deadline [task] /by [date].");
                        }
                        String[] deadlineParts = words[1].split(" /by ", 2);
                        tasks[taskCount++] = new Deadline(deadlineParts[0], deadlineParts[1]);
                        break;

                    case "event":
                        if (words.length < 2 || !words[1].contains(" /from ") || !words[1].contains(" /to ")) {
                            throw new IllegalArgumentException("Event must be in format: event [task] /from [start] /to [end].");
                        }
                        String[] eventParts = words[1].split(" /from | /to ");
                        if (eventParts.length < 3) {
                            throw new IllegalArgumentException("Invalid event format.");
                        }
                        tasks[taskCount++] = new Event(eventParts[0], eventParts[1], eventParts[2]);
                        break;

                    default:
                        throw new IllegalArgumentException("Sorry, I don't understand that command.");
                }
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } catch (NumberFormatException e) {
                System.out.println("____________________________________________________________");
                System.out.println("Please enter a valid task number.");
                System.out.println("____________________________________________________________");
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("____________________________________________________________");
                System.out.println("Error: " + e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
    }
}
