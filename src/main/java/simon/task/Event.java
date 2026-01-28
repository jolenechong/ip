package simon.task;

import simon.util.DateParser;

import java.time.LocalDateTime;

/**
 * Represents an event task with a title, start time, and end time.
 */
public class Event extends Task {

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates an Event task.
     *
     * @param title the title of the event.
     * @param from the start time of the event.
     * @param to the end time of the event.
     */
    public Event(String title, LocalDateTime from, LocalDateTime to) {
        super(title);

        this.from = from;
        this.to = to;
    }

    /**
     * Creates an Event task.
     *
     * @param title the title of the event.
     * @param from the start time of the event in string format.
     * @param to the end time of the event in string format.
     */
    public Event(String title, String from, String to) {
        this(title, DateParser.parse(from), DateParser.parse(to));
    }

    /**
     * Creates an Event task.
     *
     * @param title the title of the event.
     * @param from the start time of the event.
     * @param to the end time of the event.
     * @param isCompleted whether the event is completed.
     */
    public Event(String title, LocalDateTime from, LocalDateTime to, boolean isCompleted) {
        super(title);
        this.from = from;
        this.to = to;
        super.setCompleted(isCompleted);
    }

    /**
     * Creates an Event task.
     *
     * @param title the title of the event.
     * @param from the start time of the event in string format.
     * @param to the end time of the event in string format.
     * @param isCompleted whether the event is completed.
     */
    public Event(String title, String from, String to, boolean isCompleted) {
        this(title, DateParser.parse(from), DateParser.parse(to), isCompleted);
    }

    /**
     * Gets the start time of the event.
     *
     * @return the start time.
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Gets the end time of the event.
     *
     * @return the end time.
     */
    public LocalDateTime getTo() {
        return this.to;
    }

    @Override
    public String toDataString() {
        return "E | " + (isCompleted() ? "1" : "0") + " | " + super.getTitle() + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateParser.format(this.from) + ", to: " + DateParser.format(this.to) + ")";
    }
}
