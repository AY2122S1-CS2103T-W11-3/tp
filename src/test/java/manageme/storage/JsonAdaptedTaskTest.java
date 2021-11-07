package manageme.storage;

import static manageme.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import manageme.commons.exceptions.IllegalValueException;
import manageme.model.Name;
import manageme.testutil.Assert;
import manageme.testutil.TypicalTasks;

public class JsonAdaptedTaskTest {
    private static final String INVALID_NAME = " ";
    //private static final String INVALID_DESCRIPTION = " ";
    //private static final String INVALID_MODULE = "example.com";
    private static final String INVALID_START = "10-10-2021";
    private static final String INVALID_END = "11-11-2021";

    private static final String VALID_NAME = TypicalTasks.TASK_A.getName().toString();
    private static final String VALID_DESCRIPTION = TypicalTasks.TASK_A.getDescription().toString();
    private static final String VALID_ISDONE = TypicalTasks.TASK_A.isDone().toString();
    private static final String VALID_MODULE = TypicalTasks.TASK_A.getTagModule().toString();
    private static final String VALID_START = TypicalTasks.TASK_A.getStart().toString();
    private static final String VALID_END = TypicalTasks.TASK_A.getEnd().toString();
    //private static final List<JsonAdaptedTag> VALID_END = TASK_A.getEnd().stream()
    //       .map(JsonAdaptedTag::new)
    //        .collect(Collectors.toList());V

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(TypicalTasks.TASK_A);
        assertEquals(TypicalTasks.TASK_A, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(INVALID_NAME, VALID_DESCRIPTION, VALID_ISDONE, VALID_MODULE, VALID_START,
                        VALID_END);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_DESCRIPTION, VALID_ISDONE, VALID_MODULE,
                VALID_START, VALID_END);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    //@Test
    //public void toModelType_invalidTask_throwsIllegalValueException() {
    //    JsonAdaptedTask task =
    //            new JsonAdaptedTask(VALID_NAME, VALID_DESCRIPTION, VALID_MODULE, INVALID_START, VALID_END);
    //    String expectedMessage = TaskTime.MESSAGE_CONSTRAINTS;
    //    assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    //}

    //@Test
    //public void toModelType_nullEmail_throwsIllegalValueException() {
    //    JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_DESCRIPTION, null, VALID_START, VALID_END);
    //    String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
    //    assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    //}

    //@Test
    //public void toModelType_invalidAddress_throwsIllegalValueException() {
    //    JsonAdaptedTask task =
    //            new JsonAdaptedTask(VALID_NAME, VALID_DESCRIPTION, VALID_MODULE, INVALID_START, VALID_END);
    //    String expectedMessage = Address.MESSAGE_CONSTRAINTS;
    //   assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    //}

    //@Test
    //public void toModelType_nullAddress_throwsIllegalValueException() {
    //    JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_DESCRIPTION, VALID_MODULE, null, VALID_END);
    //    String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
    //    assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    //}

    //@Test
    //public void toModelType_invalidTags_throwsIllegalValueException() {
    //    List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_END);
    //    invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
    //    JsonAdaptedTask task =
    //            new JsonAdaptedTask(VALID_NAME, VALID_DESCRIPTION, VALID_MODULE, VALID_START, invalidTags);
    //    assertThrows(IllegalValueException.class, task::toModelType);
    //}

}
