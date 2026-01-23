package simon.command;

import simon.model.TaskList;
import simon.ui.UI;

public class MarkCommand implements Command {
    private final int index;
    private final boolean completed;

    public MarkCommand(int index, boolean completed) {
        this.index = index;
        this.completed = completed;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui) {
        if (index <= 0 || index > tasks.getTasks().size()) {
            ui.printError("That task number doesn’t exist (yet). Try one from the list!");
            return true;
        }

        var t = tasks.mark(index, completed);

        if (completed) {
            ui.printAll("""
                    Nice! I've marked this task as done:
                      %s""", t);
        } else {
            ui.printAll("""
                    OK, I've marked this task as not done yet:
                      %s""", t);
        }
        return true;
    }
}