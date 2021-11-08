//package manageme.logic.commands;
//
//import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static manageme.testutil.TypicalManageMe.getTypicalManageMe;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import manageme.model.Model;
//import manageme.model.ModelManager;
//import manageme.model.UserPrefs;
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
// */
//public class ListCommandTest {
//
//    private Model model;
//    private Model expectedModel;
//
//    @BeforeEach
//    public void setUp() {
//        model = new ModelManager(getTypicalManageMe(), new UserPrefs());
//        expectedModel = new ModelManager(model.getManageMe(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_listIsNotFiltered_showsSameList() {
//        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_listIsFiltered_showsEverything() {
//        showPersonAtIndex(model, INDEX_FIRST);
//        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//}
