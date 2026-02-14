package simon.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Represents a Utility class for parsing and formatting dates.
 */
public final class DateParser {
    private static final Logger LOGGER = Logger.getLogger(DateParser.class.getName());

    private static final DateTimeFormatter[] DATE_TIME_FORMATTERS = new DateTimeFormatter[]{
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm"), // 31/8/2025 1800
        DateTimeFormatter.ofPattern("d-M-yyyy HHmm"), // 31-8-2025 1800
        DateTimeFormatter.ofPattern("yyyy/M/d HHmm"), // 2025/08/31 1800
    };
    private static final DateTimeFormatter[] DATE_FORMATTERS = new DateTimeFormatter[]{
        DateTimeFormatter.ISO_LOCAL_DATE, // 2025-08-31
        DateTimeFormatter.ofPattern("d/M/yyyy"), // 31/8/2025
        DateTimeFormatter.ofPattern("d-M-yyyy"), // 31-8-2025
        DateTimeFormatter.ofPattern("yyyy/M/d"), // 2025/08/31
        DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH) // 31 Aug 2025
    };

    /**
     * Parses a date string into a LocalDateTime object.
     *
     * @param input the date string to parse.
     * @return the parsed LocalDateTime object.
     */
    public static LocalDateTime parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Date string is null");
        }

        String trimmed = input.trim();
        for (DateTimeFormatter fmt : DATE_TIME_FORMATTERS) {
            LocalDateTime dt = tryParseDateTime(trimmed, fmt);
            if (dt != null) {
                return dt;
            }
        }

        for (DateTimeFormatter fmt : DATE_FORMATTERS) {
            LocalDate d = tryParseDate(trimmed, fmt);
            if (d != null) {
                return d.atStartOfDay();
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + input);
    }

    private static LocalDateTime tryParseDateTime(String input, DateTimeFormatter fmt) {
        try {
            return LocalDateTime.parse(input, fmt);
        } catch (DateTimeParseException e) {
            LOGGER.fine(() -> "DateTime parse failed for format " + fmt + ": " + e.getMessage());
            return null;
        }
    }

    private static LocalDate tryParseDate(String input, DateTimeFormatter fmt) {
        try {
            return LocalDate.parse(input, fmt);
        } catch (DateTimeParseException e) {
            LOGGER.fine(() -> "Date parse failed for format " + fmt + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Formats a LocalDateTime object into a readable string.
     *
     * @param dateTime the LocalDateTime object to format.
     * @return the formatted date string.
     */
    public static String format(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu h:mma", Locale.ENGLISH);
        String formatted = dateTime.format(formatter);
        return formatted.replace("AM", "am").replace("PM", "pm");
    }

}
