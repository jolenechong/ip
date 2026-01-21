package simon.storage;

import simon.task.Task;

import java.io.*;
import java.util.ArrayList;

/**
 * Responsible for persisting and loading {@link Task} objects to and from a simple
 * text file. Each task is represented as a single line using the task's
 * {@code toDataString()} / {@code fromDataString()} format.
 * <p>
 * Instances manage a single file path. The constructor ensures the file and its
 * parent directories exist (and creates them if necessary).
 */
public class Storage {

    private final String filePath;

    /**
     * Create a Storage object that reads from and writes to the given file path.
     * The constructor will ensure the parent directory and the file exist.
     *
     * @param filePath path to the data file (relative or absolute)
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    /**
     * Ensure the parent directory and the data file exist else create them.
     */
    private void ensureFileExists() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating data file: " + e.getMessage());
        }
    }

    /**
     * Load tasks from the configured data file.
     * <p>
     * The method reads the file line-by-line and converts each line to a
     * {@link Task} using {@code Task.fromDataString(line)}. Malformed lines are
     * skipped with a message; I/O errors are logged and result in an empty list
     * being returned if nothing could be read.
     *
     * @return a non-null {@link ArrayList} of tasks (possibly empty)
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.fromDataString(line);
                    tasks.add(task);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Persist the provided task list to the configured data file.
     * <p>
     * The file is overwritten. Each task is written using {@code task.toDataString()}
     * on its own line. I/O errors are logged to standard output.
     *
     * @param tasks the tasks to save; if null, a NullPointerException may be thrown
     *              by the underlying code
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
