package simon.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simon.storage.Storage;
import simon.task.Task;

/**
 * Represents a list of tasks and provides methods to manipulate them.
 * The class interacts with a {@link Storage} instance to persist changes.
 * Each tasklist can be modified with add, mark, and delete operations.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    private final Storage storage;

    /**
     * Constructor for TaskList.
     * Loads existing tasks from the provided Storage instance.
     *
     * @param storage Storage instance for persisting tasks.
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = new ArrayList<>();
        this.tasks.addAll(storage.loadTasks());
    }

    /**
     * Returns the list of tasks.
     *
     * @return List of tasks.
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    /**
     * Adds a new task to the list and persists the change.
     *
     * @param item Task to be added.
     */
    public void add(Task item) {
        this.tasks.add(item);
        persist();
    }

    /**
     * Marks a task as completed or not completed and persists the change.
     *
     * @param num       Task number (1-based index).
     * @param completed True to mark as completed, false to mark as not completed.
     * @return The updated Task.
     */
    public Task mark(int num, boolean completed) {
        Task toMark = this.tasks.get(num - 1);
        toMark.setCompleted(completed);
        persist();
        return toMark;
    }

    /**
     * Deletes a task from the list and persists the change.
     *
     * @param num Task number (1-based index).
     * @return The deleted Task.
     */
    public Task delete(int num) {
        Task toDelete = this.tasks.remove(num - 1);
        persist();
        return toDelete;
    }

    /**
     * Finds tasks that contain the specified keyword.
     *
     * @param query The keyword to search for in task titles.
     * @return A list of tasks that match the keyword.
     */
    public List<Task> find(String query) {
        List<Task> matchingTasks = new ArrayList<>(); // a copy, safe encapsulation
        for (Task task : this.tasks) {
            if (task.getTitle().contains(query)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Persists the current list of tasks to storage.
     * Catches and logs any exceptions that occur during the save operation.
     */
    private void persist() {
        try {
            storage.saveTasks(this.tasks);
        } catch (Exception e) {
            System.err.println("Unexpected error while saving tasks: " + e.getMessage());
        }
    }
}
