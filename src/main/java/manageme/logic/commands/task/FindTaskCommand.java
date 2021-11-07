package manageme.logic.commands.task;

import static java.util.Objects.requireNonNull;

import manageme.commons.core.Messages;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.model.Model;
import manageme.model.NameContainsKeywordsPredicate;

/**
 * Finds and lists all tasks in ManageMe whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTaskCommand extends Command {

    public static final String COMMAND_WORD = "findTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Do homework";

    private final NameContainsKeywordsPredicate predicate;

    public FindTaskCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTaskCommand // instanceof handles nulls
                && predicate.equals(((FindTaskCommand) other).predicate)); // state check
    }
}
