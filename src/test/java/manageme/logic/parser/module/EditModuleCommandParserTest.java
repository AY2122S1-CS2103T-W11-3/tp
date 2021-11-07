package manageme.logic.parser.module;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static manageme.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static manageme.logic.commands.module.ModuleCommandTestUtil.MODNAME_DESC_A;
import static manageme.logic.commands.module.ModuleCommandTestUtil.MODNAME_DESC_B;
import static manageme.logic.commands.module.ModuleCommandTestUtil.VALID_MODNAME_A;
import static manageme.logic.commands.module.ModuleCommandTestUtil.VALID_MODNAME_B;
import static manageme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static manageme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import manageme.commons.core.index.Index;
import manageme.logic.commands.module.EditModuleCommand;
import manageme.model.Name;
import manageme.testutil.EditModuleDescriptorBuilder;

public class EditModuleCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE);

    private EditModuleCommandParser parser = new EditModuleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_MODNAME_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditModuleCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + MODNAME_DESC_B, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + MODNAME_DESC_B, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        // TODO: Test Link inputs when its validation is implemented
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + MODNAME_DESC_A;

        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withName(VALID_MODNAME_A).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + MODNAME_DESC_A + MODNAME_DESC_B;

        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withName(VALID_MODNAME_B).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + MODNAME_DESC_A;
        EditModuleCommand.EditModuleDescriptor descriptor =
                new EditModuleDescriptorBuilder().withName(VALID_MODNAME_A).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + MODNAME_DESC_A + MODNAME_DESC_B;
        descriptor = new EditModuleDescriptorBuilder().withName(VALID_MODNAME_B).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
