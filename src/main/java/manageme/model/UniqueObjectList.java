package manageme.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manageme.commons.util.CollectionUtil;

public class UniqueObjectList<T extends ManageMeObject> implements Iterable<T> {
    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent module as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    public ObservableList<T> getInternalList() {
        return internalList;
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void add(T toAdd) throws RuntimeException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            toAdd.throwDuplicateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedT}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedT} must not be the same as another existing module in the list.
     */
    public void setT(T target, T editedT) {
        CollectionUtil.requireAllNonNull(target, editedT);

        int index = internalList.indexOf(target);
        if (index == -1) {
            target.throwNotFoundException();
        }

        if (!target.isSame(editedT) && contains(editedT)) {
            target.throwDuplicateException();
        }

        internalList.set(index, editedT);
    }

    /**
     * Removes the equivalent module from the list.
     * The module must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            toRemove.throwNotFoundException();
        }
    }

    public void setTs(UniqueObjectList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code objects}.
     * {@code objects} must not contain duplicate objects.
     */
    public void setTs(List<T> objects) {
        CollectionUtil.requireAllNonNull(objects);
        if (!objectsAreUnique(objects)) {
            objects.get(0).throwDuplicateException();
        }

        internalList.setAll(objects);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueObjectList // instanceof handles nulls
                && internalList.equals(((UniqueObjectList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code objects} contains only unique objects.
     */
    protected boolean objectsAreUnique(List<T> objects) {
        for (int i = 0; i < objects.size() - 1; i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                if (objects.get(i).isSame(objects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
