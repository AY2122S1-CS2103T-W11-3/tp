package manageme.model.link;

import static manageme.testutil.TypicalLinks.LINK_A;
import static manageme.testutil.TypicalLinks.LINK_B;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import manageme.testutil.LinkBuilder;

public class LinkTest {

    /**
     * This tests if two Link objects have the same link content
     */
    @Test
    public void isSame() {
        // same object -> returns true
        assertTrue(LINK_A.isSame(LINK_A));

        // null -> returns false
        assertFalse(LINK_A.isSame(null));

        // same link, different name -> returns true
        Link editedLuminus = new LinkBuilder(LINK_A).withName("Lecture").build();
        assertTrue(LINK_A.isSame(editedLuminus));

        // different link, same name -> returns false
        editedLuminus = new LinkBuilder(LINK_A).withAddress("https://www.youtube.com").build();
        assertFalse(LINK_A.isSame(editedLuminus));
    }

    /**
     * This tests if two Link objects have both the same link content and the same name.
     */
    @Test
    public void equals() {
        // same values -> returns true
        Link luminusCopy = new LinkBuilder(LINK_A).build();
        assertTrue(LINK_A.equals(luminusCopy));

        // same object -> returns true
        assertTrue(LINK_A.equals(LINK_A));

        // null -> returns false
        assertFalse(LINK_A.equals(null));

        // different type -> returns false
        assertFalse(LINK_A.equals(5));

        // different Link object -> returns false
        assertFalse(LINK_A.equals(LINK_B));

        // different name -> returns false
        Link differentName = new LinkBuilder(LINK_A).withName("differentName").build();
        assertFalse(LINK_A.equals(differentName));

        // different link address -> returns false
        Link differentAddress = new LinkBuilder(LINK_A).withAddress("https://www.differentaddress.com").build();
        assertFalse(LINK_A.equals(differentAddress));

        // different link module -> return false
        Link differentModule = new LinkBuilder(LINK_A).withModule("differentModule").build();
        assertFalse(LINK_A.equals(differentModule));
    }
}
