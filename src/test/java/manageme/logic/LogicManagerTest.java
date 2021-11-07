package manageme.logic;

import static manageme.commons.core.Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX;
import static manageme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static manageme.logic.commands.module.ModuleCommandTestUtil.MODNAME_DESC_A;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.logic.commands.module.AddModuleCommand;
import manageme.logic.commands.module.ListModuleCommand;
import manageme.logic.parser.exceptions.ParseException;
import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.ReadOnlyManageMe;
import manageme.model.UserPrefs;
import manageme.model.module.Module;
import manageme.storage.JsonManageMeStorage;
import manageme.storage.JsonUserPrefsStorage;
import manageme.storage.StorageManager;
import manageme.testutil.Assert;
import manageme.testutil.ModuleBuilder;
import manageme.testutil.TypicalModules;
import manageme.time.TimeManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonManageMeStorage manageMeStorage =
                new JsonManageMeStorage(temporaryFolder.resolve("ManageMe.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(manageMeStorage, userPrefsStorage);
        TimeManager time = new TimeManager(model.getManageMe());
        logic = new LogicManager(model, storage, time);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteModCommand = "deleteMod 9";
        assertCommandException(deleteModCommand, MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listModCommand = ListModuleCommand.COMMAND_WORD;
        assertCommandSuccess(listModCommand, ListModuleCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonManageMeIoExceptionThrowingStub
        JsonManageMeStorage manageMeStorage =
                new JsonManageMeIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionManageMe.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(manageMeStorage, userPrefsStorage);
        TimeManager time = new TimeManager(model.getManageMe());
        logic = new LogicManager(model, storage, time);

        // Execute add command
        String addModuleCommand = AddModuleCommand.COMMAND_WORD + MODNAME_DESC_A;
        Module expectedModule = new ModuleBuilder(TypicalModules.MODULE_A).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.add(expectedModule);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addModuleCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredLinkList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredLinkList().remove(0));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredModuleList().remove(0));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTaskList().remove(0));
    }

    @Test
    public void getUnfilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getUnfilteredTaskList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getManageMe(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonManageMeIoExceptionThrowingStub extends JsonManageMeStorage {
        private JsonManageMeIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveManageMe(ReadOnlyManageMe manageMe, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
