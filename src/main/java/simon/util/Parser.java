package simon.util;

import simon.exception.InputErrorType;
import simon.exception.InputFormatException;

public class Parser {
    public static int parseIndex(String s) throws InputFormatException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
        }
    }
}
