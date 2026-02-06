package simon.command;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.Ui;

/**
 * Command to add a task to the task list.
 */
public class AddCommand implements Command {

    private static final String ADD_MESSAGE_TEMPLATE = """
                    Got it. I've added this task:
                      %s
                    Now you have %d tasks in the list.""";

    private final Task task;

    /**
     * Constructor for AddCommand.
     *
     * @param task The task to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the command to add the specified task
     * .
     * @param tasks The TaskList containing all tasks.
     * @param ui The UiParser instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        tasks.add(task);
        ui.printAll(ADD_MESSAGE_TEMPLATE,
                task, tasks.getTasks().size());

        return true;
    }

    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        tasks.delete(tasks.getTasks().size());
        ui.printAll("Undid adding the task:\n  %s\nNow you have %d tasks in the list.",
                task, tasks.getTasks().size());

        return true;
    }
}
