package manageme.logic.parser.link;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.link.FindLinkCommand;
import manageme.logic.parser.CommandParserTestUtil;
import manageme.model.NameContainsKeywordsPredicate;

public class FindLinkCommandParserTest {
    private final FindLinkCommandParser parser = new FindLinkCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindLinkCommand expectedFindLinkCommand =
                new FindLinkCommand(new NameContainsKeywordsPredicate(Arrays.asList("Eat", "Sleep")));
        CommandParserTestUtil.assertParseSuccess(parser, "Eat Sleep", expectedFindLinkCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Eat \n \t Sleep  \t", expectedFindLinkCommand);
    }

}
