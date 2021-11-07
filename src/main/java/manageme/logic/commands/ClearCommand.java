package manageme.logic.commands;

import static java.util.Objects.requireNonNull;

import manageme.model.ManageMe;
import manageme.model.Model;

/**
 * Clears all data from ManageMe
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ManageMe has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setManageMe(new ManageMe());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
