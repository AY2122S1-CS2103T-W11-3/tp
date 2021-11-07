package manageme.ui.task;

import java.time.LocalDateTime;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import manageme.model.task.Task;
import manageme.ui.UiPart;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {
    private static final PseudoClass IS_DONE = PseudoClass.getPseudoClass("isDone");
    private static final PseudoClass OVERDUE = PseudoClass.getPseudoClass("overdue");
    private static final PseudoClass IN_REMINDER = PseudoClass.getPseudoClass("inReminder");

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/ManageMe-level4/issues/336">The issue on ManageMe level 4</a>
     */

    public final Task task;
    public final boolean inReminder;

    @FXML
    private VBox taskCardPane;
    @FXML
    private Label name;
    @FXML
    private Label taskId;
    @FXML
    private Label taskDescription;
    @FXML
    private Label tagModule;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;

    /**
     * Creates a {@code taskCode} with the given {@code task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        this(task, displayedIndex, false);
    }

    /**
     * Creates a {@code taskCode} for reminder window.
     */
    public TaskCard(Task task, int displayedIndex, boolean inReminder) {
        super(FXML);
        this.task = task;
        this.inReminder = inReminder;
        init(displayedIndex);
    }

    private void init(int displayedIndex) {
        taskId.setText(displayedIndex + ". ");
        name.setText(task.getName().value);
        taskDescription.setText(task.getDescription().value);
        tagModule.setText(task.getTagModule().value);
        startTime.setText(task.getStart().isEmpty() ? "" : "Start: " + task.getStart().toDisplayString());
        endTime.setText(task.getEnd().isEmpty() ? "" : "End: " + task.getEnd().toDisplayString());

        if (task.isDone().value) {
            taskCardPane.pseudoClassStateChanged(IS_DONE, true);
        } else if (!task.getEnd().isEmpty()) {
            if (LocalDateTime.now().isAfter(task.getEnd().getTime())) {
                taskCardPane.pseudoClassStateChanged(OVERDUE, true);
            }
        }

        if (inReminder) {
            taskCardPane.pseudoClassStateChanged(IN_REMINDER, true);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return taskId.getText().equals(card.taskId.getText())
                && task.equals(card.task);
    }
}
