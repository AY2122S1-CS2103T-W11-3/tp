package manageme.logic.parser.link;

import static java.util.Objects.requireNonNull;
import static manageme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static manageme.logic.parser.CliSyntax.PREFIX_MODULE;
import static manageme.logic.parser.CliSyntax.PREFIX_NAME;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.link.EditLinkCommand;
import manageme.logic.parser.ArgumentMultimap;
import manageme.logic.parser.ArgumentTokenizer;
import manageme.logic.parser.Parser;
import manageme.logic.parser.ParserUtil;
import manageme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLinkCommand object
 */
public class EditLinkCommandParser implements Parser<EditLinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLinkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_MODULE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditLinkCommand.MESSAGE_USAGE), pe);
        }

        EditLinkCommand.EditLinkDescriptor editLinkDescriptor = new EditLinkCommand.EditLinkDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editLinkDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editLinkDescriptor.setAddress(ParserUtil.parseLinkAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            editLinkDescriptor.setModule(ParserUtil.parseLinkModule(argMultimap.getValue(PREFIX_MODULE).get()));
        }

        if (!editLinkDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLinkCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLinkCommand(index, editLinkDescriptor);
    }

}
