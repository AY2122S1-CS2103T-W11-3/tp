package manageme.logic.commands.link;

import static manageme.logic.commands.CommandTestUtil.assertCommandFailure;
import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.logic.commands.CommandTestUtil.showLinkAtIndex;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalIndexes.INDEX_SECOND;
import static manageme.testutil.TypicalManageMe.getTypicalManageMe;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.UserPrefs;
import manageme.model.link.Link;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteLinkCommandTest {

    private Model model = new ModelManager(getTypicalManageMe(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Link linkToDelete = model.getFilteredLinkList().get(INDEX_FIRST.getZeroBased());
        DeleteLinkCommand deleteLinkCommand = new DeleteLinkCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteLinkCommand.MESSAGE_DELETE_LINK_SUCCESS, linkToDelete);

        ModelManager expectedModel = new ModelManager(model.getManageMe(), new UserPrefs());
        expectedModel.delete(linkToDelete);

        assertCommandSuccess(deleteLinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLinkList().size() + 1);
        DeleteLinkCommand deleteLinkCommand = new DeleteLinkCommand(outOfBoundIndex);

        assertCommandFailure(deleteLinkCommand, model, Messages.MESSAGE_INVALID_LINK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showLinkAtIndex(model, INDEX_FIRST);
        Link linkToDelete = model.getFilteredLinkList().get(INDEX_FIRST.getZeroBased());
        DeleteLinkCommand deleteLinkCommand = new DeleteLinkCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteLinkCommand.MESSAGE_DELETE_LINK_SUCCESS, linkToDelete);

        Model expectedModel = new ModelManager(model.getManageMe(), new UserPrefs());
        expectedModel.delete(linkToDelete);
        showNoLink(expectedModel);

        assertCommandSuccess(deleteLinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showLinkAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getManageMe().getLinkList().size());

        DeleteLinkCommand deleteLinkCommand = new DeleteLinkCommand(outOfBoundIndex);

        assertCommandFailure(deleteLinkCommand, model, Messages.MESSAGE_INVALID_LINK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLinkCommand deleteFirstCommand = new DeleteLinkCommand(INDEX_FIRST);
        DeleteLinkCommand deleteSecondCommand = new DeleteLinkCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLinkCommand deleteFirstCommandCopy = new DeleteLinkCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different link -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoLink(Model model) {
        model.updateFilteredLinkList(p -> false);

        assertTrue(model.getFilteredLinkList().isEmpty());
    }
}
