package manageme.ui.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import manageme.commons.core.LogsCenter;
import manageme.logic.commands.calendar.NextMonthCommand;
import manageme.logic.commands.calendar.PreviousMonthCommand;
import manageme.logic.commands.calendar.ReadDayCommand;
import manageme.model.task.Task;
import manageme.ui.UiPart;
import manageme.ui.task.TaskListPanel;

/**
 * Panel to display the calendar.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private static final String TITLE_TEMPLATE = "There %s %d %s today !";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hh:mm a, MMM dd, yyyy");

    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private final ObservableList<Task> taskList;

    private LocalDate referenceDate;
    private TaskListPanel taskListPanel;

    @FXML
    private Label date;

    @FXML
    private GridPane calendarPlaceholder;

    @FXML
    private StackPane readDayPanelPlaceholder;

    @FXML
    private Label readDayTitle;

    /**
     * Creates a {@code CalendarListPanel} with the given {@code ObservableList}.
     */
    public CalendarPanel(ObservableList<Task> taskList) {
        super(FXML);
        this.taskList = taskList;
        fillCalendarPanel(LocalDate.now());

        taskList.addListener((ListChangeListener<? super Task>) change -> fillCalendarPanel(referenceDate));
    }

    /**
     * Fills up the entire CalendarPanel with a specified date as reference.
     *
     * @param referenceDate the LocalDate object used as reference
     */
    private void fillCalendarPanel(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
        fillCalendar();
        fillReadDayPanel();
    }

    /**
     * Fills up the calendar based on reference date.
     */
    private void fillCalendar() {
        date.setText(LocalDateTime.now().format(FORMATTER));
        clearCalendar();
        fillLayout();
        fillDay();
    }

    /**
     * Fills up the readDayPanel with the list of tasks on reference date.
     */
    private void fillReadDayPanel() {
        int numOfTask = getTaskInCurrentDay(taskList, referenceDate).size();
        readDayTitle.setText(numOfTask == 0
                ? "There are no tasks today !"
                : numOfTask > 1
                    ? String.format(TITLE_TEMPLATE, "are", numOfTask, "tasks")
                    : String.format(TITLE_TEMPLATE, "is", numOfTask, "task"));

        taskListPanel = new TaskListPanel(getTaskInCurrentDay(taskList, referenceDate));
        readDayPanelPlaceholder.getChildren().clear();
        readDayPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
    }

    /**
     * Clears the calendar.
     */
    private void clearCalendar() {
        calendarPlaceholder.getChildren().clear();
    }

    /**
     * Refreshes the calendar.
     */
    public void refresh() {
        taskListPanel.refresh();
        date.setText(LocalDateTime.now().format(FORMATTER));
    }

    /**
     * Fills up the layout of a calendar.
     */
    private void fillLayout() {
        calendarPlaceholder.add(createLayoutLabel(String.format("%s %s",
                referenceDate.getMonth().toString(), referenceDate.getYear())), 0, 0, 7, 1);
        calendarPlaceholder.add(createLayoutLabel("Su"), 0, 1, 1, 1);
        calendarPlaceholder.add(createLayoutLabel("Mo"), 1, 1, 1, 1);
        calendarPlaceholder.add(createLayoutLabel("Tu"), 2, 1, 1, 1);
        calendarPlaceholder.add(createLayoutLabel("We"), 3, 1, 1, 1);
        calendarPlaceholder.add(createLayoutLabel("Th"), 4, 1, 1, 1);
        calendarPlaceholder.add(createLayoutLabel("Fr"), 5, 1, 1, 1);
        calendarPlaceholder.add(createLayoutLabel("Sa"), 6, 1, 1, 1);
    }

    /**
     * Fills up the days in a month.
     */
    private void fillDay() {
        int numOfDayInMonth = referenceDate.lengthOfMonth();
        int currentDay = 1;
        int row = 2;
        int column = startColumn();

        while (currentDay <= numOfDayInMonth) {
            LocalDate date = LocalDate.of(referenceDate.getYear(), referenceDate.getMonth(), currentDay);

            DayCard dayCard = date.equals(referenceDate)
                    ? new DayCard(getTaskInCurrentDay(taskList, date), date, true)
                    : new DayCard(getTaskInCurrentDay(taskList, date), date, false);

            calendarPlaceholder.add(dayCard.getRoot(), column, row, 1, 1);

            column++;
            if (column > 6) {
                column %= 7;
                row++;
            }
            currentDay++;
        }
    }

    /**
     * Returns the list of tasks happening on specified date from an original list of tasks.
     *
     * @param taskList the original list of tasks.
     * @param current the specified date.
     * @return the filtered task list
     */
    private ObservableList<Task> getTaskInCurrentDay(ObservableList<Task> taskList, LocalDate current) {
        return taskList.filtered((task -> task.getSpan().anyMatch(dates -> dates.equals(current))));
    }

    /**
     * Sets the style for layout of calendar.
     *
     * @param str text for the label
     * @return the styled label
     */
    private Label createLayoutLabel(String str) {
        Label result = new Label(str);
        result.setPadding(new Insets(5, 5, 5, 5));
        return result;
    }

    /**
     * Determines what is the column index of the first day of the month.
     *
     * @return the column index
     */
    private int startColumn() {
        int column;
        LocalDate firstDayOfMonth = referenceDate.withDayOfMonth(1);
        DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
        switch (dayOfWeek) {
        case SUNDAY:
            column = 0;
            break;
        case MONDAY:
            column = 1;
            break;
        case TUESDAY:
            column = 2;
            break;
        case WEDNESDAY:
            column = 3;
            break;
        case THURSDAY:
            column = 4;
            break;
        case FRIDAY:
            column = 5;
            break;
        case SATURDAY:
            column = 6;
            break;
        default:
            column = -1;
            break;
        }
        return column;
    }

    /**
     * Parses system input to perform calendar-specific execution.
     *
     * @param feedbackToSystem system input
     */
    public void executeCommand(String feedbackToSystem) {
        String[] feedback = feedbackToSystem.split(" ");
        switch (feedback[0]) {
        case NextMonthCommand.COMMAND_WORD:
            showNextMonth();
            break;

        case PreviousMonthCommand.COMMAND_WORD:
            showPreviousMonth();
            break;

        case ReadDayCommand.COMMAND_WORD:
            LocalDate date = LocalDate.parse(feedback[1]);
            fillCalendarPanel(date);
            break;

        default:
            return;
        }
    }

    /**
     * Displays the next month of the calendar.
     */
    private void showNextMonth() {
        referenceDate = referenceDate.plusMonths(1);
        fillCalendarPanel(referenceDate);
    }

    /**
     * Displays the previous month of the calendar.
     */
    private void showPreviousMonth() {
        referenceDate = referenceDate.minusMonths(1);
        fillCalendarPanel(referenceDate);
    }
}
