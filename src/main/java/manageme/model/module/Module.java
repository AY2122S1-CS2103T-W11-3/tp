package manageme.model.module;

import manageme.model.ManageMeObject;
import manageme.model.Name;
import manageme.model.module.exceptions.DuplicateModuleException;
import manageme.model.module.exceptions.ModuleNotFoundException;

/**
 * Represents a Module in the app.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Module extends ManageMeObject {

    /**
     * Link optional
     */
    public Module(Name name) {
        super(name);
    }

    /**
     * Returns true if both mods have the same name.
     */
    @Override
    public boolean isSame(ManageMeObject otherMod) {
        if (otherMod == this) {
            return true;
        }
        if (otherMod instanceof Module) {
            return otherMod != null
                    && otherMod.getName().equals(getName());
        } else {
            return false;
        }
    }

    @Override
    public void throwDuplicateException() throws RuntimeException {
        throw new DuplicateModuleException();
    }

    public void throwNotFoundException() throws RuntimeException {
        throw new ModuleNotFoundException();
    }

    /**
     * Returns true if both mods have the same identity.
     * This is the same as #isSame because of how Module is implemented,
     * two Modules will be equals as long as they have the same Name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherMod = (Module) other;
        return otherMod.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own

        return getName().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());

        return builder.toString();
    }
}
