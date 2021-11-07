package manageme.model.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import manageme.commons.util.CollectionUtil;
import manageme.model.UniqueObjectList;
import manageme.model.task.exceptions.DuplicateTaskException;
import manageme.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSame(Task)}. As such, adding and
 * updating of
 * tasks uses Task#isSame(task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#isSame(Task)
 */
public class UniqueTaskList extends UniqueObjectList<Task> {

    private ArrayList<Task> sortedList = new ArrayList<>();

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    @Override
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        getInternalList().add(toAdd);
        sortedList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    @Override
    public void setT(Task target, Task editedTask) {
        CollectionUtil.requireAllNonNull(target, editedTask);

        int index = getInternalList().indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.isSame(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        getInternalList().set(index, editedTask);
        if (!sortedList.contains(target)) {
            sortedList.add(editedTask);
        } else {
            int i = sortedList.indexOf(target);
            sortedList.set(i, editedTask);
        }
    }

    @Override
    public void setTs(UniqueObjectList<Task> replacement) {
        requireNonNull(replacement);
        getInternalList().setAll(replacement.getInternalList());
        sortedList = new ArrayList<>();
        sortedList.addAll(replacement.getInternalList());
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    @Override
    public void setTs(List<Task> tasks) {
        CollectionUtil.requireAllNonNull(tasks);
        if (!super.objectsAreUnique(tasks)) {
            throw new DuplicateTaskException();
        }

        getInternalList().setAll(tasks);
        sortedList = new ArrayList<>();
        sortedList.addAll(tasks);
    }

    /**
     * Returns the backing list as an modifiable {@code ObservableList}.
     */
    public ArrayList<Task> asModifiableObservableList() {
        sortTasksByTime();
        return sortedList;
    }

    /**
     * Sorts the tasks by their first occurrence, be it start or end date. This also removes tasks without any
     * dates specified.
     */
    private void sortTasksByTime() {
        Comparator<Task> comparator = Comparator.comparing(Task::getFirstOccurrence);
        sortedList.removeIf(task -> task.getStart().value.equals("") && task.getEnd().value.equals(""));
        sortedList.removeIf(task -> task.isDone().value);
        sortedList.sort(comparator);
    }
}
