package manageme.logic.commands.link;

import static java.util.Objects.requireNonNull;

import manageme.commons.core.Messages;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.model.Model;
import manageme.model.NameContainsKeywordsPredicate;

public class FindLinkCommand extends Command {
    public static final String COMMAND_WORD = "findLink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all links whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "computer";

    private final NameContainsKeywordsPredicate predicate;

    public FindLinkCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLinkList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_LINKS_LISTED_OVERVIEW, model.getFilteredLinkList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindLinkCommand // instanceof handles nulls
                && predicate.equals(((FindLinkCommand) other).predicate)); // state check
    }
}
