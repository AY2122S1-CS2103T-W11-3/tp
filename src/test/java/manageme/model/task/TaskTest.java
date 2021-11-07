package manageme.model.task;

import static manageme.testutil.Assert.assertThrows;
import static manageme.testutil.TypicalTasks.TASK_A;
import static manageme.testutil.TypicalTasks.TASK_B;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.task.TaskCommandTestUtil;
import manageme.testutil.TaskBuilder;

public class TaskTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(TASK_A.getName(), TASK_A.getDescription(),
                null, null, null));
    }

    @Test
    public void constructor_withoutTaskIsDone_returnsTaskNotDone() {
        assertFalse(new Task(TASK_A.getName(), TASK_A.getDescription(), TASK_A.getTagModule(),
                TASK_A.getStart(), TASK_A.getEnd()).isDone().value);
    }

    @Test
    public void getSpan() {
        //no start/end datetime, returns empty stream
        Task noDateTask = new TaskBuilder().withStartDateTime("").withEndDateTime("").build();
        assertFalse(noDateTask.getSpan().findAny().isPresent());
    }

    @Test
    public void getFirstOccurence() {
        //Task with only end TaskTime, returns end datetime
        Task onlyEndTask = new TaskBuilder(TASK_A).withStartDateTime("").build();
        assertTrue(onlyEndTask.getFirstOccurrence().equals(LocalDateTime.parse("2021-10-05T14:00")));

        //Task with both start and end TaskTime, returns start datetime
        assertTrue(TASK_A.getFirstOccurrence().equals(LocalDateTime.parse("2021-10-05T11:50")));
    }

    @Test
    public void isSame() {
        // same object -> returns true
        assertTrue(TASK_A.isSame(TASK_A));

        // null -> returns false
        assertFalse(TASK_A.isSame(null));

        // same name, all other attributes different -> returns true
        Task editedA = new TaskBuilder(TASK_A).withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_B).withModule(
                        TaskCommandTestUtil.VALID_MODULE_B)
                .withStartDateTime(TaskCommandTestUtil.VALID_START_B).withEndDateTime(TaskCommandTestUtil.VALID_END_B)
                .build();
        assertTrue(TASK_A.isSame(editedA));

        // different name, all other attributes same -> returns false
        editedA = new TaskBuilder(TASK_A).withName(TaskCommandTestUtil.VALID_NAME_B).build();
        assertFalse(TASK_A.isSame(editedA));

        // name differs in case, all other attributes same -> returns false
        Task editedB = new TaskBuilder(TASK_B).withName(TaskCommandTestUtil.VALID_NAME_B.toLowerCase()).build();
        assertFalse(TASK_B.isSame(editedB));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = TaskCommandTestUtil.VALID_NAME_B + " ";
        editedB = new TaskBuilder(TASK_B).withName(nameWithTrailingSpaces).build();
        assertFalse(TASK_B.isSame(editedB));
    }
    @Test
    public void equals() {
        // same values -> returns true
        Task copyA = new TaskBuilder(TASK_A).build();
        assertTrue(TASK_A.equals(copyA));

        // same object -> returns true
        assertTrue(TASK_A.equals(TASK_A));

        // null -> returns false
        assertFalse(TASK_A.equals(null));

        // different type -> returns false
        assertFalse(TASK_A.equals(5));

        // different person -> returns false
        assertFalse(TASK_A.equals(TASK_B));

        // different name -> returns false
        Task editedA = new TaskBuilder(TASK_A).withName(TaskCommandTestUtil.VALID_NAME_B).build();
        assertFalse(TASK_A.equals(editedA));

        // different description -> returns false
        editedA = new TaskBuilder(TASK_A).withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_B).build();
        assertFalse(TASK_A.equals(editedA));

        // different module -> returns false
        editedA = new TaskBuilder(TASK_A).withModule(TaskCommandTestUtil.VALID_MODULE_B).build();
        assertFalse(TASK_A.equals(editedA));

        // different start date/time -> returns false
        editedA = new TaskBuilder(TASK_A).withStartDateTime(TaskCommandTestUtil.VALID_START_B).build();
        assertFalse(TASK_A.equals(editedA));

        // different end date/time -> returns false
        editedA = new TaskBuilder(TASK_A).withEndDateTime(TaskCommandTestUtil.VALID_END_B).build();
        assertFalse(TASK_A.equals(editedA));
    }
}
