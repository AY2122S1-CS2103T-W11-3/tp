package manageme.model.link;

import static java.util.Objects.requireNonNull;

import manageme.model.UniqueObjectList;
import manageme.model.module.Module;

/**
 * A list of links that enforces uniqueness between its elements and does not allow nulls.
 * A link is considered unique by comparing using {@code Link#isSame(Link)}. As such, adding and updating of
 * links uses Link#isSame(Link) for equality so as to ensure that the link being added or updated is
 * unique in terms of identity in the UniqueLinkList. However, the removal of a link uses Link#equals(Object) so
 * as to ensure that the link with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Link#isSame(Link)
 */
public class UniqueLinkList extends UniqueObjectList<Link> {

    /**
     * Removes all links related to a module when a module is removed.
     *
     * @param module module to remove
     */
    public void removeMod(Module module) {
        requireNonNull(module);
        int i = 0;
        while (i < getInternalList().size()) {
            if (getInternalList().get(i).getLinkModule().equals(module)) {
                remove(getInternalList().get(i));
            } else {
                i++;
            }
        }
    }

    /**
     * Removes the equivalent link from the list.
     * The link must exist in the list.
     */
    public void open(Link toOpen) {
        requireNonNull(toOpen);
        toOpen.open();
    }
}
