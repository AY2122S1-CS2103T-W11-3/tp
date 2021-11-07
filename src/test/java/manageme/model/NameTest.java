package manageme.model;

import static manageme.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null Name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid Name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("CS2100*")); // contains non-alphanumeric characters

        // valid Name
        assertTrue(Name.isValidName("CSCS")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("cs2100")); // alphanumeric characters
        assertTrue(Name.isValidName("CS2100")); // with capital letters
        assertTrue(Name.isValidName("CS2100 Computer Organization")); // long Names
    }
}
