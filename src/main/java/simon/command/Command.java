package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

/**
 * Represents a command that can be executed.
 */
public interface Command {
    /**
     * Executes command. Return true to continue REPL, false to exit.
     */
    boolean execute(TaskList tasks, UI ui);
}