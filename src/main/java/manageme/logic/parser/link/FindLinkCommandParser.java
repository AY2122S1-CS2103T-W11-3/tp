package manageme.logic.parser.link;

import java.util.Arrays;

import manageme.commons.core.Messages;
import manageme.logic.commands.link.FindLinkCommand;
import manageme.logic.parser.Parser;
import manageme.logic.parser.exceptions.ParseException;
import manageme.model.NameContainsKeywordsPredicate;

public class FindLinkCommandParser implements Parser<FindLinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindLinkCommand
     * and returns a FindLinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindLinkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindLinkCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindLinkCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
