package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

/**
 * Finds and lists all tasks that match a given keyword.
 */
public class FindCommand implements Command {

    private final String query;

    public FindCommand(String query) {
        this.query = query;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:");

        int count = 0;
        for (int i = 0; i < tasks.getTasks().size(); i++) {
            if (tasks.getTasks().get(i).getTitle().contains(query)) {
                count++;
                sb.append("\n")
                        .append(count)
                        .append(".")
                        .append(tasks.getTasks().get(i));
            }
        }

        if (count == 0) {
            sb.append(" No matching tasks found.");
        }

        ui.printAll(sb.toString());

        return true;
    }
}