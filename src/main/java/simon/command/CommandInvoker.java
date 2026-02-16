package simon.command;

import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a central place to execute commands and maintain history.
 */
public class CommandInvoker {
    private final CommandHistory history = new CommandHistory();

    /**
     * Executes a command and stores it in history.
     *
     * @param cmd the command to execute.
     * @param tasks the task list.
     * @param ui the UI instance.
     * @return true if the application should continue running, false to exit.
     */
    public boolean execute(Command cmd, TaskList tasks, Ui ui) {
        boolean isSuccess = cmd.execute(tasks, ui);

        if (cmd.isUndoable()) {
            history.push(cmd);
        }

        return isSuccess;
    }

    /**
     * Executes an undo operation for the last command in history.
     *
     * @param tasks the task list.
     * @param ui    the UI instance.
     */
    public void executeUndo(TaskList tasks, Ui ui) {
        Command cmd = history.pop();
        if (cmd != null) {
            cmd.undo(tasks, ui);
        } else {
            ui.printError("No commands to undo.");
        }
    }

}
