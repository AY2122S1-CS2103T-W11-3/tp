package manageme.logic.parser.task;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.task.FindTaskCommand;
import manageme.logic.parser.CommandParserTestUtil;
import manageme.model.NameContainsKeywordsPredicate;

public class FindTaskCommandParserTest {

    private final FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new NameContainsKeywordsPredicate(Arrays.asList("Eat", "Sleep")));
        CommandParserTestUtil.assertParseSuccess(parser, "Eat Sleep", expectedFindTaskCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Eat \n \t Sleep  \t", expectedFindTaskCommand);
    }

}
