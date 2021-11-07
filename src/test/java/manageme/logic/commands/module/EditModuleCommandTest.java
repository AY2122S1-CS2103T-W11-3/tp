package manageme.logic.commands.module;

import static manageme.logic.commands.CommandTestUtil.assertCommandFailure;
import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.logic.commands.CommandTestUtil.showModuleAtIndex;
import static manageme.logic.commands.module.ModuleCommandTestUtil.DESC_MODULE_A;
import static manageme.logic.commands.module.ModuleCommandTestUtil.DESC_MODULE_B;
import static manageme.logic.commands.module.ModuleCommandTestUtil.VALID_MODNAME_A;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalIndexes.INDEX_SECOND;
import static manageme.testutil.TypicalModules.getTypicalManageMe;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.module.EditModuleCommand.EditModuleDescriptor;
import manageme.model.ManageMe;
import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.UserPrefs;
import manageme.model.module.Module;
import manageme.testutil.EditModuleDescriptorBuilder;
import manageme.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditModuleCommand.
 */
public class EditModuleCommandTest {
    private Model model = new ModelManager(getTypicalManageMe(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Module editedModule = new ModuleBuilder().build();
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(model.getFilteredModuleList().get(0), editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastModule = Index.fromOneBased(model.getFilteredModuleList().size());
        Module lastModule = model.getFilteredModuleList().get(indexLastModule.getZeroBased());

        ModuleBuilder moduleInList = new ModuleBuilder(lastModule);
        Module editedModule = moduleInList.withName("CS2105").build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName("CS2105").build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(indexLastModule, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(lastModule, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST, new EditModuleDescriptor());
        Module editedModule = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(editModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleAtIndex(model, INDEX_FIRST);

        Module moduleInFilteredList = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleInFilteredList).withName("CS2105").build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST,
                new EditModuleDescriptorBuilder().withName("CS2105").build());

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(model.getFilteredModuleList().get(0), editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateModuleUnfilteredList_failure() {
        Module firstModule = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editModuleCommand, model, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicateModuleFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST);

        // edit module  in filtered list into a duplicate
        Module moduleInList = model.getManageMe().getModuleList().get(INDEX_SECOND.getZeroBased());
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST,
                new EditModuleDescriptorBuilder(moduleInList).build());

        assertCommandFailure(editModuleCommand, model, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_invalidModuleIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_MODNAME_A).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editModuleCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of ManageMe
     */
    @Test
    public void execute_invalidModuleIndexFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of ManageMe list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getManageMe().getModuleList().size());

        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex,
                new EditModuleDescriptorBuilder().withName(VALID_MODNAME_A).build());

        assertCommandFailure(editModuleCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditModuleCommand standardCommand = new EditModuleCommand(INDEX_FIRST, DESC_MODULE_A);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleCommand.EditModuleDescriptor(DESC_MODULE_A);
        EditModuleCommand commandWithSameValues = new EditModuleCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(5));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_SECOND, DESC_MODULE_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_FIRST, DESC_MODULE_B)));
    }

}
