package manageme.model;

import manageme.commons.util.CollectionUtil;

public abstract class ManageMeObject {
    private Name name;

    public ManageMeObject(Name name) {
        this.name = name;
    }
    public abstract void throwDuplicateException() throws RuntimeException;
    public abstract void throwNotFoundException() throws RuntimeException;

    /**
     * Returns true if both objects have the same name.
     * This defines a weaker notion of equality between two objects.
     */
    public abstract boolean isSame(ManageMeObject otherObject);

    public Name getName() {
        CollectionUtil.requireAllNonNull(name);
        return name;
    }
}
