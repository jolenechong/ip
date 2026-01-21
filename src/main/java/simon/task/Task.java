package simon.task;

abstract public class Task {
    private String title;
    private Boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public Task(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    abstract public String toDataString();

    public static Task fromDataString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid data string");
        }

        String type = parts[0];
        boolean isCompleted = parts[1].equals("1");
        String title = parts[2];

        switch (type) {
        case "T":
            return new Todo(title, isCompleted);
        case "D":
            if (parts.length < 4) {
                throw new IllegalArgumentException("Invalid data string for Deadline");
            }
            return new Deadline(title, parts[3], isCompleted);
        case "E":
            if (parts.length < 5) {
                throw new IllegalArgumentException("Invalid data string for Event");
            }
            return new Event(title, parts[3], parts[4], isCompleted);
        default:
            throw new IllegalArgumentException("Unknown task type");
        }
    }

    public String getTitle() {
        return this.title;
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
