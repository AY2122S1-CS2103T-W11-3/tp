package manageme.testutil;

import static manageme.testutil.TypicalTasks.getTypicalTasks;

import manageme.model.ManageMe;
import manageme.model.link.Link;
import manageme.model.module.Module;
import manageme.model.task.Task;

public class TypicalManageMe {
    /**
     * Returns an {@code ManageMe} with all the typical links, modules and tasks.
     */
    public static ManageMe getTypicalManageMe() {
        ManageMe ab = new ManageMe();
        for (Link link : TypicalLinks.getTypicalLinks()) {
            ab.add(link);
        }

        for (Module module : TypicalModules.getTypicalModules()) {
            ab.add(module);
        }

        for (Task task : getTypicalTasks()) {
            ab.add(task);
        }
        return ab;
    }
}
