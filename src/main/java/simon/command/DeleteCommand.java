package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand implements Command {
    private final int index;

    /**
     * Constructor for DeleteCommand.
     *
     * @param index The index of the task to be deleted (1-based).
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command to delete the specified task.
     *
     * @param tasks The TaskList containing all tasks.
     * @param ui The UI instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui) {
        if (index <= 0 || index > tasks.getTasks().size()) {
            ui.printError("That task number doesn’t exist (yet). Try one from the list!");
            return true;
        }
        var removed = tasks.delete(index);
        ui.printAll("""
                Noted. I've removed this task:
                  %s
                Now you have %d tasks in the list.""", removed, tasks.getTasks().size());
        return true;
    }
}