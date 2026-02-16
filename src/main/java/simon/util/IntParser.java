package simon.util;

import java.util.ArrayList;
import java.util.List;

import simon.exception.InputErrorType;
import simon.exception.InputFormatException;

/**
 * Utility class for parsing integers.
 */
public class IntParser {

    private static final String COMMA_DELIMITER_REGEX = "\\s*,\\s*";
    private static final String RANGE_DELIMITER_REGEX = "-";

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
        validateInputString(s);

        String[] parts = splitByComma(s);
        List<Integer> out = new ArrayList<>();

        for (String part : parts) {
            if (part.contains(RANGE_DELIMITER_REGEX)) {
                parseRange(part, out);
            } else {
                out.add(parseSingleIndex(part));
            }
        }
        return out;
    }

    private static void validateInputString(String s) throws InputFormatException {
        if (s == null || s.isBlank()) {
            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
        }
    }

    private static String[] splitByComma(String s) {
        return s.split(COMMA_DELIMITER_REGEX);
    }

    private static int parseSingleIndex(String part) throws InputFormatException {
        int index = parseIndex(part.trim());
        if (index <= 0) {
            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
        }
        return index;
    }

    private static void parseRange(String part, List<Integer> out) throws InputFormatException {
        String[] range = part.split("-", 2);
        if (range.length != 2) {
            throw new InputFormatException(InputErrorType.INVALID_RANGE);
        }

        int start = parsePositiveInt(range[0]);
        int end = parsePositiveInt(range[1]);

        if (end < start) {
            throw new InputFormatException(InputErrorType.INVALID_RANGE);
        }

        addRange(start, end, out);
    }

    private static int parsePositiveInt(String s) throws InputFormatException {
        int value = Integer.parseInt(s.trim());
        if (value <= 0) {
            throw new InputFormatException(InputErrorType.INVALID_RANGE);
        }
        return value;
    }

    private static void addRange(int start, int end, List<Integer> out) {
        for (int i = start; i <= end; i++) {
            out.add(i);
        }
    }

}
