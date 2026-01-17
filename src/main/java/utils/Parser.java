package utils;

import exceptions.InputFormatException;

public class Parser {
    public static int parseIndex(String s) throws InputFormatException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw InputFormatException.numberFormatError();
        }
    }
}
