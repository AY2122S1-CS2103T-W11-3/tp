package manageme.testutil;

import manageme.logic.commands.module.EditModuleCommand;
import manageme.model.Name;
import manageme.model.module.Module;


public class EditModuleDescriptorBuilder {
    private EditModuleCommand.EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new EditModuleCommand.EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(EditModuleCommand.EditModuleDescriptor descriptor) {
        this.descriptor = new EditModuleCommand.EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code person}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditModuleCommand.EditModuleDescriptor();
        descriptor.setName(module.getName());;
    }

    /**
     * Sets the {@code Name} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditModuleCommand.EditModuleDescriptor build() {
        return descriptor;
    }
}
