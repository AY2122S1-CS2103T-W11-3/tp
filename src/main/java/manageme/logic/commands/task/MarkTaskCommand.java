package manageme.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static manageme.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.Model;
import manageme.model.task.Task;
import manageme.model.task.TaskIsDone;

public class MarkTaskCommand extends Command {
    public static final String COMMAND_WORD = "markTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the displayed task list as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARKED_TASK_SUCCESS = "Marked/Unmarked Task: %1$s";

    private final Index targetIndex;

    public MarkTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToMark = lastShownList.get(targetIndex.getZeroBased());
        TaskIsDone newIsDone = new TaskIsDone(!taskToMark.isDone().value);
        Task markedTask = new Task(taskToMark.getName(), taskToMark.getDescription(), newIsDone,
                taskToMark.getTagModule(), taskToMark.getStart(), taskToMark.getEnd());
        model.set(taskToMark, markedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_MARKED_TASK_SUCCESS, markedTask));
    }
}
