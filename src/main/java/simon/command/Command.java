package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

public interface Command {
    /**
     * Execute command. Return true to continue REPL, false to exit.
     */
    boolean execute(TaskList tasks, UI ui) throws Exception;
}