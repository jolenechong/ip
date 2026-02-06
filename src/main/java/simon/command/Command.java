package simon.command;

import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a command that can be executed.
 */
public interface Command {

    /**
     * Executes command. Return true to continue REPL, false to exit.
     */
    boolean execute(TaskList tasks, Ui ui);

    default boolean undo(TaskList tasks, Ui ui) {
        throw new UnsupportedOperationException("Not undoable");
    }

    default boolean isUndoable() {
        return false;
    };

}
