import java.util.*;
import java.io.*;

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
        return "[" + getTaskType() + "][" + (isDone ? "X" : " ") + "] " + description.trim();
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

class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }
}

class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<String> load() {
        List<String> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return tasks;
    }

    public void save(List<Task> tasks) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

class Parser {
    public static String[] parse(String input) {
        return input.split(" ", 2);
    }
}

class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void markTask(int index, boolean isDone) {
        if (isDone) {
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).unmarkAsDone();
        }
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i).toString());
            }
        }
    }

    public int getSize() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return tasks;
    }
}

public class Lys {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    public Lys() {
        ui = new Ui();
        storage = new Storage("data/tasks.txt");
        tasks = new TaskList();

        List<String> loadedTasks = storage.load();
        for (String taskDescription : loadedTasks) {
            if (taskDescription.startsWith("[T]")) {
                tasks.addTask(new ToDo(taskDescription.substring(6))); // Remove prefix
            } else if (taskDescription.startsWith("[D]")) {
                String[] parts = taskDescription.substring(6).split(" \\(by: ");
                tasks.addTask(new Deadline(parts[0], parts[1].replace(")", "")));
            } else if (taskDescription.startsWith("[E]")) {
                String[] parts = taskDescription.substring(6).split(" \\(from: | to: ");
                tasks.addTask(new Event(parts[0], parts[1], parts[2].replace(")", "")));
            }
        }
    }

    public void run() {
        ui.showMessage("Hello! I'm Lys.\nWhat can I do for you?");
        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();
            String[] parsedInput = Parser.parse(input);
            String command = parsedInput[0].toLowerCase();

            try {
                switch (command) {
                    case "bye":
                        ui.showMessage("Bye. Hope to see you again soon!");
                        isRunning = false;
                        break;

                    case "list":
                        ui.showLine();
                        tasks.listTasks();
                        ui.showLine();
                        break;

                    case "mark":
                    case "unmark": {
                        int index = Integer.parseInt(parsedInput[1]) - 1;
                        if (index < 0 || index >= tasks.getSize()) {
                            throw new IndexOutOfBoundsException("Invalid task number.");
                        }
                        tasks.markTask(index, command.equals("mark"));
                        storage.save(tasks.getTasks());
                        if (command.equals("mark")) {
                            ui.showMessage("Nice! I've marked this task as done:\n  " + tasks.getTasks().get(index));
                        } else {
                            ui.showMessage("OK, I've marked this task as not done yet:\n  " + tasks.getTasks().get(index));
                        }
                        break;
                    }

                    case "todo":
                        tasks.addTask(new ToDo(parsedInput[1]));
                        storage.save(tasks.getTasks());  // Ensure it's saved
                        ui.showMessage("Got it. I've added this task:\n  " + tasks.getTasks().get(tasks.getSize() - 1) +
                                "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        break;

                    case "deadline":
                        if (parsedInput.length < 2 || !parsedInput[1].contains(" /by ")) {
                            throw new IllegalArgumentException("Deadline format should be: deadline [task] /by [date]");
                        }

                        String[] deadlineParts = parsedInput[1].split(" /by ", 2);
                        tasks.addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
                        storage.save(tasks.getTasks());

                        ui.showMessage("Got it. I've added this task:\n  " + tasks.getTasks().get(tasks.getSize() - 1) +
                                "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        break;

                    case "event":
                        if (parsedInput.length < 2 || !parsedInput[1].contains(" /from ") || !parsedInput[1].contains(" /to ")) {
                            throw new IllegalArgumentException("Event format should be: event [task] /from [start] /to [end]");
                        }

                        String[] eventParts = parsedInput[1].split(" /from ", 2);
                        String description = eventParts[0];

                        String[] timeParts = eventParts[1].split(" /to ", 2);
                        String from = timeParts[0];
                        String to = timeParts[1];

                        tasks.addTask(new Event(description, from, to));
                        storage.save(tasks.getTasks());

                        ui.showMessage("Got it. I've added this task:\n  " + tasks.getTasks().get(tasks.getSize() - 1) +
                                "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        break;

                    case "delete":
                        int index = Integer.parseInt(parsedInput[1]) - 1;
                        if (index < 0 || index >= tasks.getSize()) {
                            throw new IndexOutOfBoundsException("Invalid task number.");
                        }
                        Task removedTask = tasks.getTasks().get(index);
                        tasks.deleteTask(index);
                        storage.save(tasks.getTasks());
                        ui.showMessage("Noted. I've removed this task:\n  " + removedTask + "\nNow you have " + tasks.getSize() + " tasks in the list.");
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown command.");
                }
            } catch (Exception e) {
                ui.showMessage("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Lys().run();
    }
}