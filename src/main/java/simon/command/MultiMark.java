package simon.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import simon.model.TaskList;
import simon.task.Task;
import simon.ui.Ui;

/**
 * Command that marks or unmarks multiple tasks as completed in one operation.
 */
public class MultiMark implements Command {
    private static final String MARK_MESSAGE = "Noted. I've marked the following tasks as %s:\n%s";
    private static final String ERROR_TASK_DOESNT_EXIST = "That task number doesn’t exist (yet). "
            + "Try one from the list!";
    private static final String UNDO_MESSAGE = "Mark undone! These tasks are back:\n%s\nYour list now has %d tasks.";

    private final boolean isCompleted;
    private final List<Integer> indices; // defensive copy of requested 1-based indices

    private final List<MarkedEntry> markedEntries = new ArrayList<>();

    /**
     * Create a MultiMark to set the given indices to isCompleted.
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
        Set<Integer> seen = validateIndices(indices, tasks.getTasks().size());
        if (seen == null) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }
        performMark(seen, tasks);

        StringBuilder listSb = new StringBuilder();
        for (MarkedEntry e : markedEntries) {
            listSb.append("  ").append(tasks.getTasks().get(e.index - 1)).append('\n');
        }
        ui.printAll(MARK_MESSAGE, isCompleted ? "done" : "not done", listSb.toString());

        return true;
    }

    private void performMark(Set<Integer> seen, TaskList tasks) {
        // sort ascending for marking
        List<Integer> toMark = new ArrayList<>(seen);
        toMark.sort(Comparator.naturalOrder());

        markedEntries.clear();

        for (Integer idx : toMark) {
            Task t = tasks.getTasks().get(idx - 1);
            boolean prev = t.isCompleted();
            tasks.mark(idx, isCompleted);
            markedEntries.add(new MarkedEntry(idx, prev));
        }

        markedEntries.sort(Comparator.comparingInt(e -> e.index));
    }

    private Set<Integer> validateIndices(List<Integer> indices, int size) {
        Set<Integer> seen = new LinkedHashSet<>();
        for (Integer idx : indices) {
            if (idx == null || idx <= 0 || idx > size) {
                return null;
            }
            seen.add(idx);
        }
        return seen;
    }

    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        if (markedEntries.isEmpty()) {
            return true;
        }

        // restore in ascending order
        markedEntries.sort(Comparator.comparingInt(e -> e.index));
        for (MarkedEntry e : markedEntries) {
            tasks.mark(e.index, e.previous);
        }

        StringBuilder listSb = new StringBuilder();
        for (MarkedEntry e : markedEntries) {
            listSb.append("  ").append(tasks.getTasks().get(e.index - 1)).append('\n');
        }

        ui.printAll(UNDO_MESSAGE, listSb, tasks.size());
        return true;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    private static class MarkedEntry {
        final int index;
        final boolean previous;

        MarkedEntry(int index, boolean previous) {
            this.index = index;
            this.previous = previous;
        }
    }
}
