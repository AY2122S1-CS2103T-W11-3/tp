package manageme.logic.commands.module;

import static java.util.Objects.requireNonNull;

import manageme.commons.core.Messages;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.model.Model;
import manageme.model.NameContainsKeywordsPredicate;

/**
 * Finds and lists all mods in app whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */

public class FindModuleCommand extends Command {

    public static final String COMMAND_WORD = "findMod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all modules whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "computer";

    private final NameContainsKeywordsPredicate predicate;

    public FindModuleCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredModuleList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, model.getFilteredModuleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModuleCommand // instanceof handles nulls
                && predicate.equals(((FindModuleCommand) other).predicate)); // state check
    }
}
