package manageme.logic.parser.task;

import java.util.Arrays;

import manageme.commons.core.Messages;
import manageme.logic.commands.task.FindTaskCommand;
import manageme.logic.parser.Parser;
import manageme.logic.parser.exceptions.ParseException;
import manageme.model.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTaskCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns a FindTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
