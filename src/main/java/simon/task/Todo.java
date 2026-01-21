package simon.task;

public class Todo extends Task {

    public Todo(String title) {
        super(title);
    }

    public Todo(String title, boolean isCompleted) {
        super(title, isCompleted);
    }

    @Override
    public String toDataString() {
        return "T | " + (isCompleted() ? "1" : "0") + " | " + super.getTitle();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
