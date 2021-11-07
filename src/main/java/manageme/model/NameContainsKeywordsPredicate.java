package manageme.model;

import java.util.List;
import java.util.function.Predicate;

import manageme.commons.util.StringUtil;

public class NameContainsKeywordsPredicate<T extends ManageMeObject> implements Predicate<T> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(T obj) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(obj.getName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
