package simon.command;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.UI;

/**
 * Command to add a task to the task list.
 */
public class AddCommand implements Command {
    private final Task task;

    /**
     * Constructor for AddCommand.
     * @param task The task to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the command to add the specified task.
     * @param tasks The TaskList containing all tasks.
     * @param ui The UI instance for displaying output.
     * @return true to indicate successful execution.
     */
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