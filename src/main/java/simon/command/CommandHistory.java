package simon.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents the history of executed commands.
 */
public class CommandHistory {
    private final Deque<Command> stack = new ArrayDeque<>();

    public void push(Command cmd) {
        stack.push(cmd);
    }

    public Command pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }
}
