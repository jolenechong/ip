package simon.exception;

/**
 * Represents an Exception thrown when user input does not match expected format.
 * Uses {@link InputErrorType} to specify the type of input error.
 */
public class InputFormatException extends Exception {
    private final InputErrorType type;

    /**
     * Constructs InputFormatException.
     *
     * @param type The type of input error.
     */
    public InputFormatException(InputErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

}
