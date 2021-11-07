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
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_LINKADDRESS_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_LINKMODULE_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_LINKMODULE_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_NAME_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_NAME_B;
import static manageme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static manageme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalIndexes.INDEX_SECOND;
import static manageme.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import manageme.commons.core.index.Index;
import manageme.logic.commands.link.EditLinkCommand;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.LinkAddress;
import manageme.testutil.EditLinkDescriptorBuilder;

public class EditLinkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLinkCommand.MESSAGE_USAGE);

    private EditLinkCommandParser parser = new EditLinkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditLinkCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_A, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_A, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_LINKADDRESS_DESC, LinkAddress.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_LINKMODULE_DESC, TagModule.MESSAGE_CONSTRAINTS);

        // invalid name followed by valid address
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + LINKADDRESS_DESC_A,
                Name.MESSAGE_CONSTRAINTS);

        // valid name followed by invalid name. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + NAME_DESC_A + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_LINKADDRESS_DESC
                + INVALID_LINKMODULE_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + NAME_DESC_A + LINKADDRESS_DESC_A + LINKMODULE_DESC_A;

        EditLinkCommand.EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder().withName(VALID_NAME_A)
                .withAddress(VALID_LINKADDRESS_A).withModule(VALID_LINKMODULE_A).build();
        EditLinkCommand expectedCommand = new EditLinkCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_A + LINKADDRESS_DESC_A;

        EditLinkCommand.EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder().withName(VALID_NAME_A)
                .withAddress(VALID_LINKADDRESS_A).build();
        EditLinkCommand expectedCommand = new EditLinkCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_A;
        EditLinkCommand.EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder().withName(VALID_NAME_A)
                .build();
        EditLinkCommand expectedCommand = new EditLinkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + LINKADDRESS_DESC_A;
        descriptor = new EditLinkDescriptorBuilder().withAddress(VALID_LINKADDRESS_A).build();
        expectedCommand = new EditLinkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module
        userInput = targetIndex.getOneBased() + LINKMODULE_DESC_A;
        descriptor = new EditLinkDescriptorBuilder().withModule(VALID_LINKMODULE_A).build();
        expectedCommand = new EditLinkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_A + LINKADDRESS_DESC_A + LINKMODULE_DESC_A
                + NAME_DESC_B + LINKADDRESS_DESC_B + LINKMODULE_DESC_B;

        EditLinkCommand.EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder().withName(VALID_NAME_B)
                .withAddress(VALID_LINKADDRESS_B).withModule(VALID_LINKMODULE_B).build();
        EditLinkCommand expectedCommand = new EditLinkCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_LINKMODULE_DESC + LINKMODULE_DESC_A;
        EditLinkCommand.EditLinkDescriptor descriptor = new EditLinkDescriptorBuilder().withModule(VALID_LINKMODULE_A)
                .build();
        EditLinkCommand expectedCommand = new EditLinkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + NAME_DESC_A + INVALID_LINKADDRESS_DESC + LINKADDRESS_DESC_A;
        descriptor = new EditLinkDescriptorBuilder().withName(VALID_NAME_A).withAddress(VALID_LINKADDRESS_A)
                .build();
        expectedCommand = new EditLinkCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
