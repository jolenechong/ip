package simon.command;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.UI;

public class AddCommand implements Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui) {
        tasks.add(task);
        ui.printAll("""
                        Got it. I've added this task:
                          %s
                        Now you have %d tasks in the list.""",
                task, tasks.getTasks().size());
        return true;
    }
}