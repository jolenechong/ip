package simon.task;

public class Event extends Task {

    private String from;
    private String to;

    public Event(String title, String from, String to) {
        super(title);

        this.from = from;
        this.to = to;
    }

    public Event(String title, String from, String to, boolean isCompleted) {
        super(title);

        this.from = from;
        this.to = to;
        super.setCompleted(isCompleted);
    }

    @Override
    public String toDataString() {
        return "E | " + (isCompleted() ? "1" : "0") + " | " + super.getTitle() + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + ", to: " + this.to + ")";
    }
}
