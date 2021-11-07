package manageme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import manageme.commons.core.GuiSettings;
import manageme.commons.core.LogsCenter;
import manageme.commons.util.CollectionUtil;
import manageme.model.link.Link;
import manageme.model.module.Module;
import manageme.model.task.Task;

/**
 * Represents the in-memory model of the ManageMe data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ManageMe manageMe;
    private final UserPrefs userPrefs;
    private final FilteredList<Link> filteredLinks;
    private final FilteredList<Link> unfilteredLinks;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> unfilteredTasks;
    private final FilteredList<Module> filteredModules;
    private final ArrayList<Task> modifiableUnfilteredTasks;
    private Optional<Module> readModule;

    /**
     * Initializes a ModelManager with the given manageMe and userPrefs.
     */
    public ModelManager(ReadOnlyManageMe manageMe, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(manageMe, userPrefs);

        logger.fine("Initializing with ManageMe: " + manageMe + " and user prefs " + userPrefs);

        this.manageMe = new ManageMe(manageMe);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredLinks = new FilteredList<>(this.manageMe.getLinkList());
        unfilteredLinks = new FilteredList<>(this.manageMe.getLinkList());
        filteredTasks = new FilteredList<>(this.manageMe.getTaskList());
        unfilteredTasks = new FilteredList<>(this.manageMe.getTaskList());
        filteredModules = new FilteredList<>(this.manageMe.getModuleList());
        readModule = Optional.ofNullable(null);

        // Time Manager use
        modifiableUnfilteredTasks = new ArrayList<Task>(this.manageMe.getModifiableTaskList());
    }

    public ModelManager() {
        this(new ManageMe(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getManageMeFilePath() {
        return userPrefs.getManageMeFilePath();
    }

    @Override
    public void setManageMeFilePath(Path manageMeFilePath) {
        requireNonNull(manageMeFilePath);
        userPrefs.setManageMeFilePath(manageMeFilePath);
    }

    //=========== ManageMe ================================================================================

    @Override
    public void setManageMe(ReadOnlyManageMe manageMe) {
        this.manageMe.resetData(manageMe);
    }

    @Override
    public ReadOnlyManageMe getManageMe() {
        return manageMe;
    }

    @Override
    public <T extends ManageMeObject> boolean has(T target) {
        requireNonNull(target);
        return manageMe.has(target);
    }

    @Override
    public <T extends ManageMeObject> void delete(T target) {
        requireNonNull(target);
        manageMe.remove(target);
    }

    @Override
    public <T extends ManageMeObject> void set(T target, T edited) {
        requireNonNull(target);
        manageMe.setT(target, edited);
    }

    @Override
    public <T extends ManageMeObject> void add(T target) {
        manageMe.add(target);
        if (target instanceof Module) {
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        } else if (target instanceof Link) {
            updateFilteredLinkList(PREDICATE_SHOW_ALL_LINKS);
        } else {
            updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        }
    }

    @Override
    public void openLink(Link target) {
        requireNonNull(target);
        manageMe.openLink(target);
    }

    @Override
    public void editModuleInLinksWithModule(Module target, TagModule newTagModule) {
        TagModule targetTagModule = new TagModule(target.getName().value);
        for (Link link: filteredLinks) {
            if (link.getLinkModule().equals(targetTagModule)) {
                Link sameLinkEditedModule = new Link(link.getName(), link.getAddress(),
                        newTagModule);
                set(link, sameLinkEditedModule);
            }
        }
    }

    @Override
    public void editModuleInTasksWithModule(Module target, TagModule newTagModule) {
        TagModule targetTagModule = new TagModule(target.getName().value);
        for (Task task: filteredTasks) {
            if (task.getTagModule().equals(targetTagModule)) {
                Task sameTaskEditedModule = new Task(task.getName(), task.getDescription(), task.isDone(),
                        newTagModule, task.getStart(), task.getEnd());
                set(task, sameTaskEditedModule);
            }
        }
    }

    /**
     * Returns an unmodifiable view of the list of {@code Link} backed by the internal list of
     * {@code versionedManageMe}
     */
    @Override
    public ObservableList<Link> getFilteredLinkList() {
        return filteredLinks;
    }

    @Override
    public void updateFilteredLinkList(Predicate<Link> predicate) {
        requireNonNull(predicate);
        filteredLinks.setPredicate(predicate);
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    @Override
    public Optional<Module> getReadModule() {
        return readModule;
    }

    @Override
    public void setReadModule(Module module) {
        requireNonNull(module);
        readModule = Optional.of(module);
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public ObservableList<Task> getUnfilteredTaskList() {
        return unfilteredTasks;
    }

    @Override
    public ObservableList<Link> getUnfilteredLinkList() {
        return unfilteredLinks;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return manageMe.equals(other.manageMe)
                && userPrefs.equals(other.userPrefs)
                && filteredLinks.equals(other.filteredLinks)
                && filteredModules.equals(other.filteredModules)
                && readModule.equals(other.readModule)
                && filteredTasks.equals(other.filteredTasks)
                && unfilteredTasks.equals(other.unfilteredTasks);
    }
}
