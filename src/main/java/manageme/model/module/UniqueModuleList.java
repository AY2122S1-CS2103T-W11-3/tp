package manageme.model.module;

import manageme.model.UniqueObjectList;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#isSame(module)}. As such, adding and updating of
 * modules uses Module#isSame(module) for equality so as to ensure that the module being added or updated is
 * unique in terms of identity in the UniqueModuleList. However, the removal of a module uses Module#equals(Object) so
 * as to ensure that the module with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Module#isSame(Module)
 */
public class UniqueModuleList extends UniqueObjectList<Module> {
}
