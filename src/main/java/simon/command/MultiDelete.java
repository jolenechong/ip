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
 * Represents a Command that deletes multiple tasks atomically (from the user's perspective).
 */
public class MultiDelete implements Command {
    private static final String DELETE_MESSAGE_TEMPLATE = "Noted. I've removed the following tasks:\n%s"
            + "\nNow you have %d tasks in the list.";
    private static final String UNDO_MESSAGE_TEMPLATE = "All right! These tasks are back:\n%s"
            + "\nYour list now has %d tasks.";
    private static final String ERROR_TASK_DOESNT_EXIST = "One or more task numbers do not exist (yet). "
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
        if (indices.isEmpty()) {
            this.indices = new ArrayList<>();
            return;
        }
        this.indices = new ArrayList<>(indices);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        if (indices.isEmpty()) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        Set<Integer> validIndices = IntValidator.validateIndices(indices, tasks.size());
        if (validIndices == null) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        try {
            performDeletions(validIndices, tasks);
        } catch (InputFormatException e) {
            ui.printError(ERROR_TASK_DOESNT_EXIST);
            return true;
        }

        ui.printAll(String.format(
                DELETE_MESSAGE_TEMPLATE,
                formatRemovedTasks(),
                tasks.size()
        ));

        return true;
    }

    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        if (removedEntries.isEmpty()) {
            return true;
        }

        restoreTasks(tasks);

        ui.printAll(String.format(
                UNDO_MESSAGE_TEMPLATE,
                formatRemovedTasks(),
                tasks.size()
        ));

        return true;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    private void performDeletions(Set<Integer> validIndices, TaskList tasks) throws InputFormatException {
        // sort descending for deletion so earlier deletions don't shift later targets
        List<Integer> toDelete = new ArrayList<>(validIndices);
        toDelete.sort(Comparator.reverseOrder());

        removedEntries.clear();

        for (Integer idx : toDelete) {
            Task removed = tasks.delete(idx);
            removedEntries.add(new RemovedEntry(idx, removed));
        }

        removedEntries.sort(Comparator.comparingInt(e -> e.index));
    }

    private void restoreTasks(TaskList tasks) {
        // restore in ascending index order so insertion positions are correct.
        removedEntries.sort(Comparator.comparingInt(e -> e.index));
        for (RemovedEntry e : removedEntries) {
            tasks.add(e.task, e.index);
        }
    }

    private String formatRemovedTasks() {
        StringBuilder sb = new StringBuilder();
        for (RemovedEntry e : removedEntries) {
            sb.append("  ").append(e.task).append('\n');
        }
        return sb.toString();
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
