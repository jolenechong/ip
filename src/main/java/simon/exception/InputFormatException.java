package simon.exception;

/**
 * Exception thrown when user input does not match expected format.
 * Uses {@link InputErrorType} to specify the type of input error.
 * @see InputErrorType
 */
public class InputFormatException extends Exception {
    private final InputErrorType type;

    /**
     * Constructor for InputFormatException.
     * @param type The type of input error.
     */
    public InputFormatException(InputErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

}
