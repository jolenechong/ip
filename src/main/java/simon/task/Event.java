package simon.task;

import java.time.LocalDateTime;

import simon.util.DateParser;

/**
 * Represents an event task with a title, start time, and end time.
 */
public class Event extends Task {

    private static final String START_TIME_BEFORE_END_TIME_MESSAGE = "Hmm, start time must be before end time.";

    private final LocalDateTime fromDateTime;
    private final LocalDateTime toDateTime;

    /**
     * Creates an Event task.
     *
     * @param title the title of the event.
     * @param from the start time of the event.
     * @param to the end time of the event.
     */
    public Event(String title, LocalDateTime from, LocalDateTime to) {
        super(title);

        if (from.isAfter(to) || from.isEqual(to)) {
            throw new IllegalArgumentException(START_TIME_BEFORE_END_TIME_MESSAGE);
        }

        this.fromDateTime = from;
        this.toDateTime = to;
    }

    /**
     * Creates an Event task using string representations of the start and end times.
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
        this.fromDateTime = from;
        this.toDateTime = to;
        super.setCompleted(isCompleted);
    }

    /**
     * Creates an Event task using string representations of the start and end times.
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
        return this.fromDateTime;
    }

    /**
     * Gets the end time of the event.
     *
     * @return the end time.
     */
    public LocalDateTime getTo() {
        return this.toDateTime;
    }

    @Override
    public String toDataString() {
        return "E | " + (isCompleted() ? "1" : "0") + " | " + super.getTitle() + " | "
                + this.fromDateTime + " | " + this.toDateTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateParser.format(this.fromDateTime)
                + ", to: " + DateParser.format(this.toDateTime) + ")";
    }
}
