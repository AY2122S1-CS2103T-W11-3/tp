package manageme.logic.commands.link;

import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.logic.commands.CommandTestUtil.showLinkAtIndex;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalManageMe.getTypicalManageMe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.UserPrefs;

public class ListLinkCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalManageMe(), new UserPrefs());
        expectedModel = new ModelManager(model.getManageMe(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListLinkCommand(), model, ListLinkCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showLinkAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListLinkCommand(), model, ListLinkCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
