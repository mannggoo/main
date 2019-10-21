package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse string Date Time in the standard way using LocalDateTime.parse().
 */
public class StandardDateTimeParser implements DateTimeParser {

    /**
     * Parse string using LocalDateTime.parse()
     * @param stringDateTime of the unprocessed date time string
     * @return LocalDateTime representation of this string
     * @throws DateTimeParseException if the format of this string is incorrect
     */
    public LocalDateTime parseDateTime(String stringDateTime) throws ParseException {
        try {
            return LocalDateTime.parse(stringDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage());
        }
    }
}