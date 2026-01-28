package simon.task;

import simon.util.DateParser;

import java.time.LocalDateTime;

/**
 * Represents a deadline task with a title and a due date/time.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructs a Deadline task with the specified title and due date/time.
     *
     * @param title The title of the deadline task.
     * @param by    The due date/time of the deadline task.
     */
    public Deadline(String title, LocalDateTime by) {
        super(title);
        this.by = by;
    }

    /**
     * Constructs a Deadline task with the specified title and due date/time as a string.
     *
     * @param title The title of the deadline task.
     * @param by    The due date/time of the deadline task as a string.
     */
    public Deadline(String title, String by) {
        this(title, DateParser.parse(by));
    }

    /**
     * Constructs a Deadline task with the specified title, due date/time, and completion status.
     *
     * @param title The title of the deadline task.
     * @param by  The due date/time of the deadline task.
     * @param isCompleted The completion status of the deadline task.
     */
    public Deadline(String title, LocalDateTime by, boolean isCompleted) {
        super(title);
        this.by = by;
        super.setCompleted(isCompleted);
    }

    /**
     * Constructs a Deadline task with the specified title, due date/time as a string, and completion status.
     *
     * @param title The title of the deadline task.
     * @param by  The due date/time of the deadline task as a string.
     * @param isCompleted The completion status of the deadline task.
     */
    public Deadline(String title, String by, boolean isCompleted) {
        this(title, DateParser.parse(by), isCompleted);
    }

    /**
     * Gets the due date/time of the deadline task.
     *
     * @return The due date/time of the deadline task.
     */
    public LocalDateTime getBy() {
        return this.by;
    }

    @Override
    public String toDataString() {
        // format date to  2nd of December 2019, 6pm

        return "D | " + (isCompleted() ? "1" : "0") + " | " + super.getTitle() + " | " + this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateParser.format(this.by) + ")";
    }
}
