package manageme.logic.parser.link;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.logic.commands.link.DeleteLinkCommand;
import manageme.logic.parser.Parser;
import manageme.logic.parser.ParserUtil;
import manageme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLinkCommand object
 */
public class DeleteLinkCommandParser implements Parser<DeleteLinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLinkCommand
     * and returns a DeleteLinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLinkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLinkCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteLinkCommand.MESSAGE_USAGE), pe);
        }
    }

}
