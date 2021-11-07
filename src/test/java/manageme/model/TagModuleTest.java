package manageme.model;

import static manageme.testutil.Assert.assertThrows;
import static manageme.testutil.TypicalLinks.LINK_A;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TagModuleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagModule(null));
    }
    @Test
    public void constructor_invalidLinkModule_throwsIllegalArgumentException() {
        String invalidModule = "**";
        assertThrows(IllegalArgumentException.class, () -> new TagModule(invalidModule));
    }
    @Test
    public void isValidModule() {
        // null module
        assertThrows(NullPointerException.class, () -> manageme.model.TagModule.isValidModule(null));

        // invalid module
        assertFalse(manageme.model.TagModule.isValidModule("^")); // only non-alphanumeric characters
        assertFalse(manageme.model.TagModule.isValidModule("hello*")); // contains non-alphanumeric characters

        // valid module
        assertTrue(manageme.model.TagModule.isValidModule("CSABCD")); // alphabets only
        assertTrue(manageme.model.TagModule.isValidModule("12345")); // numbers only
        assertTrue(manageme.model.TagModule.isValidModule("cs2100")); // alphanumeric characters
        assertTrue(manageme.model.TagModule.isValidModule("CS2103T")); // with capital letters
    }
    @Test
    public void equals() {
        // same module, returns true
        assertTrue(LINK_A.getLinkModule().equals(LINK_A.getLinkModule()));
    }
    @Test
    public void empty() {
        // Empty TagModule with value set as ""
        assertTrue(TagModule.empty().value.equals(""));

        // Empty TagModule with Name set as an empty Optional<String>
        assertTrue(manageme.model.TagModule.empty().name.isEmpty());
    }
}
