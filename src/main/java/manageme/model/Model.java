package manageme.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import manageme.commons.core.GuiSettings;
import manageme.model.link.Link;
import manageme.model.module.Module;
import manageme.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Link> PREDICATE_SHOW_ALL_LINKS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' ManageMe file path.
     */
    Path getManageMeFilePath();

    /**
     * Sets the user prefs' ManageMe file path.
     */
    void setManageMeFilePath(Path manageMeFilePath);

    /**
     * Replaces ManageMe data with the data in {@code manageMe}.
     */
    void setManageMe(ReadOnlyManageMe manageMe);

    /** Returns the ManageMe */
    ReadOnlyManageMe getManageMe();

    public <T extends ManageMeObject> boolean has(T target);

    public <T extends ManageMeObject> void delete(T target);

    public <T extends ManageMeObject> void set(T target, T edited);

    public <T extends ManageMeObject> void add(T target);

    /**
     * Opens the given link.
     * The link must exist in the ManageMe.
     */
    void openLink(Link target);

    /**
     * Replaces the module in tasks with modules matching the {@code target} with {@code newTagModule}.
     * {@code target} must exist in the ManageMe.
     */
    void editModuleInLinksWithModule(Module target, TagModule newTagModule);

    /** Returns an unmodifiable view of the filtered link list */
    ObservableList<Link> getFilteredLinkList();

    /** Returns an unmodifiable view of the filtered link list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered link list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLinkList(Predicate<Link> predicate);

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Returns the module to be read.
     */
    Optional<Module> getReadModule();

    /**
     * Updates the module to be read to {@code module}.
     */
    void setReadModule(Module module);

    /**
     * Replaces the module in tasks with modules matching the {@code target} with {@code newTagModule}.
     * {@code target} must exist in the ManageMe.
     */
    void editModuleInTasksWithModule(Module target, TagModule newTagModule);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /** Returns an unmodifiable view of the unfiltered task list */
    ObservableList<Task> getUnfilteredTaskList();

    /** Returns an unmodifiable view of the unfiltered link list */
    ObservableList<Link> getUnfilteredLinkList();
}
