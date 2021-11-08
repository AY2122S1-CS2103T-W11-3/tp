package manageme.logic.commands;

//import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static manageme.testutil.TypicalManageMe.getTypicalManageMe;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import manageme.model.Model;
//import manageme.model.ModelManager;
//import manageme.model.UserPrefs;
//import manageme.model.person.Person;
//import manageme.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

//    private Model model;
//
//    @BeforeEach
//    public void setUp() {
//        model = new ModelManager(getTypicalManageMe(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_newPerson_success() {
//        Person validPerson = new PersonBuilder().build();
//
//        Model expectedModel = new ModelManager(model.getManageMe(), new UserPrefs());
//        expectedModel.addPerson(validPerson);
//
//        assertCommandSuccess(new AddCommand(validPerson), model,
//                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() {
//        Person personInList = model.getManageMe().getPersonList().get(0);
//        CommandTestUtil.assertCommandFailure(new AddCommand(personInList), model,
//        AddCommand.MESSAGE_DUPLICATE_PERSON);
//    }

}
