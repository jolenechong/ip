package simon.command;

import java.util.List;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.Ui;

/**
 * Command to find tasks that match a given keyword.
 */
public class FindCommand implements Command {

    private static final String FIND_MESSAGE_TEMPLATE = "Here are the matching tasks in your list:";
    private static final String ERROR_NO_MATCHING_TASKS = "No matching tasks found.";
    private final String query;

    /**
     * Constructor for FindCommand.
     *
     * @param query The keyword to search for in task titles.
     */
    public FindCommand(String query) {
        this.query = query;
    }

    /**
     * Finds and lists all tasks that match a given keyword.
     *
     * @param tasks The TaskList containing all tasks.
     * @param ui The UiParser instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        List<Task> matchingTasks = tasks.find(query);

        StringBuilder sb = new StringBuilder();
        sb.append(FIND_MESSAGE_TEMPLATE);

        for (Task task : matchingTasks) {
            sb.append("\n")
                    .append(matchingTasks.indexOf(task) + 1)
                    .append(".")
                    .append(task);
        }

        if (matchingTasks.isEmpty()) {
            sb.append(ERROR_NO_MATCHING_TASKS);
        }

        ui.printAll(sb.toString());

        return true;
    }
}
