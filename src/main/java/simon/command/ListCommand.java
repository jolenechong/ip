package simon.command;

import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand implements Command {
    private static final String LIST_MESSAGE = "Here are the tasks in your list:";
    private static final String EMPTY_LIST_MESSAGE = "Your task list is empty :( Add some tasks first!";

    /**
     * Executes the list command to display all tasks.
     *
     * @param tasks The TaskList containing all tasks.
     * @param ui The UiParser instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        if (tasks.isEmpty()) {
            ui.printAll(EMPTY_LIST_MESSAGE);
            return true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(LIST_MESSAGE + "\n");

        for (int i = 0; i < tasks.getTasks().size(); i++) {
            sb.append((i + 1) + "." + tasks.getTasks().get(i));
            if (i != tasks.size() - 1) {
                sb.append("\n");
            }
        }

        ui.printAll(sb.toString());
        return true;
    }

}
