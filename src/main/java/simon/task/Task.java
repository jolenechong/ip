package simon.task;

abstract public class Task {
    private String title;
    private Boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public Boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        String completed = this.completed ? "X" : " ";
        return "[" + completed + "] " + this.title;
    }
}
