package manageme.logic.parser.task;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static manageme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static manageme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static manageme.testutil.TypicalIndexes.INDEX_FIRST;
import static manageme.testutil.TypicalIndexes.INDEX_SECOND;
import static manageme.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import manageme.commons.core.index.Index;
import manageme.logic.commands.task.EditTaskCommand;
import manageme.logic.commands.task.TaskCommandTestUtil;
import manageme.model.Name;
import manageme.testutil.EditTaskDescriptorBuilder;


public class EditTaskCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TaskCommandTestUtil.VALID_NAME_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TaskCommandTestUtil.NAME_DESC_A, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TaskCommandTestUtil.NAME_DESC_A, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + TaskCommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);


        // valid name followed by invalid name. The test case for invalid name followed by valid name
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TaskCommandTestUtil.NAME_DESC_B
                + TaskCommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + TaskCommandTestUtil.INVALID_NAME_DESC
                + TaskCommandTestUtil.DESCRIPTION_DESC_A + TaskCommandTestUtil.MODULE_DESC_A,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + TaskCommandTestUtil.DESCRIPTION_DESC_A
                + TaskCommandTestUtil.MODULE_DESC_A + TaskCommandTestUtil.NAME_DESC_B
                + TaskCommandTestUtil.START_DESC_A + TaskCommandTestUtil.END_DESC_A;

        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil.VALID_NAME_B)
                .withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_A)
                .withModule(TaskCommandTestUtil.VALID_MODULE_A)
                .withStartDateTime(TaskCommandTestUtil.VALID_START_A)
                .withEndDateTime(TaskCommandTestUtil.VALID_END_A).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + TaskCommandTestUtil.DESCRIPTION_DESC_B
                + TaskCommandTestUtil.NAME_DESC_A;

        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_B)
                .withName(TaskCommandTestUtil.VALID_NAME_A).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TaskCommandTestUtil.NAME_DESC_A;
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil.VALID_NAME_A).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + TaskCommandTestUtil.DESCRIPTION_DESC_A;
        descriptor =
                new EditTaskDescriptorBuilder().withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_A).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // module
        userInput = targetIndex.getOneBased() + TaskCommandTestUtil.MODULE_DESC_A;
        descriptor = new EditTaskDescriptorBuilder().withModule(TaskCommandTestUtil.VALID_MODULE_A).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start
        userInput = targetIndex.getOneBased() + TaskCommandTestUtil.START_DESC_A;
        descriptor = new EditTaskDescriptorBuilder().withStartDateTime(TaskCommandTestUtil.VALID_START_A).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end
        userInput = targetIndex.getOneBased() + TaskCommandTestUtil.END_DESC_A;
        descriptor = new EditTaskDescriptorBuilder().withEndDateTime(TaskCommandTestUtil.VALID_END_A).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + TaskCommandTestUtil.NAME_DESC_A
                + TaskCommandTestUtil.DESCRIPTION_DESC_A + TaskCommandTestUtil.MODULE_DESC_A
                + TaskCommandTestUtil.START_DESC_A + TaskCommandTestUtil.END_DESC_A
                + TaskCommandTestUtil.DESCRIPTION_DESC_B + TaskCommandTestUtil.NAME_DESC_B
                + TaskCommandTestUtil.MODULE_DESC_B;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil
                        .VALID_NAME_B)
                .withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_B)
                .withModule(TaskCommandTestUtil.VALID_MODULE_B)
                .withStartDateTime(TaskCommandTestUtil.VALID_START_A)
                .withEndDateTime(TaskCommandTestUtil.VALID_END_A)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + TaskCommandTestUtil.INVALID_NAME_DESC + TaskCommandTestUtil
                .NAME_DESC_B;
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil.VALID_NAME_B).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TaskCommandTestUtil.DESCRIPTION_DESC_B
                + TaskCommandTestUtil.INVALID_NAME_DESC + TaskCommandTestUtil.MODULE_DESC_B
                + TaskCommandTestUtil.NAME_DESC_B;
        descriptor =
                new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil.VALID_NAME_B).withDescription(
                                TaskCommandTestUtil.VALID_DESCRIPTION_B)
                        .withModule(TaskCommandTestUtil.VALID_MODULE_B).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
