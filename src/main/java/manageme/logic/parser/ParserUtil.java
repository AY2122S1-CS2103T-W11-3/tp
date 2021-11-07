package manageme.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import manageme.commons.core.index.Index;
import manageme.commons.util.StringUtil;
import manageme.logic.parser.exceptions.ParseException;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.LinkAddress;
import manageme.model.tag.Tag;
import manageme.model.task.TaskDescription;
import manageme.model.task.TaskTime;

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

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code LinkAddress}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Link} is invalid.
     */
    public static LinkAddress parseLinkAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!LinkAddress.isValidLinkAddress(trimmedAddress)) {
            throw new ParseException(LinkAddress.MESSAGE_CONSTRAINTS);
        }
        return new LinkAddress(trimmedAddress);
    }

    /**
     * Parses a {@code String link module} into a {@code TagModule}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code TagModule} is invalid.
     */
    public static manageme.model.TagModule parseLinkModule(String linkModule) throws ParseException {
        requireNonNull(linkModule);
        String trimmedM = linkModule.trim();
        if (!manageme.model.TagModule.isValidModule(trimmedM)) {
            throw new ParseException(TagModule.MESSAGE_CONSTRAINTS);
        }
        return new manageme.model.TagModule(trimmedM);
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

    /**
     * Parses a {@code String task description} into a {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code TaskDescription} is invalid.
     */
    public static TaskDescription parseTaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        String trimmedD = taskDescription.trim();
        //  if (!TaskDescription.isValidDescription(trimmedD)) {
        //       throw new ParseException(TaskDescription.MESSAGE_CONSTRAINTS);
        // }
        return new TaskDescription(trimmedD);
    }

    /**
     * Parses a {@code String task module} into a {@code TagModule}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code TagModule} is invalid.
     */
    public static TagModule parseTagModule(String tagModule) throws ParseException {
        requireNonNull(tagModule);
        String trimmedM = tagModule.trim();
        if (!TagModule.isValidModule(trimmedM)) {
            throw new ParseException(TagModule.MESSAGE_CONSTRAINTS);
        }
        return new TagModule(trimmedM);
    }

    /**
     * Parses a {@code String date/time} into a {@code TaskTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code TaskTime} is invalid.
     */
    public static TaskTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!TaskTime.isValidTaskTime(trimmedDateTime)) {
            throw new ParseException(TaskTime.MESSAGE_CONSTRAINTS);
        }
        return new TaskTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LocalDate} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();

        try {
            LocalDate parsedDate = LocalDate.parse(trimmedDate);
            return parsedDate;
        } catch (DateTimeException dte) {
            throw new ParseException("The date entered is in the incorrect format");
        }
    }
}
