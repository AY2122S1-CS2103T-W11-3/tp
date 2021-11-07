package manageme.logic.parser;

import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_NAME_A;
import static manageme.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static manageme.testutil.Assert.assertThrows;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import manageme.logic.parser.exceptions.ParseException;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.tag.Tag;
import manageme.model.task.TaskDescription;
import manageme.model.task.TaskTime;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    private static final String VALID_TASKDESCRIPTION = "immediately";
    private static final String VALID_TagModule = "CS2103T";
    private static final String VALID_TASKTIME = "2021-10-05T11:40";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME_A);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME_A));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME_A + WHITESPACE;
        Name expectedName = new Name(VALID_NAME_A);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTaskDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTaskDescription((String) null));
    }
    @Test
    public void parseTaskDescription_validValueWithoutWhitespace_returnsTaskDescription() throws Exception {
        TaskDescription expectedTaskDescription = new TaskDescription(VALID_TASKDESCRIPTION);
        assertEquals(expectedTaskDescription, ParserUtil.parseTaskDescription(VALID_TASKDESCRIPTION));
    }

    @Test
    public void parseTaskDescription_validValueWithWhitespace_returnsTrimmedTaskDescription() throws Exception {
        String taskDescriptionWithWhitespace = WHITESPACE + VALID_TASKDESCRIPTION + WHITESPACE;
        TaskDescription expectedTaskDescription = new TaskDescription(VALID_TASKDESCRIPTION);
        assertEquals(expectedTaskDescription, ParserUtil.parseTaskDescription(taskDescriptionWithWhitespace));
    }

    @Test
    public void parseTagModule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTagModule((String) null));
    }
    @Test
    public void parseTagModule_validValueWithoutWhitespace_returnsTagModule() throws Exception {
        TagModule expectedTagModule = new TagModule(VALID_TagModule);
        assertEquals(expectedTagModule, ParserUtil.parseTagModule(VALID_TagModule));
    }

    @Test
    public void parseTagModule_validValueWithWhitespace_returnsTrimmedTagModule() throws Exception {
        String TagModuleWithWhitespace = WHITESPACE + VALID_TagModule + WHITESPACE;
        TagModule expectedTagModule = new TagModule(VALID_TagModule);
        assertEquals(expectedTagModule, ParserUtil.parseTagModule(TagModuleWithWhitespace));
    }

    @Test
    public void parseTaskTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime((String) null));
    }
    @Test
    public void parseTaskTime_validValueWithoutWhitespace_returnsTaskTime() throws Exception {
        TaskTime expectedTaskTime = new TaskTime(VALID_TASKTIME);
        assertEquals(expectedTaskTime, ParserUtil.parseDateTime(VALID_TASKTIME));
    }

    @Test
    public void parseTaskTime_validValueWithWhitespace_returnsTrimmedTaskTime() throws Exception {
        String taskTimeWithWhitespace = WHITESPACE + VALID_TASKTIME + WHITESPACE;
        TaskTime expectedTaskTime = new TaskTime(VALID_TASKTIME);
        assertEquals(expectedTaskTime, ParserUtil.parseDateTime(taskTimeWithWhitespace));
    }
}
