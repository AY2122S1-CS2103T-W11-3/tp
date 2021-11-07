package manageme.model.link;

import static manageme.testutil.Assert.assertThrows;
import static manageme.testutil.TypicalLinks.LINK_A;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LinkAddressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkAddress(null));
    }

    @Test
    public void constructor_invalidLinkAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new LinkAddress(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null name
        assertThrows(NullPointerException.class, () -> LinkAddress.isValidLinkAddress(null));

        // invalid name
        assertFalse(LinkAddress.isValidLinkAddress("")); // empty string
        assertFalse(LinkAddress.isValidLinkAddress(" ")); // spaces only
        assertFalse(LinkAddress.isValidLinkAddress("www.facebook.com")); // address without headings

        // valid name
        assertTrue(LinkAddress.isValidLinkAddress("https://www.google.com")); // alphabets only
        assertTrue(LinkAddress.isValidLinkAddress("ftp://192.168.0.1")); // numbers only
        assertTrue(LinkAddress.isValidLinkAddress("file://home/lisa/test.docx")); // alphanumeric characters
    }

    @Test
    public void equals() {
        // same name, returns true
        assertTrue(LINK_A.getAddress().equals(LINK_A.getAddress()));

    }
}
