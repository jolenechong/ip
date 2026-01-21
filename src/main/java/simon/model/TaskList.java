package simon.model;

import simon.Simon;
import simon.storage.Storage;
import simon.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> tasks;
    private final Storage storage;

    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = new ArrayList<>();
        this.tasks.addAll(storage.loadTasks());
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void add(Task item) {
        this.tasks.add(item);
        persist();
    }

    public Task mark(int num, boolean completed) {
        Task toMark = this.tasks.get(num - 1);
        toMark.setCompleted(completed);
        persist();
        return toMark;
    }

    public Task delete(int num) {
        Task toDelete = this.tasks.remove(num - 1);
        persist();
        return toDelete;
    }

    private void persist() {
        try {
            storage.saveTasks(this.tasks);
        } catch (Exception e) {
            System.err.println("Unexpected error while saving tasks: " + e.getMessage());
        }
    }
}
