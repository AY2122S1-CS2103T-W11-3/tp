package manageme.logic.parser.link;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static manageme.logic.commands.link.LinkCommandTestUtil.INVALID_LINKADDRESS_DESC;
import static manageme.logic.commands.link.LinkCommandTestUtil.INVALID_LINKMODULE_DESC;
import static manageme.logic.commands.link.LinkCommandTestUtil.INVALID_NAME_DESC;
import static manageme.logic.commands.link.LinkCommandTestUtil.LINKADDRESS_DESC_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.LINKADDRESS_DESC_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.LINKMODULE_DESC_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.LINKMODULE_DESC_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.NAME_DESC_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.NAME_DESC_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_LINKADDRESS_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_LINKMODULE_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_NAME_A;
import static manageme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static manageme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static manageme.testutil.TypicalLinks.LINK_A;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.CommandTestUtil;
import manageme.logic.commands.link.AddLinkCommand;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.link.LinkAddress;
import manageme.testutil.LinkBuilder;

public class AddLinkCommandParserTest {
    private AddLinkCommandParser parser = new AddLinkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Link expectedLink = new LinkBuilder(LINK_A).build();

        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + NAME_DESC_A
                + LINKADDRESS_DESC_A + LINKMODULE_DESC_A, new AddLinkCommand(expectedLink));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_B + NAME_DESC_A
                + LINKADDRESS_DESC_A + LINKMODULE_DESC_A, new AddLinkCommand(expectedLink));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, LINKADDRESS_DESC_B + NAME_DESC_A
                + LINKADDRESS_DESC_A + LINKMODULE_DESC_A, new AddLinkCommand(expectedLink));

        // multiple modules - last module accepted
        assertParseSuccess(parser, LINKMODULE_DESC_B + NAME_DESC_A
                + LINKADDRESS_DESC_A + LINKMODULE_DESC_A, new AddLinkCommand(expectedLink));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no module
        Link expectedLink = new LinkBuilder(LINK_A).withModule("").build();
        assertParseSuccess(parser, NAME_DESC_A + LINKADDRESS_DESC_A, new AddLinkCommand(expectedLink));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLinkCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_A + LINKADDRESS_DESC_A + LINKMODULE_DESC_A, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_A + VALID_LINKADDRESS_A + LINKMODULE_DESC_A, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_A + VALID_LINKADDRESS_A + VALID_LINKMODULE_A,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + LINKADDRESS_DESC_A + LINKMODULE_DESC_A,
                Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_A + INVALID_LINKADDRESS_DESC + LINKMODULE_DESC_A,
                LinkAddress.MESSAGE_CONSTRAINTS);

        // invalid module
        assertParseFailure(parser, NAME_DESC_A + LINKADDRESS_DESC_A + INVALID_LINKMODULE_DESC,
                TagModule.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_LINKADDRESS_DESC + LINKMODULE_DESC_A,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + NAME_DESC_A
                + LINKADDRESS_DESC_A + LINKMODULE_DESC_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLinkCommand.MESSAGE_USAGE));
    }
}
