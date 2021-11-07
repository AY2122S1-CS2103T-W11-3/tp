package manageme.logic.parser.task;

import static manageme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static manageme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static manageme.logic.parser.CliSyntax.PREFIX_END;
import static manageme.logic.parser.CliSyntax.PREFIX_MODULE;
import static manageme.logic.parser.CliSyntax.PREFIX_NAME;
import static manageme.logic.parser.CliSyntax.PREFIX_START;

import java.util.stream.Stream;

import manageme.logic.commands.task.AddTaskCommand;
import manageme.logic.parser.ArgumentMultimap;
import manageme.logic.parser.ArgumentTokenizer;
import manageme.logic.parser.Parser;
import manageme.logic.parser.ParserUtil;
import manageme.logic.parser.Prefix;
import manageme.logic.parser.exceptions.ParseException;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.task.Task;
import manageme.model.task.TaskDescription;
import manageme.model.task.TaskTime;

public class AddTaskCommandParser implements Parser<AddTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_MODULE,
                        PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTaskCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_START) && !arePrefixesPresent(argMultimap, PREFIX_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTaskCommand.MESSAGE_START_WITHOUT_END));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        TaskDescription description =
                ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        TagModule module = argMultimap.getValue(PREFIX_MODULE).isPresent()
                ? ParserUtil.parseTagModule(argMultimap.getValue(PREFIX_MODULE).get())
                : TagModule.empty();
        TaskTime start = argMultimap.getValue(PREFIX_START).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START).get())
                : TaskTime.empty();
        TaskTime end = argMultimap.getValue(PREFIX_END).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END).get())
                : TaskTime.empty();

        if (!start.isEmpty() && !end.isEmpty() && start.getTime().isAfter(end.getTime())) {
            throw new ParseException(AddTaskCommand.MESSAGE_START_LATER_THAN_END);
        }

        if (name.value.length() > 50) {
            throw new ParseException(AddTaskCommand.MESSAGE_TASK_NAME_TOO_LONG);
        }

        if (description.value.length() > 100) {
            throw new ParseException(AddTaskCommand.MESSAGE_TASK_DESCRIPTION_TOO_LONG);
        }

        Task task = new Task(name, description, module, start, end);
        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
