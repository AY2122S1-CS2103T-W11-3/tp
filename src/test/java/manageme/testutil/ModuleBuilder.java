package manageme.testutil;

import manageme.model.Name;
import manageme.model.module.Module;

public class ModuleBuilder {
    public static final String DEFAULT_NAME = "CS2100";

    private Name name;

    /**
     * Creates a {@code ModuleBuilder} with the default details.
     */
    public ModuleBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        name = moduleToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Module} that we are building.
     */
    public ModuleBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Module build() {
        return new Module(name);
    }
}
