package manageme.ui.calendar;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import manageme.model.task.Task;
import manageme.ui.UiPart;

/**
 * An UI component that displays information of a day.
 */
public class DayCard extends UiPart<Region> {

    private static final PseudoClass HAS_TASK = PseudoClass.getPseudoClass("has");
    private static final PseudoClass IS_SELECTED = PseudoClass.getPseudoClass("selected");

    private static final String FXML = "DayCard.fxml";

    private ObservableList<Task> taskList;
    private LocalDate date;
    private boolean isSelected;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/ManageMe-level4/issues/336">The issue on ManageMe level 4</a>
     */

    @FXML
    private VBox dayCardPane;
    @FXML
    private Label day;
    @FXML
    private Rectangle has;

    /**
     * Creates a day card with the given date and index to display.
     */
    public DayCard(ObservableList<Task> taskList, LocalDate date, boolean isSelected) {
        super(FXML);
        this.date = date;
        this.taskList = taskList;
        this.isSelected = isSelected;
        fillDay();
    }

    /**
     * Fills up the DayCard.
     */
    private void fillDay() {
        day.setText(String.format("%d", date.getDayOfMonth()));
        if (!taskList.isEmpty()) {
            has.pseudoClassStateChanged(HAS_TASK, true);
        }

        if (isSelected) {
            dayCardPane.pseudoClassStateChanged(IS_SELECTED, true);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayCard)) {
            return false;
        }

        // state check
        DayCard card = (DayCard) other;
        return taskList.equals(card.taskList)
                && date.equals(card.date)
                && isSelected == card.isSelected;
    }
}
