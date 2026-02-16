package simon.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import simon.exception.InputFormatException;
import simon.model.TaskList;
import simon.task.Task;
import simon.ui.Ui;
import simon.util.IntValidator;

/**
 * Represents a Command that marks or unmarks multiple tasks as completed in one operation.
 */
public class MultiMark implements Command {
    private static final String MARK_MESSAGE_TEMPLATE = "Noted. I've marked the following tasks as %s:\n%s";
    private static final String UNDO_MESSAGE_TEMPLATE = "Mark undone! These tasks are back:\n%s\nYour list "
            + "now has %d tasks.";
    private static final String ERROR_TASK_DOESNT_EXIST = "That task number does not exist (yet). "
            + "Try one from the list!";
    private static final String STATUS_DONE = "done";
    private static final String STATUS_NOT_DONE = "not done";
    private static final Integer BASE_INDEX = 1;

    private final boolean isCompleted;
    private final List<Integer> indices; // defensive copy of requested 1-based indices

    private final List<MarkedEntry> markedEntries = new ArrayList<>();

    /**
     * Creates a MultiMark to set the given indices to isCompleted.
     *
     * @param isCompleted desired completion state for all given indices
     * @param indices list of 1-based task indices
     */
    public MultiMark(boolean isCompleted, List<Integer> indices) {
        this.isCompleted = isCompleted;
        this.indices = new ArrayList<>(indices);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        if (indices.isEmpty()) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        Set<Integer> validIndices =
                IntValidator.validateIndices(indices, tasks.getTasks().size());

        if (validIndices == null || validIndices.isEmpty()) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        try {
            performMark(validIndices, tasks);
        } catch (InputFormatException e) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        ui.printAll(String.format(
                MARK_MESSAGE_TEMPLATE,
                isCompleted ? STATUS_DONE : STATUS_NOT_DONE,
                formatMarkedTasks(tasks)
        ));

        return true;
    }


    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        if (markedEntries.isEmpty()) {
            return true;
        }

        try {
            restorePreviousStates(tasks);
        } catch (InputFormatException e) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        ui.printAll(String.format(
                UNDO_MESSAGE_TEMPLATE,
                formatMarkedTasks(tasks),
                tasks.size()
        ));

        return true;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    private void performMark(Set<Integer> validIndices, TaskList tasks) throws InputFormatException {
        // sort ascending for marking
        List<Integer> toMark = new ArrayList<>(validIndices);
        toMark.sort(Comparator.naturalOrder());

        markedEntries.clear();

        for (Integer idx : toMark) {
            Task t = tasks.getTasks().get(idx - BASE_INDEX);
            boolean wasCompleted = t.isCompleted();
            tasks.mark(idx, isCompleted);
            markedEntries.add(new MarkedEntry(idx, wasCompleted));
        }

        markedEntries.sort(Comparator.comparingInt(e -> e.index));
    }

    private void restorePreviousStates(TaskList tasks) throws InputFormatException {
        // restore in ascending order for consistency
        markedEntries.sort(Comparator.comparingInt(e -> e.index));

        for (MarkedEntry entry : markedEntries) {
            tasks.mark(entry.index, entry.wasCompleted);
        }
    }

    private String formatMarkedTasks(TaskList tasks) {
        StringBuilder sb = new StringBuilder();

        for (MarkedEntry entry : markedEntries) {
            sb.append("  ")
                    .append(tasks.getTasks().get(entry.index - 1))
                    .append('\n');
        }

        return sb.toString();
    }

    private static class MarkedEntry {
        final int index;
        final boolean wasCompleted;

        MarkedEntry(int index, boolean wasCompleted) {
            this.index = index;
            this.wasCompleted = wasCompleted;
        }
    }
}
