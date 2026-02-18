package simon.command;

import simon.exception.InputFormatException;
import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a Command to mark a task as completed or not completed.
 */
public class MarkCommand implements Command {
    private static final String ERROR_TASK_DOESNT_EXIST = "That task number does not exist. "
            + "Try one from the list!";
    private static final String MARKED_AS_DONE_MESSAGE_TEMPLATE = "Nice! I've marked this task as done: \n%s";
    private static final String MARKED_AS_NOT_DONE_MESSAGE_TEMPLATE = "OK, I've marked this task as not done yet: \n%s";

    private final int index;
    private final boolean isCompleted;

    /**
     * Constructs a MarkCommand.
     *
     * @param index The index of the task to be marked (1-based).
     * @param isCompleted True to mark as completed, false to mark as not completed.
     */
    public MarkCommand(int index, boolean isCompleted) {
        this.index = index;
        this.isCompleted = isCompleted;
    }

    /**
     * Executes the command to mark/unmark the specified task.
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
            var t = tasks.mark(index, isCompleted);

            if (isCompleted) {
                ui.printAll(MARKED_AS_DONE_MESSAGE_TEMPLATE, t);
            } else {
                ui.printAll(MARKED_AS_NOT_DONE_MESSAGE_TEMPLATE, t);
            }
        } catch (InputFormatException e) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }
        return true;
    }

    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        try {
            var t = tasks.mark(index, !isCompleted);

            if (!isCompleted) {
                ui.printAll(MARKED_AS_DONE_MESSAGE_TEMPLATE, t);
            } else {
                ui.printAll(MARKED_AS_NOT_DONE_MESSAGE_TEMPLATE, t);
            }
        } catch (InputFormatException e) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        return true;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
