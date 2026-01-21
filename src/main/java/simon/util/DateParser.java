package simon.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public final class DateParser {
    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE,                    // 2025-08-31
            DateTimeFormatter.ofPattern("d/M/yyyy"),             // 31/8/2025
            DateTimeFormatter.ofPattern("d-M-yyyy"),             // 31-8-2025
            DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH) // 31 Aug 2025
    };

    private DateParser() {
    }

    public static LocalDate parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Date string is null");
        }

        String trimmed = input.trim();
        for (DateTimeFormatter fmt : FORMATTERS) {
            try {
                return LocalDate.parse(trimmed, fmt);
            } catch (DateTimeParseException ignored) {
                // will try next format
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + input);
    }

}
