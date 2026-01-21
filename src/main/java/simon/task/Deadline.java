package simon.task;

public class Deadline extends Task {
    private String by;

    public Deadline(String title, String by) {
        super(title);
        this.by = by;
    }

    public Deadline(String title, String by, boolean isCompleted) {
        super(title);
        this.by = by;
        super.setCompleted(isCompleted);
    }

    @Override
    public String toDataString() {
        return "D | " + (isCompleted() ? "1" : "0") + " | " + super.getTitle() + " | " + this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
