package simon.command;

import simon.model.TaskList;
import simon.task.Deadline;
import simon.task.Event;
import simon.task.Task;
import simon.ui.UI;
import simon.util.DateParser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Command to list all deadlines and events occurring on a specific date.
 */
public class OnCommand implements Command {
    private final LocalDateTime when;

    /**
     * Constructor for OnCommand.
     * @param when The date to filter deadlines and events.
     */
    public OnCommand(LocalDateTime when) {
        this.when = when;
    }

    /**
     * Executes the command to list deadlines and events on the specified date.
     * @param tasks The TaskList containing all tasks.
     * @param ui The UI instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the deadlines and events on " + DateParser.format(when) + ":\n");

        List<Task> list = tasks.getTasks();
        boolean shown = false;
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);

            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getBy().toLocalDate().equals(when.toLocalDate())) {

                    shown = true;
                    sb.append(i + 1 + "." + deadline);

                    if (i != list.size() - 1) {
                        sb.append("\n");
                    }
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.getFrom().toLocalDate().equals(when.toLocalDate())
                        || event.getTo().toLocalDate().equals(when.toLocalDate())) {

                    shown = true;
                    sb.append(i + 1 + "." + event);

                    if (i != list.size() - 1) {
                        sb.append("\n");
                    }
                }
            }
        }

        if (!shown) {
            sb.append("No deadlines or events found on that date.");
        }

        ui.printAll(sb.toString());
        return true;
    }
}