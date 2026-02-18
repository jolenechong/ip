package simon.util;

import static simon.util.IntParser.parseIndex;

import java.util.ArrayList;
import java.util.List;

import simon.command.AddCommand;
import simon.command.ByeCommand;
import simon.command.Command;
import simon.command.CommandInvoker;
import simon.command.CompositeCommand;
import simon.command.DeleteCommand;
import simon.command.FindCommand;
import simon.command.ListCommand;
import simon.command.MarkCommand;
import simon.command.MultiDelete;
import simon.command.MultiMark;
import simon.command.OnCommand;
import simon.command.UndoCommand;
import simon.exception.InputErrorType;
import simon.exception.InputFormatException;
import simon.task.Deadline;
import simon.task.Event;
import simon.task.Todo;

/**
 * Represents a Utility class for parsing user input into Command objects.
 */
public class UiParser {

    private static final String CHAINING_DELIMITER_REGEX = "\\s*&&\\s*";
    private static final String SINGLE_COMMAND_REGEX = "\\s+";
    private static final String BY_DELIMITER = " /by ";
    private static final String FROM_DELIMITER = " /from ";
    private static final String TO_DELIMITER = " /to ";
    private static final String COMMA_DELIMITER = ",";
    private static final String DASH_DELIMITER = "-";
    private static final String CHAINING_DELIMITER = "&&";
    private static final String NEGATIVE_NUMBER_REGEX = "-\\d+";

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

        if (raw.contains(CHAINING_DELIMITER)) {
            return parseMultiple(raw, invoker);
        }

        return parseSingle(raw, invoker);

    }

    private Command parseMultiple(String raw, CommandInvoker invoker)
            throws InputFormatException {
        String[] commandStrings = raw.split(CHAINING_DELIMITER_REGEX);
        List<Command> commands = new ArrayList<>();
        for (String commandString : commandStrings) {
            commands.add(parseSingle(commandString, invoker));
        }
        return new CompositeCommand(invoker, commands);
    }

    private Command parseSingle(String raw, CommandInvoker invoker) throws InputFormatException {
        String[] parts = raw.split(SINGLE_COMMAND_REGEX, 2);
        String cmd = parts[0].toLowerCase();

        return switch (cmd) {
        case "bye" -> new ByeCommand();
        case "list" -> new ListCommand();
        case "find" -> new FindCommand(requireArgument(parts, InputErrorType.QUERY_EMPTY));
        case "mark" -> parseMarkOrMultiMark(parts, true);
        case "unmark" -> parseMarkOrMultiMark(parts, false);
        case "todo" -> new AddCommand(new Todo(requireArgument(parts, InputErrorType.TODO_EMPTY)));
        case "deadline" -> parseDeadline(requireArgument(parts, InputErrorType.DEADLINE_FORMAT));
        case "event" -> parseEvent(requireArgument(parts, InputErrorType.EVENT_FORMAT));
        case "delete" -> parseDeleteOrMultiDelete(parts);
        case "on" -> new OnCommand(DateParser.parse(
                requireArgument(parts, InputErrorType.ON_FORMAT).trim()));
        case "undo" -> new UndoCommand(invoker);
        default -> throw new InputFormatException(InputErrorType.UNKNOWN_INPUT);
        };
    }

    private static Command parseDeleteOrMultiDelete(String[] parts)
            throws InputFormatException {
        String arg = requireArgument(parts, InputErrorType.DELETE_FORMAT);

        if (arg.contains(COMMA_DELIMITER) || arg.contains(DASH_DELIMITER)) {
            if (arg.matches(NEGATIVE_NUMBER_REGEX)) {
                throw new InputFormatException(InputErrorType.NUMBER_RANGE);
            }
            List<Integer> indexes = IntParser.parseIndexes(arg);
            return new MultiDelete(indexes);
        } else {
            int index = parseIndex(arg);
            return new DeleteCommand(index);
        }
    }

    private static Command parseMarkOrMultiMark(String[] parts, boolean isCompleted)
            throws InputFormatException {
        String arg = requireArgument(parts, InputErrorType.MARK_FORMAT);

        if (arg.contains(COMMA_DELIMITER) || arg.contains(DASH_DELIMITER)) {
            if (arg.matches(NEGATIVE_NUMBER_REGEX)) {
                throw new InputFormatException(InputErrorType.NUMBER_RANGE);
            }
            List<Integer> indexes = IntParser.parseIndexes(arg);
            return new MultiMark(isCompleted, indexes);
        } else {
            int index = parseIndex(arg);
            return new MarkCommand(index, isCompleted);
        }
    }

    private static String requireArgument(String[] parts, InputErrorType error)
            throws InputFormatException {
        if (parts.length <= 1 || parts[1].isBlank()) {
            throw new InputFormatException(error);
        }
        return parts[1];
    }

    private Command parseDeadline(String rest) throws InputFormatException {
        int byIndex = rest.indexOf(BY_DELIMITER);
        if (byIndex == -1) {
            throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
        }
        String desc = rest.substring(0, byIndex).trim();
        String by = rest.substring(byIndex + 5).trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new InputFormatException(InputErrorType.DEADLINE_FORMAT);
        }
        return new AddCommand(new Deadline(desc, by));
    }

    private Command parseEvent(String rest) throws InputFormatException {
        int fromIndex = rest.indexOf(FROM_DELIMITER);
        int toIndex = rest.indexOf(TO_DELIMITER);
        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new InputFormatException(InputErrorType.EVENT_FORMAT);
        }

        String desc = rest.substring(0, fromIndex).trim();
        String from = rest.substring(fromIndex + 7, toIndex).trim();
        String to = rest.substring(toIndex + 5).trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new InputFormatException(InputErrorType.EVENT_FORMAT);
        }

        return new AddCommand(new Event(desc, from, to));
    }
}
