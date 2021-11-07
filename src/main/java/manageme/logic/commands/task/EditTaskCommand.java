package manageme.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static manageme.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.commons.util.CollectionUtil;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.Model;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.module.Module;
import manageme.model.task.Task;
import manageme.model.task.TaskDescription;
import manageme.model.task.TaskTime;


/**
 * Updates a task identified using it's displayed index from ManageMe.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "editTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer between 1 and 2147483647) ";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";
    public static final String MESSAGE_START_LATER_THAN_END = "The task cannot have start date later than the end date";
    public static final String MESSAGE_START_WITHOUT_END = "The task cannot have a start without an end date";
    public static final String MESSAGE_TASK_NAME_TOO_LONG = "Maximum Length of Edited Task Name is 50 Characters";
    public static final String MESSAGE_TASK_DESCRIPTION_TOO_LONG =
            "Maximum Length of Edited Task Description is 100 Characters";
    public static final String MESSAGE_NONEXISTENT_MODULE = "The module you have associated does not exist";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index              of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.isSame(editedTask) && model.has(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        if (!editedTask.getStart().isEmpty() && editedTask.getEnd().isEmpty()) {
            throw new CommandException(MESSAGE_START_WITHOUT_END);
        }

        if (!editedTask.getStart().isEmpty() && !editedTask.getEnd().isEmpty()
                && editedTask.getStart().getTime().isAfter(editedTask.getEnd().getTime())) {
            throw new CommandException(MESSAGE_START_LATER_THAN_END);
        }

        if (editedTask.getName().value.length() > 50) {
            throw new CommandException(MESSAGE_TASK_NAME_TOO_LONG);
        }

        if (editedTask.getDescription().value.length() > 100) {
            throw new CommandException(MESSAGE_TASK_DESCRIPTION_TOO_LONG);
        }

        //if there is a module being associated, check it exists
        if (!editedTask.getTagModule().value.isEmpty()) {
            if (!model.has(new Module(new Name(editedTask.getTagModule().value)))) {
                throw new CommandException(MESSAGE_NONEXISTENT_MODULE);
            }
        }

        model.set(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        TaskDescription updatedDescription = editTaskDescriptor.getTaskDescription()
                .orElse(taskToEdit.getDescription());
        // Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(taskToEdit.getTags());
        TagModule updatedModule = editTaskDescriptor.getModule()
                .orElse(taskToEdit.getTagModule());
        TaskTime updatedStartTime = editTaskDescriptor.getStart()
                .orElse(taskToEdit.getStart());
        TaskTime updatedEndTime = editTaskDescriptor.getEnd()
                .orElse(taskToEdit.getEnd());
        return new Task(updatedName, updatedDescription, taskToEdit.isDone(), updatedModule, updatedStartTime,
                updatedEndTime);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Name name;
        private TaskDescription description;
        private TagModule module;
        private TaskTime start;
        private TaskTime end;
        // private Set<Tag> tags;

        public EditTaskDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setName(toCopy.name);
            setDescription(toCopy.description);
            setModule(toCopy.module);
            setStart(toCopy.start);
            setEnd(toCopy.end);
            // setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, description, module, start, end);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDescription(TaskDescription description) {
            this.description = description;
        }

        public Optional<TaskDescription> getTaskDescription() {
            return Optional.ofNullable(description);
        }

        public void setModule(TagModule module) {
            this.module = module;
        }

        public Optional<TagModule> getModule() {
            return Optional.ofNullable(module);
        }

        public void setStart(TaskTime start) {
            this.start = start;
        }

        public Optional<TaskTime> getStart() {
            return Optional.ofNullable(start);
        }

        public void setEnd(TaskTime end) {
            this.end = end;
        }

        public Optional<TaskTime> getEnd() {
            return Optional.ofNullable(end);
        }

        ///**
        // * Sets {@code tags} to this object's {@code tags}.
        // * A defensive copy of {@code tags} is used internally.
        // */
        //public void setTags(Set<Tag> tags) {
        //    this.tags = (tags != null) ? new HashSet<>(tags) : null;
        //}

        ///**
        // * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
        // * if modification is attempted.
        // * Returns {@code Optional#empty()} if {@code tags} is null.
        // */
        //public Optional<Set<Tag>> getTags() {
        //    return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        //}

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getName().equals(e.getName())
                    && getTaskDescription().equals(e.getTaskDescription())
                    && getModule().equals(e.getModule())
                    && getStart().equals(e.getStart())
                    && getEnd().equals(e.getEnd());
            //        && getTags().equals(e.getTags());
        }
    }
}
