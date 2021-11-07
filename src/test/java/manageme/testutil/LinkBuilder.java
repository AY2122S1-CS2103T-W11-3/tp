package manageme.testutil;

import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.link.LinkAddress;

public class LinkBuilder {
    public static final String DEFAULT_NAME = "Tutorial";
    public static final String DEFAULT_LINK = "https://www.luminus.com";
    public static final String DEFAULT_MODULE = "CS2100";

    private Name name;
    private LinkAddress address;
    private TagModule module;

    /**
     * Creates a {@code LinkBuilder} with the default details.
     */
    public LinkBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new LinkAddress(DEFAULT_LINK);
        module = new TagModule(DEFAULT_MODULE);
    }

    /**
     * Initializes the LinkBuilder with the data of {@code LinkToCopy}.
     */
    public LinkBuilder(Link linkToCopy) {
        name = linkToCopy.getName();
        address = linkToCopy.getAddress();
        module = linkToCopy.getLinkModule();
    }

    /**
     * Sets the {@code Name} of the {@code Link} that we are building.
     */
    public LinkBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code LinkAddress} of the {@code Link} that we are building.
     */
    public LinkBuilder withAddress(String address) {
        this.address = new LinkAddress(address);
        return this;
    }

    /**
     * Sets the {@code TagModule} of the {@code Task} that we are building.
     */
    public LinkBuilder withModule(String linkModule) {
        this.module = linkModule.equals("") ? TagModule.empty() : new TagModule(linkModule);
        return this;
    }

    public Link build() {
        return new Link(name, address, module);
    }
}
