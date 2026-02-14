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
 * Represents a Command that deletes multiple tasks atomically (from the user's perspective).
 */
public class MultiDelete implements Command {
    private static final String DELETE_MESSAGE = "Noted. I've removed the following tasks:\n%s"
            + "\nNow you have %d tasks in the list.";
    private static final String UNDO_MESSAGE = "All right! These tasks are back:\n%s\nYour list now has %d tasks.";
    private static final String ERROR_TASK_DOESNT_EXIST = "One or more task numbers don’t exist (yet). "
            + "Try ones from the list!";

    private final List<Integer> indices;

    /**
     * Stores removed entries for undo; populated during execute.
     */
    private final List<RemovedEntry> removedEntries = new ArrayList<>();

    /**
     * Creates a MultiDelete for the requested 1-based indices.
     *
     * @param indices list of 1-based indices; a defensive copy will be kept.
     */
    public MultiDelete(List<Integer> indices) {
        // keep a defensive copy
        this.indices = (indices == null) ? new ArrayList<>() : new ArrayList<>(indices);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        if (indices.isEmpty()) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        Set<Integer> seen = validateIndices(indices, tasks.getTasks().size());
        performDeletions(seen, tasks);

        StringBuilder sb = new StringBuilder();
        for (RemovedEntry e : removedEntries) {
            sb.append("  ").append(e.task).append('\n');
        }
        ui.printAll(String.format(DELETE_MESSAGE, sb.toString(), removedEntries.size()));

        return true;
    }

    private void performDeletions(Set<Integer> seen, TaskList tasks) {
        // sort descending for deletion so earlier deletions don't shift later targets
        List<Integer> toDelete = new ArrayList<>(seen);
        toDelete.sort(Comparator.reverseOrder());

        removedEntries.clear();

        for (Integer idx : toDelete) {
            Task removed = tasks.delete(idx);
            removedEntries.add(new RemovedEntry(idx, removed));
        }

        removedEntries.sort(Comparator.comparingInt(e -> e.index));
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
        if (removedEntries.isEmpty()) {
            return true;
        }

        // restore in ascending index order so insertion positions are correct.
        removedEntries.sort(Comparator.comparingInt(e -> e.index));
        for (RemovedEntry e : removedEntries) {
            tasks.add(e.task, e.index);
        }

        StringBuilder restoredSb = new StringBuilder();
        for (RemovedEntry e : removedEntries) {
            restoredSb.append("  ").append(e.task).append('\n');
        }
        ui.printAll(String.format(UNDO_MESSAGE, restoredSb, tasks.size()));

        return true;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    private static class RemovedEntry {
        final int index;
        final Task task;

        RemovedEntry(int index, Task task) {
            this.index = index;
            this.task = task;
        }
    }
}
