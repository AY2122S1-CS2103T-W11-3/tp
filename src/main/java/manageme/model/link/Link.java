package manageme.model.link;

import java.util.Objects;

import manageme.commons.util.CollectionUtil;
import manageme.model.ManageMeObject;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.exceptions.DuplicateLinkException;
import manageme.model.link.exceptions.LinkNotFoundException;

/**
 * Represents a link in the app.
 * Guarantees: field values are validated, immutable
 */
public class Link extends ManageMeObject {

    private final LinkAddress address;
    private final TagModule module;

    /**
     * Basic Link with only name and address.
     */
    public Link(Name name, LinkAddress address) {
        super(name);
        CollectionUtil.requireAllNonNull(address);
        this.address = address;
        this.module = TagModule.empty();
    }

    /**
     * Basic Link associated with a module.
     */
    public Link(Name name, LinkAddress address, TagModule module) {
        super(name);
        CollectionUtil.requireAllNonNull(address, module);
        this.address = address;
        this.module = module;
    }

    public LinkAddress getAddress() {
        return address;
    }

    public TagModule getLinkModule() {
        return module;
    }

    /**
     * Returns true if both links have the same link address.
     * This defines a weaker notion of equality between two links.
     * @param other
     */
    @Override
    public boolean isSame(ManageMeObject other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Link) {
            Link otherLink = (Link) other;
            return otherLink != null
                    && otherLink.getAddress().equals(getAddress());
        } else {
            return false;
        }
    }

    /**
     * Oen link in web browser.
     */
    public void open() {
        address.open();
    }

    @Override
    public void throwDuplicateException() throws RuntimeException {
        throw new DuplicateLinkException();
    }

    @Override
    public void throwNotFoundException() throws RuntimeException {
        throw new LinkNotFoundException();
    }

    /**
     * Returns true if both mods have the same identity and data fields.
     * This defines a stronger notion of equality between two mods.
     * @param other
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Link)) {
            return false;
        }

        Link otherLink = (Link) other;
        return otherLink.getName().equals(getName())
                && otherLink.getAddress().equals(getAddress())
                && otherLink.getLinkModule().equals(getLinkModule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getName(), address, module);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Address: ")
                .append(getAddress())
                .append("; TagModule: ")
                .append(getLinkModule());
        return builder.toString();
    }
}
