package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

/**
 * Command to mark a task as completed or not completed.
 */
public class MarkCommand implements Command {
    private final int index;
    private final boolean completed;

    /**
     * Constructor for MarkCommand.
     *
     * @param index The index of the task to be marked (1-based).
     * @param completed True to mark as completed, false to mark as not completed.
     */
    public MarkCommand(int index, boolean completed) {
        this.index = index;
        this.completed = completed;
    }

    /**
     * Executes the command to mark/unmark the specified task.
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

        var t = tasks.mark(index, completed);

        if (completed) {
            ui.printAll("""
                    Nice! I've marked this task as done:
                      %s""", t);
        } else {
            ui.printAll("""
                    OK, I've marked this task as not done yet:
                      %s""", t);
        }
        return true;
    }
}
