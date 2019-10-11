package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.core.item.Item.ItemBuilder;
import seedu.address.commons.core.item.ItemDescription;
import seedu.address.commons.core.item.Priority;
import seedu.address.commons.core.item.Reminder;
import seedu.address.commons.core.item.Task;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    public static ItemDescription parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!ItemDescription.isValidItemDescription(trimmedDescription)) {
            throw new ParseException(ItemDescription.MESSAGE_CONSTRAINTS);
        }
        return new ItemDescription(trimmedDescription);
    }

    public static Optional<Event> parseDateTime(String dateTime) throws ParseException {
        if (dateTime == null) {
            return Optional.empty();
        }

        String trimmedDateTime = dateTime.trim();
        LocalDateTime formattedDateTime;

        try {
            formattedDateTime = LocalDateTime.parse(trimmedDateTime);
        } catch(DateTimeParseException e) {
            throw new ParseException("Date Time format given is incorrect. " +
                    "Please follow this format: \"-d 2018-12-30T19:34:50.63\"");
            //throw new ParseException(Event.MESSAGE_CONSTRAINTS);
        }

        Event newEvent = new Event(formattedDateTime, null, null);

        return Optional.of(newEvent);
    }

    public static Optional<Reminder> parseReminder(String reminder) throws ParseException {
        if (reminder == null) {
            return Optional.empty();
        }

        String trimmedDateTime = reminder.trim();
        LocalDateTime formattedDateTime;

        try {
            formattedDateTime = LocalDateTime.parse(trimmedDateTime);
        } catch(DateTimeParseException e) {
            throw new ParseException("Date Time format given is incorrect. " +
                    "Please follow this format: \"-r 2018-12-30T19:34:50.63\"");
            //throw new ParseException(Event.MESSAGE_CONSTRAINTS);
        }

        Reminder newReminder = new Reminder(formattedDateTime);
        return Optional.of(newReminder);
    }

    public static Optional<Priority> parsePriority(String priority) throws ParseException {
        if (priority == null) {
            return Optional.empty();
        }

        String trimmedPriority = priority.trim();
        Priority processedPriority;

        if (trimmedPriority.equalsIgnoreCase("HIGH")){
            processedPriority = Priority.HIGH;
        } else if (trimmedPriority.equalsIgnoreCase("MEDIUM")) {
            processedPriority = Priority.MEDIUM;
        } else if (trimmedPriority.equalsIgnoreCase("LOW")) {
            processedPriority = Priority.LOW;
        } else {
            throw new ParseException("Priority format given is incorrect. " +
                    "Please follow this format \"-p High\"");
        }

        return Optional.of(processedPriority); //maybe use enum here
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
