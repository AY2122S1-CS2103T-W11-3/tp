package manageme.testutil;

import manageme.logic.commands.task.EditTaskCommand;
import manageme.model.Name;
import manageme.model.task.Task;
import manageme.model.task.TaskDescription;
import manageme.model.task.TaskTime;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {
    private EditTaskCommand.EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
    }

    /**
     * Creates a new descriptor based on the parameters.
     * @param descriptor a descriptor passed into the function.
     */
    public EditTaskDescriptorBuilder(EditTaskCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setDescription(task.getDescription());
        descriptor.setModule(task.getTagModule());
        descriptor.setStart(task.getStart());
        descriptor.setEnd(task.getEnd());
    }


    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new TaskDescription(description));
        return this;
    }

    /**
     * Sets the {@code TagModule} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withModule(String module) {
        descriptor.setModule(new manageme.model.TagModule(module));
        return this;
    }

    /**
     * Sets the {@code start} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStartDateTime(String start) {
        descriptor.setStart(new TaskTime(start));
        return this;
    }

    /**
     * Sets the {@code end} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withEndDateTime(String end) {
        descriptor.setEnd(new TaskTime(end));
        return this;
    }

    public EditTaskCommand.EditTaskDescriptor build() {
        return descriptor;
    }
}
