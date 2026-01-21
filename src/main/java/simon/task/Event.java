package simon.task;

import simon.util.DateParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String title, LocalDateTime from, LocalDateTime to) {
        super(title);

        this.from = from;
        this.to = to;
    }

    public Event(String title, String from, String to) {
        this(title, DateParser.parse(from), DateParser.parse(to));
    }

    public Event(String title, LocalDateTime from, LocalDateTime to, boolean isCompleted) {
        super(title);
        this.from = from;
        this.to = to;
        super.setCompleted(isCompleted);
    }

    public Event(String title, String from, String to, boolean isCompleted) {
        this(title, DateParser.parse(from), DateParser.parse(to), isCompleted);
    }

    public LocalDateTime getFrom() {
        return this.from;
    }

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
