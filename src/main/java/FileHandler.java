import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private static final String FILE_PATH = "data/duke.txt";

    // Alternative method for Task[] array (Not recommended, but provided)
    public static void saveTasks(Task[] tasks, int taskCount) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs(); // Ensure directory exists

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < taskCount; i++) {
                writer.write(taskToFileFormat(tasks[i]));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return tasks; // Return empty list if file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    private static String taskToFileFormat(Task task) {
        String status = task.isDone ? "1" : "0";
        if (task instanceof ToDo) {
            return "T | " + status + " | " + task.description;
        } else if (task instanceof Deadline) {
            return "D | " + status + " | " + task.description + " | " + ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            return "E | " + status + " | " + task.description + " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo();
        }
        return "";
    }

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null; // Invalid format

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                task = new Event(description, parts[3], parts[4]);
                break;
        }
        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }
}
