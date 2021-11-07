package manageme.ui.module;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import manageme.model.link.Link;
import manageme.model.module.Module;
import manageme.model.task.Task;
import manageme.ui.UiPart;
import manageme.ui.link.LinkListPanel;
import manageme.ui.task.TaskListPanel;

public class ModuleWindow extends UiPart<Stage> {
    private static final String FXML = "ModuleWindow.fxml";

    private Stage moduleWindow;

    private Module module;

    @FXML
    private Label name;
    @FXML
    private VBox readLinkList;
    @FXML
    private VBox readTaskList;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public ModuleWindow(Stage root) {
        super(FXML, root);

        this.moduleWindow = root;
    }

    /**
     * Creates a new HelpWindow.
     */
    public ModuleWindow() {
        this(new Stage());
    }

    /**
     * Displays module details to the user.
     *
     * @param optionalModule the Optional that contains the specific module user requested to read.
     */
    public void display(Optional<Module> optionalModule, ObservableList<Link> unfilteredLinkList,
                        ObservableList<Task> unfilteredTaskList) {
        this.module = optionalModule.get();
        resetWindow();
        moduleWindow.setTitle(module.getName().value);
        name.setText(module.getName().value);
        fillLinkList(module, unfilteredLinkList);
        fillTaskList(module, unfilteredTaskList);
        moduleWindow.setResizable(false);

        moduleWindow.showAndWait();
    }

    /**
     * Resets Module Window.
     */
    private void resetWindow() {
        readLinkList.getChildren().clear();
        readTaskList.getChildren().clear();
    }

    /**
     * Fills up the task list.
     */
    public void fillTaskList(Module module, ObservableList<Task> unfilteredTaskList) {
        FilteredList<Task> tasks = unfilteredTaskList.filtered(task -> {
            Optional<String> tagModule = task.getTagModule().name;

            if (tagModule.isEmpty()) {
                return false;
            }

            return module.getName().value.equals(tagModule.get());
        });

        readTaskList.getChildren().add(new TaskListPanel(tasks).getRoot());
    }

    /**
     * Fills up the link list.
     */
    public void fillLinkList(Module module, ObservableList<Link> unfilteredLinkList) {
        FilteredList<Link> links = unfilteredLinkList.filtered(link -> {
            Optional<String> linkModule = link.getLinkModule().name;

            if (linkModule.isEmpty()) {
                return false;
            }

            return module.getName().value.equals(linkModule.get());
        });

        readLinkList.getChildren().add(new LinkListPanel(links).getRoot());
    }
}
