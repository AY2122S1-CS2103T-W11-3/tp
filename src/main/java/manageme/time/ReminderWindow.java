package manageme.time;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import manageme.model.task.Task;
import manageme.ui.UiPart;
import manageme.ui.task.TaskCard;

public class ReminderWindow extends UiPart<Stage> {
    private static final String FXML = "ReminderWindow.fxml";

    private Stage reminderWindow;

    private ArrayList<Task> acknowledgedList = new ArrayList<>();
    private ArrayList<Task> displayedList;

    @FXML
    private Label title;
    @FXML
    private VBox reminderTaskList;
    @FXML
    private Button ackButton;

    /**
     * Creates a new ReminderWindow.
     *
     * @param root Stage to use as the root of the ReminderWindow.
     */
    public ReminderWindow(Stage root) {
        super(FXML, root);

        this.reminderWindow = root;
        reminderWindow.setTitle("Reminder");
        title.setText("Happening now !");
    }

    /**
     * Creates a new HelpWindow.
     */
    public ReminderWindow() {
        this(new Stage());
    }

    /**
     * Displays all tasks that have started.
     */
    public void display(ArrayList<Task> allTasks) {
        initBothLists(allTasks);

        if (!displayedList.isEmpty()) {
            resetWindow();
            reminderWindow.show();

            reminderWindow.setMinHeight(reminderWindow.getHeight());
            reminderWindow.setMinWidth(reminderWindow.getWidth());
            reminderWindow.setResizable(false);
        }
    }

    /**
     * Resets Reminder Window.
     */
    private void resetWindow() {
        reminderTaskList.getChildren().clear();
        for (int i = 0; i < displayedList.size(); i++) {
            reminderTaskList.getChildren().add(new TaskCard(displayedList.get(i), i + 1, true).getRoot());
        }
    }

    /**
     * Acknowledges all tasks shown in displayedList.
     * Acknowledged tasks will not appear in subsequent ReminderWindow.
     */
    @FXML
    private void acknowledgeDisplayList() {
        for (int i = 0; i < displayedList.size(); i++) {
            acknowledgedList.add(displayedList.get(i));
        }
        displayedList.clear();
        reminderWindow.close();
    }

    private void initBothLists(ArrayList<Task> newList) {
        this.displayedList = newList;
        this.acknowledgedList.retainAll(newList);
        this.displayedList.removeAll(acknowledgedList);
    }
}
