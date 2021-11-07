package manageme.logic.parser.task;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static manageme.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static manageme.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static manageme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static manageme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static manageme.testutil.TypicalTasks.TASK_A_MANUAL;
import static manageme.testutil.TypicalTasks.TASK_B_MANUAL;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.task.AddTaskCommand;
import manageme.logic.commands.task.TaskCommandTestUtil;
import manageme.model.Name;
import manageme.model.task.Task;
import manageme.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(TASK_B_MANUAL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TaskCommandTestUtil.NAME_DESC_B
                + TaskCommandTestUtil.DESCRIPTION_DESC_B + TaskCommandTestUtil.MODULE_DESC_B
                + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_B, new AddTaskCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, TaskCommandTestUtil.NAME_DESC_A + TaskCommandTestUtil.NAME_DESC_B
                + TaskCommandTestUtil.DESCRIPTION_DESC_B + TaskCommandTestUtil.MODULE_DESC_B
                + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_B, new AddTaskCommand(expectedTask));

        // multiple description - last description accepted
        assertParseSuccess(parser, TaskCommandTestUtil.NAME_DESC_B + TaskCommandTestUtil.DESCRIPTION_DESC_A
                + TaskCommandTestUtil.DESCRIPTION_DESC_B + TaskCommandTestUtil.MODULE_DESC_B
                + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_B, new AddTaskCommand(expectedTask));

        // multiple modules - last module accepted
        assertParseSuccess(parser, TaskCommandTestUtil.NAME_DESC_B + TaskCommandTestUtil.DESCRIPTION_DESC_B
                + TaskCommandTestUtil.MODULE_DESC_A + TaskCommandTestUtil.MODULE_DESC_B
                + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_B, new AddTaskCommand(expectedTask));

        // multiple start date/times - last date/time accepted
        assertParseSuccess(parser, TaskCommandTestUtil.NAME_DESC_B + TaskCommandTestUtil.DESCRIPTION_DESC_B
                + TaskCommandTestUtil.MODULE_DESC_B
                + TaskCommandTestUtil.START_DESC_A + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_B,
                new AddTaskCommand(expectedTask));

        // multiple end date/times - last date/time accepted
        assertParseSuccess(parser, TaskCommandTestUtil.NAME_DESC_B + TaskCommandTestUtil.DESCRIPTION_DESC_B
                + TaskCommandTestUtil.MODULE_DESC_B + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_A
                + TaskCommandTestUtil.END_DESC_B, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero modules
        Task expectedTask = new TaskBuilder(TASK_A_MANUAL).withModule("").build();
        assertParseSuccess(parser, TaskCommandTestUtil.NAME_DESC_A + TaskCommandTestUtil.DESCRIPTION_DESC_A
                + TaskCommandTestUtil.START_DESC_A + TaskCommandTestUtil.END_DESC_A, new AddTaskCommand(expectedTask));
    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, TaskCommandTestUtil.VALID_NAME_B + TaskCommandTestUtil.DESCRIPTION_DESC_B,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TaskCommandTestUtil.NAME_DESC_B + TaskCommandTestUtil.VALID_DESCRIPTION_B,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, TaskCommandTestUtil.VALID_NAME_B + TaskCommandTestUtil.VALID_DESCRIPTION_B,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, TaskCommandTestUtil.INVALID_NAME_DESC
                + TaskCommandTestUtil.DESCRIPTION_DESC_B + TaskCommandTestUtil.MODULE_DESC_B
                + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_B , Name.MESSAGE_CONSTRAINTS);

        //Add more invalid testcases here once more are included

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TaskCommandTestUtil.NAME_DESC_B
                        + TaskCommandTestUtil.DESCRIPTION_DESC_B + TaskCommandTestUtil.MODULE_DESC_B
                        + TaskCommandTestUtil.START_DESC_B + TaskCommandTestUtil.END_DESC_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
