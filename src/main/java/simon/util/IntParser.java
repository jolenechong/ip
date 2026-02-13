package simon.util;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Parses a string representing indexes and ranges into a list of integers.
     * For example, "1,3-5,7" would be parsed into [1,3,4,5,7].
     *
     * @param s the string to parse.
     * @return the list of parsed integers.
     * @throws InputFormatException if the string format is invalid.
     */
    public static List<Integer> parseIndexes(String s) throws InputFormatException {
        if (s == null || s.isBlank()) {
            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
        }
        String[] parts = s.split("\\s*,\\s*");
        List<Integer> out = new ArrayList<>();

        for (String p : parts) {
            assert(p.contains("-")); // assume only strings with '-' reach here

            String[] range = p.split("-", 2);
            if (range.length != 2) {
                throw new InputFormatException(InputErrorType.INVALID_RANGE);
            }

            int a = Integer.parseInt(range[0].trim());
            int b = Integer.parseInt(range[1].trim());
            if (a <= 0 || b <= 0 || b < a) {
                throw new InputFormatException(InputErrorType.INVALID_RANGE);
            }
            for (int i = a; i <= b; i++) {
                out.add(i);
            }
        }
        return out;
    }
}
