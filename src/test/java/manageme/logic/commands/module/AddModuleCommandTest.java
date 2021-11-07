package manageme.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static manageme.logic.commands.module.ModuleCommandTestUtil.VALID_MODNAME_A;
import static manageme.logic.commands.module.ModuleCommandTestUtil.VALID_MODNAME_B;
import static manageme.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import manageme.commons.core.GuiSettings;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.ManageMe;
import manageme.model.ManageMeObject;
import manageme.model.Model;
import manageme.model.Name;
import manageme.model.ReadOnlyManageMe;
import manageme.model.ReadOnlyUserPrefs;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.module.Module;
import manageme.model.task.Task;
import manageme.testutil.ModuleBuilder;

public class AddModuleCommandTest {
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Name validName = new Name(VALID_MODNAME_A);
        Module validModule = new ModuleBuilder().withName(VALID_MODNAME_A).build();

        CommandResult commandResult = new AddModuleCommand(validName).execute(modelStub);

        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS, validModule), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Name validName = new Name(VALID_MODNAME_A);
        Module validModule = new ModuleBuilder().withName(VALID_MODNAME_A).build();
        AddModuleCommand addCommand = new AddModuleCommand(validName);
        ModelStub modelStub = new AddModuleCommandTest.ModelStubWithModule(validModule);

        assertThrows(CommandException.class, AddModuleCommand.MESSAGE_DUPLICATE_MODULE, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Name cs2100 = new Name(VALID_MODNAME_A);
        Name cs2103 = new Name(VALID_MODNAME_B);
        AddModuleCommand addCs2100Command = new AddModuleCommand(cs2100);
        AddModuleCommand addCs2103Command = new AddModuleCommand(cs2103);

        // same object -> returns true
        assertTrue(addCs2100Command.equals(addCs2100Command));

        // same values -> returns true
        AddModuleCommand addCs2100CommandCopy = new AddModuleCommand(cs2100);
        assertTrue(addCs2100Command.equals(addCs2100CommandCopy));

        // different types -> returns false
        assertFalse(addCs2100Command.equals(1));

        // null -> returns false
        assertFalse(addCs2100Command.equals(null));

        // different module -> returns false
        assertFalse(addCs2100Command.equals(addCs2103Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getManageMeFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setManageMeFilePath(Path manageMeFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setManageMe(ReadOnlyManageMe newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyManageMe getManageMe() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends ManageMeObject> boolean has(T target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends ManageMeObject> void delete(T target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends ManageMeObject> void set(T target, T edited) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends ManageMeObject> void add(T target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editModuleInLinksWithModule(Module target, TagModule newTagModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void openLink(Link target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Link> getFilteredLinkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLinkList(Predicate<Link> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Module> getReadModule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReadModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editModuleInTasksWithModule(Module target, manageme.model.TagModule newTagModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getUnfilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Link> getUnfilteredLinkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends AddModuleCommandTest.ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public <T extends ManageMeObject> boolean has(T module) {
            requireNonNull(module);
            return this.module.isSame(module);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends AddModuleCommandTest.ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public <T extends ManageMeObject> boolean has(T module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSame);
        }

        public <T extends ManageMeObject> void add(T module) {
            requireNonNull(module);
            modulesAdded.add((Module) module);
        }

        @Override
        public ReadOnlyManageMe getManageMe() {
            return new ManageMe();
        }
    }
}
