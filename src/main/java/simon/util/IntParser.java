package simon.util;

import simon.exception.InputErrorType;
import simon.exception.InputFormatException;

/**
 * Utility class for parsing integers.
 */
public class IntParser {

    /**
     * Parses a string into an integer index, else throws an InputFormatException.
     *
     * @param s the string to parse.
     * @return the parsed integer.
     * @throws InputFormatException if the string is not a valid integer.
     */
    public static int parseIndex(String s) throws InputFormatException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
        }
    }
}
