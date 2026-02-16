package simon.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import simon.task.Task;

/**
 * Represents Storage, persists and loads {@link Task} objects to and from a simple
 * text file. Each task is represented as a single line using the task's
 * {@code toDataString()} / {@code fromDataString()} format.
 * <p>
 * Instances manage a single file path. The constructor ensures the file and its
 * parent directories exist (and creates them if necessary).
 */
public class Storage {

    private static final String SKIPPING_CORRUPTED_LINE = "Skipping corrupted line: ";
    private final String filePath;

    /**
     * Creates a Storage object that reads from and writes to the given file path.
     * The constructor will ensure the parent directory and the file exist.
     *
     * @param filePath path to the data file (relative or absolute)
     */
    public Storage(String filePath) {
        Objects.requireNonNull(filePath, "filePath must not be null");

        this.filePath = filePath;
        ensureFileExists();
    }

    /**
     * Ensures the parent directory and the data file exist else create them.
     */
    private void ensureFileExists() {
        assert filePath != null;

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
     * Loads tasks from the configured data file.
     * <p>
     * The method reads the file line-by-line and converts each line.
     * To a {@link Task} using {@code Task.fromDataString(line)}.
     *
     * @return a non-null {@link ArrayList} of tasks (possibly empty)
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = tryParseTaskLine(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    private Task tryParseTaskLine(String line) {
        try {
            return Task.fromDataString(line);
        } catch (IllegalArgumentException e) {
            System.out.println(SKIPPING_CORRUPTED_LINE + line);
            return null;
        }
    }

    /**
     * Persists the provided task list to the configured data file.
     * The file is overwritten. Each task is written using {@code task.toDataString()}.
     * I/O errors are logged to standard output.
     *
     * @param tasks the tasks to save; if null, a NullPointerException may be thrown
     *              by the underlying code
     */
    public void saveTasks(ArrayList<Task> tasks) {
        for (Task t : tasks) {
            Objects.requireNonNull(t, "task list contains null element");
        }

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
