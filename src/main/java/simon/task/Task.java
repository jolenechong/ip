package simon.task;

/**
 * Represents a generic task with a title and completion status.
 */
public abstract class Task {

    private static final String DATA_DELIMITER = " \\| ";
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final String COMPLETED_FLAG = "1";
    private static final String DISPLAY_COMPLETED = "X";
    private static final String DISPLAY_INCOMPLETE = " ";

    private static final String ERROR_INVALID_DATA = "Invalid data string";
    private static final String ERROR_INVALID_DEADLINE = "Invalid data string for Deadline";
    private static final String ERROR_INVALID_EVENT = "Invalid data string for Event";
    private static final String ERROR_UNKNOWN_TYPE = "Unknown task type";

    private final String title;
    private boolean isCompleted;

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
    public Task(String title, boolean isCompleted) {
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

        String[] parts = line.split(DATA_DELIMITER);
        if (parts.length < 3) {
            throw new IllegalArgumentException(ERROR_INVALID_DATA);
        }

        String type = parts[0];
        boolean isCompleted = COMPLETED_FLAG.equals(parts[1]);
        String title = parts[2];

        return createTask(type, parts, title, isCompleted);
    }

    private static Task createTask(
            String type,
            String[] parts,
            String title,
            boolean isCompleted
    ) {
        return switch (type) {
        case TYPE_TODO -> new Todo(title, isCompleted);
        case TYPE_DEADLINE -> {
            requireArgLength(parts, 4, ERROR_INVALID_DEADLINE);
            yield new Deadline(title, parts[3], isCompleted);
        }
        case TYPE_EVENT -> {
            requireArgLength(parts, 4, ERROR_INVALID_EVENT);
            yield new Event(title, parts[3], parts[4], isCompleted);
        }
        default -> throw new IllegalArgumentException(ERROR_UNKNOWN_TYPE);
        };
    }

    private static void requireArgLength(String[] args, int expectedLength, String errorMessage) {
        if (args.length < expectedLength) {
            throw new IllegalArgumentException(errorMessage);
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
    public boolean isCompleted() {
        return this.isCompleted;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param isCompleted Completion status to set.
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        String completed = this.isCompleted ? DISPLAY_COMPLETED : DISPLAY_INCOMPLETE;
        return "[" + completed + "] " + this.title;
    }
}
