package manageme.logic.parser.link;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static manageme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static manageme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.link.OpenLinkCommand;

public class OpenLinkCommandParserTest {
    private OpenLinkCommandParser parser = new OpenLinkCommandParser();

    @Test
    public void parse_validArgs_returnsOpenCommand() {
        assertParseSuccess(parser, "1", new OpenLinkCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenLinkCommand.MESSAGE_USAGE));
    }
}
