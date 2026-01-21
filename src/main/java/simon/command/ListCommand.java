package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

public class ListCommand implements Command {
    @Override
    public boolean execute(TaskList tasks, UI ui) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getTasks().size(); i++) {
            sb.append((i + 1) + "." + tasks.getTasks().get(i));
            if (i != tasks.getTasks().size() - 1) {
                sb.append("\n");
            }
        }
        ui.printAll(sb.toString());
        return true;
    }
}