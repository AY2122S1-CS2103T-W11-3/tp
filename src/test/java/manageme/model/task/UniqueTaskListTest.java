package manageme.model.task;

import static manageme.testutil.Assert.assertThrows;
import static manageme.testutil.TypicalTasks.TASK_A;
import static manageme.testutil.TypicalTasks.TASK_B;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.task.TaskCommandTestUtil;
import manageme.model.task.exceptions.DuplicateTaskException;
import manageme.model.task.exceptions.TaskNotFoundException;
import manageme.testutil.TaskBuilder;

public class UniqueTaskListTest {
    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(TASK_A));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(TASK_A);
        assertTrue(uniqueTaskList.contains(TASK_A));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(TASK_A);
        Task editedA =
                new TaskBuilder(TASK_A).withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_B).withStartDateTime(
                                TaskCommandTestUtil.VALID_START_B)
                .build();
        assertTrue(uniqueTaskList.contains(editedA));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniqueTaskList.add(TASK_A);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(TASK_A));
    }

    @Test
    public void set_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setT(null, TASK_A));
    }

    @Test
    public void set_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setT(TASK_A, null));
    }

    @Test
    public void set_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.setT(TASK_A, TASK_A));
    }

    @Test
    public void set_editedTaskisSame_success() {
        uniqueTaskList.add(TASK_A);
        uniqueTaskList.setT(TASK_A, TASK_A);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TASK_A);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void set_editedTaskHasSameIdentity_success() {
        uniqueTaskList.add(TASK_A);
        Task editedA = new TaskBuilder(TASK_A).withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_B).withEndDateTime(
                        TaskCommandTestUtil.VALID_END_B)
                .build();
        uniqueTaskList.setT(TASK_A, editedA);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(editedA);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void set_editedTaskHasDifferentIdentity_success() {
        uniqueTaskList.add(TASK_A);
        uniqueTaskList.setT(TASK_A, TASK_B);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TASK_B);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void set_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        uniqueTaskList.add(TASK_A);
        uniqueTaskList.add(TASK_B);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setT(TASK_A, TASK_B));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(TASK_A));
    }

    @Test
    public void remove_existingTask_removesTask() {
        uniqueTaskList.add(TASK_A);
        uniqueTaskList.remove(TASK_A);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void sets_nullUniqueTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTs((UniqueTaskList) null));
    }

    @Test
    public void sets_uniqueTaskList_replacesOwnListWithProvidedUniqueTaskList() {
        uniqueTaskList.add(TASK_A);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TASK_B);
        uniqueTaskList.setTs(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void sets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTs((List<Task>) null));
    }

    @Test
    public void sets_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(TASK_A);
        List<Task> taskList = Collections.singletonList(TASK_B);
        uniqueTaskList.setTs(taskList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TASK_B);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void sets_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(TASK_A, TASK_A);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTs(listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTaskList.asUnmodifiableObservableList().remove(0));
    }
}
