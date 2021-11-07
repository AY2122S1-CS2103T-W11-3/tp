package manageme.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;

import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.Model;
import manageme.model.task.Task;


/**
 * Deletes all tasks marked as done in ManageMe.
 */
public class DeleteDoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "deleteDoneTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all tasks that are marked as done.";

    public static final String MESSAGE_DELETE_ALL_TASK_SUCCESS = "Deleted All Tasks marked as done";

    public DeleteDoneTaskCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        for (int i = lastShownList.size() - 1; i >= 0; i--) {
            Task taskToDelete = lastShownList.get(i);
            if (taskToDelete.isDone().value) {
                model.delete(taskToDelete);
            }
        }

        return new CommandResult(MESSAGE_DELETE_ALL_TASK_SUCCESS);
    }
}
