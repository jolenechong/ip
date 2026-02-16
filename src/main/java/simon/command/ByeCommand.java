package simon.command;

import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents the Bye command which exits the application.
 */
public class ByeCommand implements Command {
    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        ui.sayBye();
        return false;
    }

}
