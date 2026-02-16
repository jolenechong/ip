package simon.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simon.exception.InputErrorType;
import simon.exception.InputFormatException;
import simon.storage.Storage;
import simon.task.Deadline;
import simon.task.Event;
import simon.task.Task;

/**
 * Represents a list of tasks and provides methods to manipulate them.
 * The class interacts with a {@link Storage} instance to persist changes.
 * Each tasklist can be modified with add, mark, and delete operations.
 */
public class TaskList {
    private static final int BASE_INDEX = 1;

    private final ArrayList<Task> tasks;
    private final Storage storage;

    /**
     * Constructs TaskList which loads existing tasks from the provided Storage instance.
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
     * Checks if the task list is empty.
     *
     * @return true if the task list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Returns the size of the task list.
     *
     * @return The number of tasks in the list.
     */
    public int size() {
        return this.tasks.size();
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
     * Adds a task back to the list at the specified index and persists the change.
     * Used for undoing a delete operation.
     *
     * @param removed The Task to be re-added.
     * @param index   The index (1-based) where the task should be added.
     */
    public void add(Task removed, int index) {
        this.tasks.add(index - BASE_INDEX, removed);
        persist();
    }

    /**
     * Marks a task as completed or not completed and persists the change.
     *
     * @param num       Task number (1-based index).
     * @param isCompleted True to mark as completed, false to mark as not completed.
     * @return The updated Task.
     */
    public Task mark(int num, boolean isCompleted) throws InputFormatException {
        if (num < 1 || num > tasks.size()) {
            throw new InputFormatException(InputErrorType.NUMBER_RANGE);
        }

        Task toMark = this.tasks.get(num - 1);
        toMark.setCompleted(isCompleted);

        persist();
        return toMark;
    }

    /**
     * Deletes a task from the list and persists the change.
     *
     * @param num Task number (1-based index).
     * @return The deleted Task.
     */
    public Task delete(int num) throws InputFormatException {
        if (num < 1 || num > tasks.size()) {
            throw new InputFormatException(InputErrorType.NUMBER_RANGE);
        }

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
     * Finds tasks that occur on the specified date.
     *
     * @param date The date to search for tasks (time component is ignored).
     * @return A list of tasks that occur on the specified date.
     */
    public List<Task> on(LocalDateTime date) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task instanceof Deadline deadline) {
                if (deadline.getBy().toLocalDate().equals(date.toLocalDate())) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event event) {
                if (event.getFrom().toLocalDate().equals(date.toLocalDate())
                        || event.getTo().toLocalDate().equals(date.toLocalDate())) {
                    matchingTasks.add(task);
                }
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
