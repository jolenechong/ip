package simon.command;

import simon.exception.InputFormatException;
import simon.model.TaskList;
import simon.task.Task;
import simon.ui.Ui;

/**
 * Represents a Command to delete a task from the task list.
 */
public class DeleteCommand implements Command {
    private static final String DELETE_MESSAGE_TEMPLATE = """
            Noted. I've removed this task:
              %s
            Now you have %d tasks in the list.""";
    private static final String ERROR_TASK_DOESNT_EXIST = "That task number doesn’t exist (yet). "
            + "Try one from the list!";
    private static final String UNDO_MESSAGE_TEMPLATE = "Undid deleting the task:\n  %s"
            + "\nNow you have %d tasks in the list.";

    private final int index;
    private Task removed;

    /**
     * Constructs DeleteCommand.
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
     * @param ui The UiParser instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        if (index <= 0 || index > tasks.size()) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        try {
            removed = tasks.delete(index);
        } catch (InputFormatException e) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }
        ui.printAll(DELETE_MESSAGE_TEMPLATE, removed, tasks.size());

        return true;
    }

    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        tasks.add(removed, index);
        ui.printAll(UNDO_MESSAGE_TEMPLATE, removed, tasks.size());

        return true;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

}
