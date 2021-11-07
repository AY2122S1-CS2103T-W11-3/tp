package manageme.model.task;

import static java.util.Objects.requireNonNull;
import static manageme.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

public class TaskTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid time value, please follow the format YYYY-MM-DD[T]hh:mm. Example: 2021-11-11T16:00";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    private static final String DATETIME_PATTERN = "dd/MM/yyyy hh:mma";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    private static final String NO_TIME = "";

    public final String value;
    public final Optional<LocalDateTime> time;


    /**
     * Constructs a {@code TaskTime}.
     *
     * @param taskTime A Task Time.
     */
    public TaskTime(String taskTime) {
        requireNonNull(taskTime);
        checkArgument(isValidTaskTime(taskTime), MESSAGE_CONSTRAINTS);
        String trimmedT = taskTime.trim();
        this.value = trimmedT;
        if (value.equals("")) {
            this.time = Optional.empty();
        } else {
            this.time = Optional.of(LocalDateTime.parse(trimmedT));
        }
    }

    /**
     * Constructs an empty {@code TaskTime}.
     */
    public TaskTime() {
        this.value = NO_TIME;
        this.time = Optional.empty();
    }

    /**
     * Checks input date to see if it can be parsed and of correct format.
     * @param test input date
     * @return true or false if it is suitable.
     */
    public static boolean isValidTaskTime(String test) {
        if (test.equals("")) {
            return true;
        }

        boolean parsable = true;
        try {
            LocalDateTime temp = LocalDateTime.parse(test);
        } catch (DateTimeParseException e) {
            parsable = false;
        }
        return parsable && test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns a display-formatted string of TaskTime for the UI.
     * @return display-formatted string
     */
    public String toDisplayString() {
        if (time.isPresent()) {
            return dateTimeFormatter.format(time.get());
        } else {
            return "";
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && value.equals(((TaskTime) other).value))
                && time.equals((((TaskTime) other).time)); // state check
    }

    public static TaskTime empty() {
        return new TaskTime();
    }

    public boolean isEmpty() {
        return time.isEmpty();
    }

    public LocalDateTime getTime() {
        if (time.isEmpty()) {
            return null;
        }
        return time.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, time);
    }
}
