package simon.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Utility class for parsing and formatting dates.
 */
public final class DateParser {
    private static final DateTimeFormatter[] DATE_TIME_FORMATTERS = new DateTimeFormatter[]{
        DateTimeFormatter.ISO_LOCAL_DATE, // 2025-08-31
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

    private DateParser() {
    }

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
            try {
                return LocalDateTime.parse(trimmed, fmt);
            } catch (DateTimeParseException ignored) {
                // try next
            }
        }

        for (DateTimeFormatter fmt : DATE_FORMATTERS) {
            try {
                LocalDate d = LocalDate.parse(trimmed, fmt);
                return d.atStartOfDay();
            } catch (DateTimeParseException ignored) {
                // try next
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + input);
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
