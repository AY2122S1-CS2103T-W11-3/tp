package manageme.testutil;

import manageme.model.ManageMe;
import manageme.model.link.Link;
import manageme.model.module.Module;
import manageme.model.task.Task;

/**
 * A utility class to help with building ManageMe objects.
 * Example usage: <br>
 *     {@code ManageMe ab = new ManageMeBuilder().withLink("John", "Doe").build();}
 */
public class ManageMeBuilder {

    private ManageMe manageMe;

    public ManageMeBuilder() {
        manageMe = new ManageMe();
    }

    public ManageMeBuilder(ManageMe manageMe) {
        this.manageMe = manageMe;
    }

    /**
     * Adds a new {@code Link} to the {@code ManageMe} that we are building.
     */
    public ManageMeBuilder withLink(Link link) {
        manageMe.add(link);
        return this;
    }

    /**
     * Adds a new {@code Module} to the {@code ManageMe} that we are building.
     */
    public ManageMeBuilder withModule(Module module) {
        manageMe.add(module);
        return this;
    }

    /**
     * Adds a new {@code Task} to the {@code ManageMe} that we are building.
     */
    public ManageMeBuilder withTask(Task task) {
        manageMe.add(task);
        return this;
    }

    public ManageMe build() {
        return manageMe;
    }
}
