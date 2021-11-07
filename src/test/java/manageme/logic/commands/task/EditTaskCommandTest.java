package manageme.logic.commands.task;

import static manageme.logic.commands.CommandTestUtil.assertCommandFailure;
import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.logic.commands.CommandTestUtil.showTaskAtIndex;
import static manageme.logic.commands.task.TaskCommandTestUtil.DESC_A;
import static manageme.logic.commands.task.TaskCommandTestUtil.DESC_B;
import static manageme.logic.commands.task.TaskCommandTestUtil.INVALID_END_A;
import static manageme.logic.commands.task.TaskCommandTestUtil.INVALID_LONG_DESCRIPTION;
import static manageme.logic.commands.task.TaskCommandTestUtil.INVALID_LONG_NAME;
import static manageme.logic.commands.task.TaskCommandTestUtil.INVALID_START_A;
import static manageme.logic.commands.task.TaskCommandTestUtil.VALID_DESCRIPTION_B;
import static manageme.logic.commands.task.TaskCommandTestUtil.VALID_MODULE_B;
import static manageme.logic.commands.task.TaskCommandTestUtil.VALID_NAME_B;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalIndexes.INDEX_SECOND;
import static manageme.testutil.TypicalManageMe.getTypicalManageMe;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.ClearCommand;
import manageme.model.ManageMe;
import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.UserPrefs;
import manageme.model.task.Task;
import manageme.testutil.EditTaskDescriptorBuilder;
import manageme.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {
    private Model model = new ModelManager(getTypicalManageMe(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withName(VALID_NAME_B).withDescription(VALID_DESCRIPTION_B)
                .withModule(VALID_MODULE_B).build();

        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withName(VALID_NAME_B)
                .withDescription(VALID_DESCRIPTION_B).withModule(VALID_MODULE_B).build();
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_FIRST, new EditTaskCommand.EditTaskDescriptor());

        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_filteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withName(VALID_NAME_B).build();
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_B).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withName(VALID_NAME_B).build();
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getManageMe().getTaskList().size());

        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_B).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidNameLength_failure() {
        showTaskAtIndex(model, INDEX_FIRST);
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder().withName(INVALID_LONG_NAME).build());

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_TASK_NAME_TOO_LONG);
    }

    @Test
    public void execute_invalidDescriptionLength_failure() {
        showTaskAtIndex(model, INDEX_FIRST);
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder().withDescription(INVALID_LONG_DESCRIPTION).build());

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_TASK_DESCRIPTION_TOO_LONG);
    }

    @Test
    public void execute_invalidStartAndEndDate_failure() {
        showTaskAtIndex(model, INDEX_FIRST);
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder()
                        .withEndDateTime(INVALID_END_A)
                        .withStartDateTime(INVALID_START_A).build());

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_START_LATER_THAN_END);
    }

    @Test
    public void execute_invalidStartWithoutEndDate_failure() {
        showTaskAtIndex(model, INDEX_FIRST);
        EditTaskCommand editTaskCommand = null;
        editTaskCommand = new EditTaskCommand(INDEX_FIRST,
                new EditTaskDescriptorBuilder()
                        .withEndDateTime("")
                        .withStartDateTime(INVALID_START_A).build());

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_START_WITHOUT_END);
    }

    @Test
    public void equals() {
        EditTaskCommand standardCommand = null;
        standardCommand = new EditTaskCommand(INDEX_FIRST, DESC_A);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor = new EditTaskCommand.EditTaskDescriptor(DESC_A);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND, DESC_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST, DESC_B)));
    }
}
