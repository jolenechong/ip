package simon.task;

import simon.util.DateParser;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String title, LocalDateTime by) {
        super(title);
        this.by = by;
    }

    public Deadline(String title, String by) {
        this(title, DateParser.parse(by));
    }

    public Deadline(String title, LocalDateTime by, boolean isCompleted) {
        super(title);
        this.by = by;
        super.setCompleted(isCompleted);
    }

    public Deadline(String title, String by, boolean isCompleted) {
        this(title, DateParser.parse(by), isCompleted);
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
