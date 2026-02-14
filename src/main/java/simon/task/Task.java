package simon.task;

/**
 * Represents a generic task with a title and completion status.
 */
public abstract class Task {
    private String title;
    private Boolean isCompleted;

    /**
     * Constructs Task class.
     *
     * @param title Title of the task.
     */
    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    /**
     * Constructs Task class with completion status.
     *
     * @param title Title of the task.
     * @param isCompleted Completion status of the task.
     */
    public Task(String title, Boolean isCompleted) {
        this.title = title;
        this.isCompleted = isCompleted;
    }

    /**
     * Converts task to data string for storage.
     *
     * @return Data string representation of the task.
     */
    public abstract String toDataString();

    /**
     * Recreates a Task object from a data string.
     *
     * @param line Data string representation of the task.
     * @return Task object.
     */
    public static Task fromDataString(String line) {
        assert line != null : "Line should not be null";
        assert !line.isEmpty() : "Line should not be empty";

        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid data string");
        }

        String type = parts[0];
        boolean isCompleted = parts[1].equals("1");
        String title = parts[2];

        switch (type) {
        case "T":
            return new Todo(title, isCompleted);
        case "D":
            if (parts.length < 4) {
                throw new IllegalArgumentException("Invalid data string for Deadline");
            }
            return new Deadline(title, parts[3], isCompleted);
        case "E":
            if (parts.length < 5) {
                throw new IllegalArgumentException("Invalid data string for Event");
            }
            return new Event(title, parts[3], parts[4], isCompleted);
        default:
            throw new IllegalArgumentException("Unknown task type");
        }
    }

    /**
     * Gets the title of the task.
     *
     * @return Title of the task.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Checks if the task is completed.
     *
     * @return True if the task is completed, false otherwise.
     */
    public Boolean isCompleted() {
        return this.isCompleted;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param isCompleted Completion status to set.
     */
    public void setCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        String completed = this.isCompleted ? "X" : " ";
        return "[" + completed + "] " + this.title;
    }
}
