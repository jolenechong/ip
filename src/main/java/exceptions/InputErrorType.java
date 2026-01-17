package exceptions;

public enum InputErrorType {
    TODO_EMPTY("That todo seems to be missing a description :("),
    DEADLINE_FORMAT("That deadline looks a little lost… Format it like: deadline <description> /by <time>"),
    EVENT_FORMAT("This event needs boundaries! Format: event <description> /from <start> /to <end>"),
    NUMBER_FORMAT("That doesn’t look like a number hmm... Please enter a valid number."),
    NUMBER_RANGE("That task number doesn’t exist (yet). Try one from the list!");

    private final String message;

    InputErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
