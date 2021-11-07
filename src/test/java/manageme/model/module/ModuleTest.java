package manageme.model.module;

import static manageme.logic.commands.module.ModuleCommandTestUtil.VALID_MODNAME_A;
import static manageme.testutil.TypicalModules.MODULE_A;
import static manageme.testutil.TypicalModules.MODULE_B;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import manageme.testutil.ModuleBuilder;

public class ModuleTest {

    @Test
    public void isSame() {
        // same object -> returns true
        assertTrue(MODULE_A.isSame(MODULE_A));

        // null -> returns false
        assertFalse(MODULE_A.isSame(null));

        // same name -> returns true
        Module editedCS2100 = new ModuleBuilder(MODULE_A).build();
        assertTrue(MODULE_A.isSame(editedCS2100));

        // name differs in case, all other attributes same -> returns false
        Module smallCaseCS2100 = new ModuleBuilder().withName(VALID_MODNAME_A.toLowerCase()).build();
        assertFalse(MODULE_A.isSame(smallCaseCS2100));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_MODNAME_A + " ";
        editedCS2100 = new ModuleBuilder(MODULE_A).withName(nameWithTrailingSpaces).build();
        assertFalse(MODULE_A.isSame(editedCS2100));
    }

    @Test
    public void equals() {
        Module moduleA = new ModuleBuilder(MODULE_A).build();
        // same values -> returns true
        Module moduleCS2100Copy = new ModuleBuilder(MODULE_A).build();
        assertTrue(moduleA.equals(moduleCS2100Copy));

        // same object -> returns true
        assertTrue(moduleA.equals(moduleA));

        // null -> returns false
        assertFalse(moduleA.equals(null));

        // different type -> returns false
        assertFalse(moduleA.equals(5));

        // different Module object -> returns false
        assertFalse(moduleA.equals(MODULE_B));

        // different name -> returns false
        Module editedCS2103 = new ModuleBuilder(MODULE_B).withName(VALID_MODNAME_A).build();
        assertFalse(MODULE_B.equals(editedCS2103));
    }
}
