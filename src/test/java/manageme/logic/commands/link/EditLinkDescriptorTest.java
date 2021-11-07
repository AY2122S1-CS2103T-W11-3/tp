package manageme.logic.commands.link;

import static manageme.logic.commands.link.LinkCommandTestUtil.DESC_LINK_A;
import static manageme.logic.commands.link.LinkCommandTestUtil.DESC_LINK_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_LINKADDRESS_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_LINKMODULE_B;
import static manageme.logic.commands.link.LinkCommandTestUtil.VALID_NAME_B;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import manageme.logic.commands.link.EditLinkCommand.EditLinkDescriptor;
import manageme.testutil.EditLinkDescriptorBuilder;

public class EditLinkDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditLinkDescriptor descriptorWithSameValues = new EditLinkDescriptor(DESC_LINK_A);
        assertTrue(DESC_LINK_A.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_LINK_A.equals(DESC_LINK_A));

        // null -> returns false
        assertFalse(DESC_LINK_A.equals(null));

        // different types -> returns false
        assertFalse(DESC_LINK_A.equals(5));

        // different values -> returns false
        assertFalse(DESC_LINK_A.equals(DESC_LINK_B));

        // different name -> returns false
        EditLinkDescriptor editedLinkA = new EditLinkDescriptorBuilder(DESC_LINK_A).withName(VALID_NAME_B).build();
        assertFalse(DESC_LINK_A.equals(editedLinkA));

        // different address -> returns false
        editedLinkA = new EditLinkDescriptorBuilder(DESC_LINK_A).withAddress(VALID_LINKADDRESS_B).build();
        assertFalse(DESC_LINK_A.equals(editedLinkA));

        // different module -> returns false
        editedLinkA = new EditLinkDescriptorBuilder(DESC_LINK_A).withModule(VALID_LINKMODULE_B).build();
        assertFalse(DESC_LINK_A.equals(editedLinkA));
    }
}
