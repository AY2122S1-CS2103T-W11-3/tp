package manageme.testutil;

import manageme.logic.commands.link.EditLinkCommand.EditLinkDescriptor;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.link.LinkAddress;

/**
 * A utility class to help with building EditLinkDescriptor objects.
 */
public class EditLinkDescriptorBuilder {

    private EditLinkDescriptor descriptor;

    public EditLinkDescriptorBuilder() {
        descriptor = new EditLinkDescriptor();
    }

    public EditLinkDescriptorBuilder(EditLinkDescriptor descriptor) {
        this.descriptor = new EditLinkDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLinkDescriptor} with fields containing {@code link}'s details
     */
    public EditLinkDescriptorBuilder(Link link) {
        descriptor = new EditLinkDescriptor();
        descriptor.setName(link.getName());
        descriptor.setAddress(link.getAddress());
        descriptor.setModule(link.getLinkModule());
    }

    /**
     * Sets the {@code Name} of the {@code EditLinkDescriptor} that we are building.
     */
    public EditLinkDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditLinkDescriptor} that we are building.
     */
    public EditLinkDescriptorBuilder withModule(String module) {
        descriptor.setModule(new TagModule(module));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditLinkDescriptor} that we are building.
     */
    public EditLinkDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new LinkAddress(address));
        return this;
    }

    public EditLinkDescriptor build() {
        return descriptor;
    }
}
