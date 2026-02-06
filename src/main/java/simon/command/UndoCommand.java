package simon.command;

import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a command to undo the last executed command.
 */
public class UndoCommand implements Command {

    private final CommandInvoker invoker;

    public UndoCommand(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        invoker.executeUndo(tasks, ui);
        return true;
    }

    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        return Command.super.undo(tasks, ui);
    }

    @Override
    public boolean isUndoable() {
        return Command.super.isUndoable();
    }
}
