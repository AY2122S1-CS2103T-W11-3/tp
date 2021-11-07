package manageme.testutil;

import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.task.Task;
import manageme.model.task.TaskDescription;
import manageme.model.task.TaskIsDone;
import manageme.model.task.TaskTime;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_NAME = "Finish CS2103T TP";
    public static final String DEFAULT_DESCRIPTION = "This is a default template text to test "
            + "TaskDescription";
    public static final boolean DEFAULT_ISDONE = false;
    public static final String DEFAULT_MODULE = "CS2103T";
    public static final String DEFAULT_STARTDATETIME = "2021-10-05T11:00";
    public static final String DEFAULT_ENDDATETIME = "2021-10-12T23:59";

    private Name name;
    private TaskDescription description;
    private TaskIsDone isDone;
    private TagModule module;
    private TaskTime start;
    private TaskTime end;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new TaskDescription(DEFAULT_DESCRIPTION);
        isDone = new TaskIsDone(DEFAULT_ISDONE);
        module = new TagModule();
        start = new TaskTime(DEFAULT_STARTDATETIME);
        end = new TaskTime(DEFAULT_ENDDATETIME);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        description = taskToCopy.getDescription();
        isDone = taskToCopy.isDone();
        module = taskToCopy.getTagModule();
        start = taskToCopy.getStart();
        end = taskToCopy.getEnd();
    }

    /**
     * Sets the {@code name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new TaskDescription(description);
        return this;
    }

    /**
     * Sets the {@code module} of the {@code Task} that we are building.
     */
    public TaskBuilder withModule(String tagModule) {
        this.module = tagModule.equals("") ? TagModule.empty() : new TagModule(tagModule);
        return this;
    }
    /**
     * Sets the {@code isDone} of the {@code Task} that we are building.
     */
    public TaskBuilder withIsDone(boolean isDone) {
        this.isDone = new TaskIsDone(isDone);
        return this;
    }

    /**
     * Sets the {@code start} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartDateTime(String start) {
        this.start = new TaskTime(start);
        return this;
    }

    /**
     * Sets the {@code end} of the {@code Task} that we are building.
     */
    public TaskBuilder withEndDateTime(String end) {
        this.end = new TaskTime(end);
        return this;
    }

    public Task build() {
        return new Task(name, description, isDone, module, start, end);
    }

}
