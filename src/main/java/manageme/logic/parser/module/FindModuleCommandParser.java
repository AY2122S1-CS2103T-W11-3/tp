package manageme.logic.parser.module;

import java.util.Arrays;

import manageme.commons.core.Messages;
import manageme.logic.commands.module.FindModuleCommand;
import manageme.logic.parser.Parser;
import manageme.logic.parser.exceptions.ParseException;
import manageme.model.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindModuleCommand object
 */
public class FindModuleCommandParser implements Parser<FindModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindModuleCommand
     * and returns a FindModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindModuleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindModuleCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindModuleCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
