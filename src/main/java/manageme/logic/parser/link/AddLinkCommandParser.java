package manageme.logic.parser.link;

import static manageme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static manageme.logic.parser.CliSyntax.PREFIX_MODULE;
import static manageme.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import manageme.commons.core.Messages;
import manageme.logic.commands.link.AddLinkCommand;
import manageme.logic.parser.ArgumentMultimap;
import manageme.logic.parser.ArgumentTokenizer;
import manageme.logic.parser.Parser;
import manageme.logic.parser.ParserUtil;
import manageme.logic.parser.Prefix;
import manageme.logic.parser.exceptions.ParseException;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.link.LinkAddress;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddLinkCommandParser implements Parser<AddLinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLinkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_MODULE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS) || !argMultimap.getPreamble().isEmpty()) {

            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLinkCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        LinkAddress address = ParserUtil.parseLinkAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        TagModule module = argMultimap.getValue(PREFIX_MODULE).isPresent()
                ? ParserUtil.parseLinkModule(argMultimap.getValue(PREFIX_MODULE).get())
                : manageme.model.TagModule.empty();

        Link link = new Link(name, address, module);

        return new AddLinkCommand(link);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
