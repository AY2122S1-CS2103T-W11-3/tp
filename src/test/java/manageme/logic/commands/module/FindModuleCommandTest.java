package manageme.logic.commands.module;

import static manageme.commons.core.Messages.MESSAGE_MODULES_LISTED_OVERVIEW;
import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.testutil.TypicalManageMe.getTypicalManageMe;
import static manageme.testutil.TypicalModules.MODULE_A;
import static manageme.testutil.TypicalModules.MODULE_B;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.NameContainsKeywordsPredicate;
import manageme.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindModuleCommand}.
 */
public class FindModuleCommandTest {
    private Model model = new ModelManager(getTypicalManageMe(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalManageMe(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindModuleCommand findFirstCommand = new FindModuleCommand(firstPredicate);
        FindModuleCommand findSecondCommand = new FindModuleCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindModuleCommand findFirstCommandCopy = new FindModuleCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different module -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noModuleFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindModuleCommand command = new FindModuleCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());
    }

    @Test
    public void execute_multipleKeywords_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("CS2100 CS2103");
        FindModuleCommand command = new FindModuleCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MODULE_A, MODULE_B), model.getFilteredModuleList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
