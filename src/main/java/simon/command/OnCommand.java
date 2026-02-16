package simon.command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.Ui;
import simon.util.DateParser;

/**
 * Represents a Command to list all deadlines and events occurring on a specific date.
 */
public class OnCommand implements Command {
    private static final String ON_MESSAGE_TEMPLATE = "Here are the deadlines and events on %s:\n%s";
    private static final String NO_TASKS_MESSAGE = "No deadlines or events found on that date.";

    private final LocalDateTime when;

    /**
     * Constructs a OnCommand.
     *
     * @param when The date to filter deadlines and events.
     */
    public OnCommand(LocalDateTime when) {
        this.when = when;
    }

    /**
     * Executes the command to list deadlines and events on the specified date.
     *
     * @param tasks The TaskList containing all tasks.
     * @param ui The UiParser instance for displaying output.
     * @return true to indicate successful execution.
     */
    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        List<Task> matchingTasks = tasks.on(when);

        if (matchingTasks.isEmpty()) {
            ui.printAll(NO_TASKS_MESSAGE);
            return true;
        }

        String formatted = formatTasks(matchingTasks);

        ui.printAll(ON_MESSAGE_TEMPLATE, DateParser.format(when), formatted);

        return true;
    }

    /**
     * Format matching tasks into a newline-separated string.
     */
    private static String formatTasks(List<Task> tasks) {
        return tasks.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

}
