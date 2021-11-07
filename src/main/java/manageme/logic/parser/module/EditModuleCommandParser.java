package manageme.logic.parser.module;

import static java.util.Objects.requireNonNull;
import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static manageme.logic.parser.CliSyntax.PREFIX_NAME;

import manageme.commons.core.index.Index;
import manageme.logic.commands.module.EditModuleCommand;
import manageme.logic.commands.module.EditModuleCommand.EditModuleDescriptor;
import manageme.logic.parser.ArgumentMultimap;
import manageme.logic.parser.ArgumentTokenizer;
import manageme.logic.parser.Parser;
import manageme.logic.parser.ParserUtil;
import manageme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditModuleCommand object
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE), pe);
        }

        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editModuleDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditModuleCommand.MESSAGE_NOT_EDITED);
        }

        return new EditModuleCommand(index, editModuleDescriptor);
    }
}
