package simon.util;

import static simon.util.IntParser.parseIndex;

import java.time.LocalDateTime;

import simon.command.AddCommand;
import simon.command.Command;
import simon.command.CommandInvoker;
import simon.command.DeleteCommand;
import simon.command.FindCommand;
import simon.command.ListCommand;
import simon.command.MarkCommand;
import simon.command.OnCommand;
import simon.exception.InputErrorType;
import simon.exception.InputFormatException;
import simon.task.Deadline;
import simon.task.Event;
import simon.task.Todo;

/**
 * Utility class for parsing user input into Command objects.
 */
public class UiParser {

    /**
     * Parses a raw input string into a Command object.
     *
     * @param raw which is the raw input string.
     * @return the parsed Command object.
     * @throws InputFormatException if the input format is invalid.
     */
    public Command parse(String raw, CommandInvoker invoker) throws InputFormatException {
        if (raw == null || raw.isBlank()) {
            throw new InputFormatException(InputErrorType.TODO_EMPTY);
        }
        String[] parts = raw.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();

        return switch (cmd) {
        case "bye" -> (tasks, ui) -> {
            ui.sayBye();
            return false;
        };
        case "list" -> new ListCommand();
        case "find" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.QUERY_EMPTY);
            }
            yield new FindCommand(parts[1]);
        }
        case "mark" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
            }
            yield new MarkCommand(parseIndex(parts[1]), true);
        }
        case "unmark" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
            }
            yield new MarkCommand(parseIndex(parts[1]), false);
        }
        case "todo" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.TODO_EMPTY);
            }
            yield new AddCommand(new Todo(parts[1]));
        }
        case "deadline" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
            }
            String rest = parts[1];
            int byIndex = rest.indexOf(" /by ");
            if (byIndex == -1) {
                throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
            }
            String desc = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + 5).trim();
            if (desc.isEmpty() || by.isEmpty()) {
                throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
            }
            yield new AddCommand(new Deadline(desc, by));
        }
        case "event" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.EVENT_FORMAT);
            }
            String rest = parts[1];
            int fromIndex = rest.indexOf(" /from ");
            int toIndex = rest.indexOf(" /to ");
            if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
                throw new InputFormatException(InputErrorType.EVENT_FORMAT);
            }
            String desc = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + 7, toIndex).trim();
            String to = rest.substring(toIndex + 5).trim();
            if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new InputFormatException(InputErrorType.EVENT_FORMAT);
            }
            yield new AddCommand(new Event(desc, from, to));
        }
        case "delete" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.NUMBER_RANGE);
            }
            yield new DeleteCommand(parseIndex(parts[1]));
        }
        case "on" -> {
            if (parts.length <= 1) {
                throw new InputFormatException(InputErrorType.EVENT_FORMAT);
            }
            LocalDateTime d = DateParser.parse(parts[1].trim());
            yield new OnCommand(d);
        }
        case "undo" -> {
            yield (tasks, ui) -> invoker.executeUndo(tasks, ui);
        }
        default -> throw new InputFormatException(InputErrorType.UNKNOWN_INPUT);
        };
    }
}
