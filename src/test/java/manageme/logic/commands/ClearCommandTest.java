package manageme.logic.commands;

import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.testutil.TypicalManageMe.getTypicalManageMe;

import org.junit.jupiter.api.Test;

import manageme.model.ManageMe;
import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyManageMe_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyManageMe_success() {
        Model model = new ModelManager(getTypicalManageMe(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalManageMe(), new UserPrefs());
        expectedModel.setManageMe(new ManageMe());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
