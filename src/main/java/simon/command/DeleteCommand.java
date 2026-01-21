package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

public class DeleteCommand implements Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui) {
        if (index <= 0 || index > tasks.getTasks().size()) {
            ui.printError("That task number doesn’t exist (yet). Try one from the list!");
            return true;
        }
        var removed = tasks.delete(index);
        ui.printAll("""
                Noted. I've removed this task:
                  %s
                Now you have %d tasks in the list.""", removed, tasks.getTasks().size());
        return true;
    }
}