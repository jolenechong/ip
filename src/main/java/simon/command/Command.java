package simon.command;

import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a command that can be executed.
 */
public interface Command {

    /**
     * Executes the command to perform its intended action.
     * May involve modifying/querying/displaying TaskList.
     * .
     * @param tasks The TaskList containing all tasks.
     * @param ui The UiParser instance for displaying output.
     * @return true to indicate successful execution.
     */
    boolean execute(TaskList tasks, Ui ui);

    /**
     * Undoes the command, reverting any changes made by the execute method.
     *
     * @param tasks The TaskList containing all tasks.
     * @param ui The UiParser instance for displaying output.
     * @return true to indicate successful undo, false if undo is not supported.
     */
    default boolean undo(TaskList tasks, Ui ui) {
        throw new UnsupportedOperationException("Not undoable");
    }

    /**
     * Indicates whether this command supports undoing.
     *
     * @return true if the command is undoable, false otherwise.
     */
    default boolean isUndoable() {
        return false;
    };

}
