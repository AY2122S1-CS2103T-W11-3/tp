package manageme.logic.commands.link;

import static java.util.Objects.requireNonNull;

import java.util.List;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.Model;
import manageme.model.link.Link;

/**
 * Deletes a link identified using it's displayed index from the address book.
 */
public class DeleteLinkCommand extends Command {

    public static final String COMMAND_WORD = "deleteLink";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the link identified by the index number used in the displayed link list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LINK_SUCCESS = "Deleted Link: %1$s";

    private final Index targetIndex;

    public DeleteLinkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Link> lastShownList = model.getFilteredLinkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LINK_DISPLAYED_INDEX);
        }

        Link linkToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.delete(linkToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LINK_SUCCESS, linkToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLinkCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteLinkCommand) other).targetIndex)); // state check
    }
}
