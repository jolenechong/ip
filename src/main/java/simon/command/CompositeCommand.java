package simon.command;

import java.util.List;

import simon.model.TaskList;
import simon.ui.Ui;

/**
 * Represents a command that is composed of multiple other commands.
 */
public class CompositeCommand implements Command {

    private final CommandInvoker invoker;
    private List<Command> commands;

    /**
     * Constructs CompositeCommand.
     *
     * @param invoker CommandInvoker to execute sub-commands.
     * @param commands List of commands to be executed as part of this composite command.
     */
    public CompositeCommand(CommandInvoker invoker, List<Command> commands) {
        this.invoker = invoker;
        this.commands = commands;
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui) {
        for (Command cmd: this.commands) {
            invoker.execute(cmd, tasks, ui);
        }

        return true;
    }

    @Override
    public boolean undo(TaskList tasks, Ui ui) {
        for (int i = this.commands.size() - 1; i >= 0; i--) {
            Command cmd = this.commands.get(i);
            cmd.undo(tasks, ui);
        }
        return true;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
