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

public class OpenLinkCommand extends Command {
    public static final String COMMAND_WORD = "openLink";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Open the link identified by the index number used in the displayed link list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_OPEN_LINK_SUCCESS = "Opened Link: %1$s";
    public static final String MESSAGE_OPEN_LINK_FAILURE = "Failed to open Link: %1$s.";

    private final Index targetIndex;

    public OpenLinkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Link> lastShownList = model.getFilteredLinkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LINK_DISPLAYED_INDEX);
        }

        Link linkToOpen = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.openLink(linkToOpen);
        } catch (Exception e) {
            String failureResult = String.format(MESSAGE_OPEN_LINK_FAILURE, linkToOpen) + "\n" + e.getMessage();
            return new CommandResult(failureResult);
        }
        return new CommandResult(String.format(MESSAGE_OPEN_LINK_SUCCESS, linkToOpen));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenLinkCommand // instanceof handles nulls
                && targetIndex.equals(((OpenLinkCommand) other).targetIndex)); // state check
    }
}
