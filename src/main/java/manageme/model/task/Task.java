package manageme.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import manageme.commons.util.CollectionUtil;
import manageme.model.ManageMeObject;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.task.exceptions.DuplicateTaskException;
import manageme.model.task.exceptions.TaskNotFoundException;

/**
 * Represents a Task in the Task List.
 * Guarantees: details are present and not TaskTime.empty(), field values are validated, immutable.
 */
public class Task extends ManageMeObject {
    public static final String MESSAGE_START_WITHOUT_END =
            "A task with a start datetime MUST also have an end datetime";

    private final TaskDescription description;
    private TaskIsDone isDone;

    //Optional: TagModule that the task may be linked to. Can only be linked to max 1 module.
    private final TagModule module;

    //Optional: Task may have an end DateTime(Deadline) or both a start and end DateTime(Event).
    private final TaskTime start;
    private final TaskTime end;

    /**
     * Task with a start and end date(Event). Includes module.
     * Every field must be present and not TaskTime.empty().
     */
    public Task(Name name, TaskDescription description, TagModule module, TaskTime start,
                TaskTime end) {
        super(name);
        CollectionUtil.requireAllNonNull(description, module, start, end);
        //checkArgument(checkStartHasEnd(start, end), MESSAGE_START_WITHOUT_END);
        this.description = description;
        this.isDone = new TaskIsDone(false);
        this.module = module;
        this.start = start;
        this.end = end;
    }

    /**
     * Task with a start and end date(Event). Includes module.
     * Every field must be present and not TaskTime.empty().
     */
    public Task(Name name, TaskDescription description, TaskIsDone isDone, TagModule module, TaskTime start,
                TaskTime end) {
        super(name);
        CollectionUtil.requireAllNonNull(name, description, module, start, end);
        //checkArgument(checkStartHasEnd(start, end), MESSAGE_START_WITHOUT_END);
        this.description = description;
        this.isDone = isDone;
        this.module = module;
        this.start = start;
        this.end = end;
    }

    public TaskDescription getDescription() {
        return description;
    }

    public TagModule getTagModule() {
        return module;
    }

    public TaskTime getStart() {
        return start;
    }

    public TaskTime getEnd() {
        return end;
    }

    public TaskIsDone isDone() {
        return isDone;
    }

    /**
     * Returns the dates that this {@code Task} object spans over.
     *
     * @return The sequential ordered stream of dates.
     */
    public Stream<LocalDate> getSpan() {
        if (!end.isEmpty() && !start.isEmpty()) {
            return start.getTime().toLocalDate().datesUntil(end.time.get().plusDays(1).toLocalDate());
        } else if (start.isEmpty() && !end.isEmpty()) {
            return end.getTime().toLocalDate().datesUntil(end.getTime().plusDays(1).toLocalDate());
        } else {
            return Stream.empty();
        }
    }

    /**
     * Returns the start or end datetime of the Task, depending on which is earlier.
     * @return The earliest datetime in the Task
     */
    public LocalDateTime getFirstOccurrence() {
        if (start.isEmpty() && end.isEmpty()) {
            // Should not happen
            System.out.println("getFirstOccurrence");
            return null;
        } else if (start.isEmpty()) {
            return end.getTime();
        } else {
            return start.getTime();
        }
    }

    /**
     * Returns true if both tasks have the same name.
     * This defines a weaker notion of equality between two tasks.
     */
    @Override
    public boolean isSame(ManageMeObject otherTask) {
        if (otherTask == this) {
            return true;
        }
        if (otherTask instanceof Task) {
            return otherTask != null
                    && otherTask.getName().equals(getName());
        } else {
            return false;
        }
    }

    @Override
    public void throwDuplicateException() throws RuntimeException {
        throw new DuplicateTaskException();
    }

    public void throwNotFoundException() throws RuntimeException {
        throw new TaskNotFoundException();
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(getName())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.isDone().equals(isDone())
                && otherTask.getTagModule().equals(getTagModule())
                && otherTask.getStart().equals(getStart())
                && otherTask.getEnd().equals(getEnd());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), description, isDone, module, start, end);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName())
                .append("; Description: ")
                .append(getDescription())
                .append("; Done: ")
                .append(isDone())
                .append("; TagModule: ")
                .append(getTagModule())
                .append("; Start: ")
                .append(getStart())
                .append("; End: ")
                .append(getEnd());
        return builder.toString();
    }
}
