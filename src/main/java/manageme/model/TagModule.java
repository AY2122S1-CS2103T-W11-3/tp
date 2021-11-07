package manageme.model;

import static java.util.Objects.requireNonNull;
import static manageme.commons.util.AppUtil.checkArgument;

import java.util.Optional;


public class TagModule {
    public static final String MESSAGE_CONSTRAINTS = "Modules should only contain alphanumeric characters "
            + "and whitespaces";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    //This allows whitespace in between characters
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;
    public final Optional<String> name;

    /**
     * Constructs a {@code TagModule}.
     *
     * @param linkModule A Link Module.
     */
    public TagModule(String linkModule) {
        requireNonNull(linkModule);
        checkArgument(isValidModule(linkModule), MESSAGE_CONSTRAINTS);
        this.value = linkModule;
        this.name = Optional.of(linkModule);
    }

    /**
     * Constructs an empty {@code TagModule}.
     */
    public TagModule() {
        this.value = "";
        this.name = Optional.empty();
    }

    /**
     * Returns true if a given string is a valid Module.
     */
    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX) || test.equals("");
    }

    @Override
    public String toString() {
        if (value == "") {
            return "Not tagged";
        } else {
            return value;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagModule // instanceof handles nulls
                && value.equals(((TagModule) other).value)); // state check
    }

    public static TagModule empty() {
        return new TagModule();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
