package manageme.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import manageme.logic.commands.task.TaskCommandTestUtil;
import manageme.model.ManageMe;
import manageme.model.task.Task;

public class TypicalTasks {
    public static final Task TASK_A = new TaskBuilder().withName("Eat with friends")
            .withDescription("At NUS UTown")
            .withStartDateTime("2021-10-05T11:50")
            .withEndDateTime("2021-10-05T14:00").build();
    public static final Task TASK_B = new TaskBuilder().withName("Work on CS2100 Assignment 2")
            .withDescription("Refer to lectures 7-13")
            .withModule("CS2100")
            .withStartDateTime("2021-10-07T23:59")
            .withEndDateTime("2021-10-14T23:59").build();

    // Manually added - Task's details found in {@code TaskCommandTestUtil}
    public static final Task TASK_A_MANUAL =
            new TaskBuilder().withName(TaskCommandTestUtil.VALID_NAME_A)
                    .withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_A)
                    .withModule(TaskCommandTestUtil.VALID_MODULE_A)
                    .withStartDateTime(TaskCommandTestUtil.VALID_START_A)
                    .withEndDateTime(TaskCommandTestUtil.VALID_END_A).build();
    public static final Task TASK_B_MANUAL =
            new TaskBuilder().withName(TaskCommandTestUtil.VALID_NAME_B)
                    .withDescription(TaskCommandTestUtil.VALID_DESCRIPTION_B)
                    .withModule(TaskCommandTestUtil.VALID_MODULE_B)
                    .withStartDateTime(TaskCommandTestUtil.VALID_START_B)
                    .withEndDateTime(TaskCommandTestUtil.VALID_END_B).build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code ManageMe} with all the typical tasks.
     */
    public static ManageMe getTypicalManageMe() {
        ManageMe ab = new ManageMe();
        for (Task task : getTypicalTasks()) {
            ab.add(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TASK_A, TASK_B));
    }
}
