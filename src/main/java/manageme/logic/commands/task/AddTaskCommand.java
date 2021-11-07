package manageme.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static manageme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static manageme.logic.parser.CliSyntax.PREFIX_END;
import static manageme.logic.parser.CliSyntax.PREFIX_MODULE;
import static manageme.logic.parser.CliSyntax.PREFIX_NAME;
import static manageme.logic.parser.CliSyntax.PREFIX_START;

import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.Model;
import manageme.model.Name;
import manageme.model.module.Module;
import manageme.model.task.Task;

public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: "
            + PREFIX_NAME + "TASK_NAME "
            + PREFIX_DESCRIPTION + "TASK_DESCRIPTION "
            + PREFIX_MODULE + "ASSOCIATED_MODULE_NAME "
            + PREFIX_START + "TASK_START_DATETIME "
            + PREFIX_END + "TASK_END_DATETIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Do homework "
            + PREFIX_DESCRIPTION + "Complete CS2103T quiz on testing. "
            + PREFIX_MODULE + "CS2103T "
            + PREFIX_START + "2021-10-05T11:50:55 "
            + PREFIX_END + "2021-10-07T11:50:55";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    public static final String MESSAGE_START_LATER_THAN_END = "The task cannot have start date later than the end date";
    public static final String MESSAGE_START_WITHOUT_END = "The task cannot have a start without an end date";
    public static final String MESSAGE_TASK_NAME_TOO_LONG = "Maximum Length of Task Name is 50 Characters";
    public static final String MESSAGE_TASK_DESCRIPTION_TOO_LONG =
            "Maximum Length of Task Description is 100 Characters";
    public static final String MESSAGE_NONEXISTENT_MODULE = "The module you have associated does not exist";

    private final Task toAdd;
    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.has(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        //if there is a module being associated, check it exists
        if (!toAdd.getTagModule().value.isEmpty()) {
            if (!model.has(new Module(new Name(toAdd.getTagModule().value)))) {
                throw new CommandException(MESSAGE_NONEXISTENT_MODULE);
            }
        }

        model.add(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
