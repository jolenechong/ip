package simon.command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.UI;

/**
 * Command to find tasks that match a given keyword.
 */
public class FindCommand implements Command {

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
     * @param ui The UI instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui) {
        List<Task> matchingTasks = tasks.find(query);

        StringBuilder sb = new StringBuilder();

        String result = IntStream.range(0, matchingTasks.size())
                .mapToObj(i -> (i + 1) + "." + matchingTasks.get(i))
                .collect(Collectors.joining("\n", "Here are the matching tasks in your list:\n", ""));

        if (matchingTasks.isEmpty()) {
            result = "No matching tasks found.";
        }

        ui.printAll(result);


        if (matchingTasks.isEmpty()) {
            sb.append("\nNo matching tasks found.");
        }

        ui.printAll(sb.toString());

        return true;
    }
}
