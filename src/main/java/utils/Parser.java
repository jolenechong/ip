package utils;

import exceptions.InputErrorType;
import exceptions.InputFormatException;

public class Parser {
    public static int parseIndex(String s) throws InputFormatException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InputFormatException(InputErrorType.NUMBER_FORMAT);
        }
    }
}
