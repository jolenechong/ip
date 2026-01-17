package exceptions;

public class InputFormatException extends Exception {
    public static final String TODO_EMPTY_MESSAGE = "That todo seems to be missing a description :(";
    public static final String DEADLINE_FORMAT_MESSAGE =
            "That deadline looks a little lost… Format it like: deadline <description> /by <time>";
    public static final String EVENT_FORMAT_MESSAGE =
            "This event needs boundaries! Format: event <description> /from <start> /to <end>";
    public static final String NUMBER_FORMAT_MESSAGE =
            "That doesn’t look like a number hmm... Please enter a valid number.";
    public static final String NUMBER_RANGE_MESSAGE =
            "That task number doesn’t exist (yet). Try one from the list!\"";

    public InputFormatException(String message) {
        super(message);
    }

    public InputFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InputFormatException todoDescriptionEmpty() {
        return new InputFormatException(TODO_EMPTY_MESSAGE);
    }

    public static InputFormatException deadlineFormatError() {
        return new InputFormatException(DEADLINE_FORMAT_MESSAGE);
    }

    public static InputFormatException eventFormatError() {
        return new InputFormatException(EVENT_FORMAT_MESSAGE);
    }

    public static InputFormatException numberFormatError() {
        return new InputFormatException(NUMBER_FORMAT_MESSAGE);
    }

    public static InputFormatException numberRangeError() {
        return new InputFormatException(NUMBER_RANGE_MESSAGE);
    }
}
