package simon.command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand implements Command {
    private static final String LIST_MESSAGE_TEMPLATE = "Here are the tasks in your list:";
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

        String result = formatTasks(tasks.getTasks());

        ui.printAll(result);
        return true;
    }

    /**
     * Formats a list of tasks into a numbered string prefixed by the list message template.
     */
    private static String formatTasks(List<Task> taskList) {
        return IntStream.range(0, taskList.size())
                .mapToObj(i -> (i + 1) + "." + taskList.get(i))
                .collect(Collectors.joining("\n", LIST_MESSAGE_TEMPLATE + "\n", ""));
    }

}
