package simon.exception;

/**
 * Typed input errors with human-readable messages.
 */
public enum InputErrorType {
    TODO_EMPTY("That todo seems to be missing a description :("),
    DEADLINE_FORMAT("That deadline looks a little lost… Format it like: deadline <description> /by <time>"),
    EVENT_FORMAT("This event needs boundaries! Format: event <description> /from <start> /to <end>"),
    NUMBER_FORMAT("That doesn’t look like a number hmm... Please enter a valid number."),
    NUMBER_RANGE("That task number doesn’t exist (yet). Try one from the list!"),
    UNKNOWN_INPUT("hUH what are you sAying"),
    QUERY_EMPTY("You need to provide a search query!");

    private final String message;

    /**
     * Constructor for InputErrorType.
     *
     * @param message Human-readable error message.
     */
    InputErrorType(String message) {
        this.message = message;
    }

    /**
     * Gets the human-readable error message.
     *
     * @return Error message.
     */
    public String getMessage() {
        return message;
    }
}
