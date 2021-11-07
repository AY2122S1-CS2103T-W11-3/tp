package manageme.logic.commands.module;

import static java.util.Objects.requireNonNull;

import java.util.List;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.Model;
import manageme.model.TagModule;
import manageme.model.module.Module;

/**
 * Deletes a module identified using it's displayed index from the task manager.
 */
public class DeleteModuleCommand extends Command {
    public static final String COMMAND_WORD = "deleteMod";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the index number used in the displayed module list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final Index targetIndex;

    public DeleteModuleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.editModuleInTasksWithModule(moduleToDelete, TagModule.empty());
        model.editModuleInLinksWithModule(moduleToDelete, TagModule.empty());
        model.delete(moduleToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteModuleCommand) other).targetIndex)); // state check
    }
}
