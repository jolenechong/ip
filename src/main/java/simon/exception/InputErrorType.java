package simon.exception;

/**
 * Typed input errors with human-readable messages.
 */
public enum InputErrorType {
    TODO_EMPTY("That todo seems to be missing a description :("),
    DEADLINE_FORMAT("That deadline looks a little lost. Format it like: deadline <description> /by <time>"),
    EVENT_FORMAT("This event needs boundaries! Format: event <description> /from <start> /to <end>"),
    NUMBER_FORMAT("That does not look like a number hmm, please enter a valid number."),
    NUMBER_RANGE("That task number does not exist. Try one from the list!"),
    UNKNOWN_INPUT("hUH what are you sAying"),
    QUERY_EMPTY("You need to provide a search query!"),
    MARK_FORMAT("That does not look like a valid mark command. Try mark/unmark <task number> instead!"),
    DELETE_FORMAT("That does not look like a valid delete command. Try delete <task number> instead!"),
    ON_FORMAT("That does not look like a valid on command. Try on <date> instead!"),
    AI_FORMAT("That does not look like a valid ai command. Try @ai <query> instead!"),
    AI_NO_MODEL("I can't find the AI model! Please check your configuration and try again."),
    INVALID_RANGE("The range you provided is invalid. Use the format: <start>-<end>,<> with start <= end.");

    private final String message;

    /**
     * Constructs InputErrorType.
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
