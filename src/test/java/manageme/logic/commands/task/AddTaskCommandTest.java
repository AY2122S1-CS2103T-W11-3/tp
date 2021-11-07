package manageme.logic.commands.task;

import static java.util.Objects.requireNonNull;
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
import manageme.model.ReadOnlyManageMe;
import manageme.model.ReadOnlyUserPrefs;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.module.Module;
import manageme.model.task.Task;
import manageme.testutil.TaskBuilder;

public class AddTaskCommandTest {
    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        AddTaskCommandTest.ModelStubAcceptingTaskAdded modelStub =
                new AddTaskCommandTest.ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = new AddTaskCommand(validTask).execute(modelStub);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task validTask = new TaskBuilder().build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask);
        AddTaskCommandTest.ModelStub modelStub = new AddTaskCommandTest.ModelStubWithTask(validTask);

        assertThrows(CommandException.class, AddTaskCommand.MESSAGE_DUPLICATE_TASK, ()
            -> addTaskCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Task taskA = new TaskBuilder().withName("TaskA").build();
        Task taskB = new TaskBuilder().withName("TaskB").build();
        AddTaskCommand addACommand = new AddTaskCommand(taskA);
        AddTaskCommand addBCommand = new AddTaskCommand(taskB);

        // same object -> returns true
        assertTrue(addACommand.equals(addACommand));

        // same values -> returns true
        AddTaskCommand addACommandCopy = new AddTaskCommand(taskA);
        assertTrue(addACommand.equals(addACommandCopy));

        // different types -> returns false
        assertFalse(addACommand.equals(1));

        // null -> returns false
        assertFalse(addACommand.equals(null));

        // different link -> returns false
        assertFalse(addACommand.equals(addBCommand));
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
     * A Model stub that contains a single link.
     */
    private class ModelStubWithTask extends AddTaskCommandTest.ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public <T extends ManageMeObject> boolean has(T task) {
            requireNonNull(task);
            return this.task.isSame(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends AddTaskCommandTest.ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public <T extends ManageMeObject> boolean has(T task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSame);
        }

        @Override
        public <T extends ManageMeObject> void add(T task) {
            requireNonNull(task);
            tasksAdded.add((Task) task);
        }

        @Override
        public ReadOnlyManageMe getManageMe() {
            return new ManageMe();
        }
    }
}
