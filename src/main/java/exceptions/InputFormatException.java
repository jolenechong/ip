package exceptions;

public class InputFormatException extends Exception {
    private final InputErrorType type;

    public InputFormatException(InputErrorType type) {
        super(type.getMessage());
        this.type = type;
    }

    public InputErrorType getType() {
        return type;
    }
}
