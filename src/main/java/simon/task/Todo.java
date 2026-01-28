package simon.task;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {

    /**
     * Constructor for Todo task.
     *
     * @param title Title of the todo task.
     */
    public Todo(String title) {
        super(title);
    }

    /**
     * Constructor for Todo task with completion status.
     *
     * @param title Title of the todo task.
     * @param isCompleted Completion status of the todo task.
     */
    public Todo(String title, boolean isCompleted) {
        super(title, isCompleted);
    }

    /**
     * Returns the string representation of the Todo task for data storage.
     *
     * @return Formatted string for data storage.
     */
    @Override
    public String toDataString() {
        return "T | " + (isCompleted() ? "1" : "0") + " | " + super.getTitle();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
