package manageme.logic.commands.link;

import static manageme.logic.commands.CommandTestUtil.assertCommandFailure;
import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.logic.commands.CommandTestUtil.showLinkAtIndex;
import static manageme.logic.commands.link.LinkCommandTestUtil.DESC_LINK_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.DESC_LINK_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_NAME_A;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalIndexes.INDEX_SECOND;
import static manageme.testutil.TypicalManageMe.getTypicalManageMe;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.ClearCommand;
import manageme.logic.commands.link.EditLinkCommand.EditLinkDescriptor;
import manageme.model.ManageMe;
import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.UserPrefs;
import manageme.model.link.Link;
import manageme.testutil.EditLinkDescriptorBuilder;
import manageme.testutil.LinkBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditLinkCommandTest {

    private Model model = new ModelManager(getTypicalManageMe(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Link editedLink = new LinkBuilder().build();
        EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder(editedLink).build();
        EditLinkCommand editLinkCommand = new EditLinkCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditLinkCommand.MESSAGE_EDIT_LINK_SUCCESS, editedLink);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(model.getFilteredLinkList().get(0), editedLink);

        assertCommandSuccess(editLinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLink = Index.fromOneBased(model.getFilteredLinkList().size());
        Link lastLink = model.getFilteredLinkList().get(indexLastLink.getZeroBased());

        LinkBuilder linkInList = new LinkBuilder(lastLink);
        Link editedLink = linkInList.withName(VALID_NAME_A).build();

        EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder().withName(VALID_NAME_A).build();
        EditLinkCommand editLinkCommand = new EditLinkCommand(indexLastLink, descriptor);

        String expectedMessage = String.format(EditLinkCommand.MESSAGE_EDIT_LINK_SUCCESS, editedLink);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(lastLink, editedLink);

        assertCommandSuccess(editLinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditLinkCommand editLinkCommand = new EditLinkCommand(INDEX_FIRST, new EditLinkDescriptor());
        Link editedLink = model.getFilteredLinkList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditLinkCommand.MESSAGE_EDIT_LINK_SUCCESS, editedLink);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());

        assertCommandSuccess(editLinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLinkAtIndex(model, INDEX_FIRST);

        Link linkInFilteredList = model.getFilteredLinkList().get(INDEX_FIRST.getZeroBased());
        Link editedLink = new LinkBuilder(linkInFilteredList).withName(VALID_NAME_A).build();
        EditLinkCommand editLinkCommand = new EditLinkCommand(INDEX_FIRST,
                new EditLinkDescriptorBuilder().withName(VALID_NAME_A).build());

        String expectedMessage = String.format(EditLinkCommand.MESSAGE_EDIT_LINK_SUCCESS, editedLink);

        Model expectedModel = new ModelManager(new ManageMe(model.getManageMe()), new UserPrefs());
        expectedModel.set(model.getFilteredLinkList().get(0), editedLink);

        assertCommandSuccess(editLinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLinkUnfilteredList_failure() {
        Link firstLink = model.getFilteredLinkList().get(INDEX_FIRST.getZeroBased());
        EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder(firstLink).build();
        EditLinkCommand editLinkCommand = new EditLinkCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editLinkCommand, model, EditLinkCommand.MESSAGE_DUPLICATE_LINK);
    }

    @Test
    public void execute_duplicateLinkFilteredList_failure() {
        showLinkAtIndex(model, INDEX_FIRST);

        // edit link in filtered list into a duplicate in address book
        Link linkInList = model.getManageMe().getLinkList().get(INDEX_SECOND.getZeroBased());
        EditLinkCommand editLinkCommand = new EditLinkCommand(INDEX_FIRST,
                new EditLinkDescriptorBuilder(linkInList).build());

        assertCommandFailure(editLinkCommand, model, EditLinkCommand.MESSAGE_DUPLICATE_LINK);
    }

    @Test
    public void execute_invalidLinkIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLinkList().size() + 1);
        EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder().withName(VALID_NAME_A).build();
        EditLinkCommand editLinkCommand = new EditLinkCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editLinkCommand, model, Messages.MESSAGE_INVALID_LINK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidLinkIndexFilteredList_failure() {
        showLinkAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getManageMe().getLinkList().size());

        EditLinkCommand editLinkCommand = new EditLinkCommand(outOfBoundIndex,
                new EditLinkDescriptorBuilder().withName(VALID_NAME_A).build());

        assertCommandFailure(editLinkCommand, model, Messages.MESSAGE_INVALID_LINK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditLinkCommand standardCommand = new EditLinkCommand(INDEX_FIRST, DESC_LINK_A);

        // same values -> returns true
        EditLinkDescriptor copyDescriptor = new EditLinkDescriptor(DESC_LINK_A);
        EditLinkCommand commandWithSameValues = new EditLinkCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditLinkCommand(INDEX_SECOND, DESC_LINK_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditLinkCommand(INDEX_FIRST, DESC_LINK_B)));
    }

}
