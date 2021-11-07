package manageme.model.module;

import static manageme.testutil.Assert.assertThrows;
import static manageme.testutil.TypicalModules.MODULE_A;
import static manageme.testutil.TypicalModules.MODULE_B;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import manageme.model.module.exceptions.DuplicateModuleException;
import manageme.model.module.exceptions.ModuleNotFoundException;
import manageme.testutil.ModuleBuilder;

public class UniqueModuleListTest {
    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void contains_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.contains(null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(MODULE_A));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueModuleList.add(MODULE_A);
        assertTrue(uniqueModuleList.contains(MODULE_A));
    }

    @Test
    public void contains_moduleWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModuleList.add(MODULE_A);
        Module editedAlice = new ModuleBuilder(MODULE_A).build();
        assertTrue(uniqueModuleList.contains(editedAlice));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.add(null));
    }

    @Test
    public void add_duplicateModule_throwsDuplicateModuleException() {
        uniqueModuleList.add(MODULE_A);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.add(MODULE_A));
    }

    @Test
    public void setT_nullTargetModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setT(null, MODULE_A));
    }

    @Test
    public void setT_nullEditedModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setT(MODULE_A, null));
    }

    @Test
    public void setT_targetModuleNotInList_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.setT(MODULE_A, MODULE_A));
    }

    @Test
    public void setT_editedModuleisSame_success() {
        uniqueModuleList.add(MODULE_A);
        uniqueModuleList.setT(MODULE_A, MODULE_A);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(MODULE_A);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setT_editedModuleHasSameIdentity_success() {
        uniqueModuleList.add(MODULE_A);
        Module editedModule = new ModuleBuilder(MODULE_A).withName("edited").build();
        uniqueModuleList.setT(MODULE_A, editedModule);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(editedModule);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setT_editedModuleHasDifferentIdentity_success() {
        uniqueModuleList.add(MODULE_A);
        uniqueModuleList.setT(MODULE_A, MODULE_B);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(MODULE_B);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setT_editedModuleHasNonUniqueIdentity_throwsDuplicateModuleException() {
        uniqueModuleList.add(MODULE_A);
        uniqueModuleList.add(MODULE_B);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setT(MODULE_A, MODULE_B));
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.remove(null));
    }

    @Test
    public void remove_moduleDoesNotExist_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.remove(MODULE_A));
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueModuleList.add(MODULE_A);
        uniqueModuleList.remove(MODULE_A);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setTs_nullUniqueModuleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setTs((UniqueModuleList) null));
    }

    @Test
    public void setTs_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(MODULE_A);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(MODULE_B);
        uniqueModuleList.setTs(expectedUniqueModuleList);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setTs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setTs((List<Module>) null));
    }

    @Test
    public void setTs_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(MODULE_A);
        List<Module> moduleList = Collections.singletonList(MODULE_B);
        uniqueModuleList.setTs(moduleList);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(MODULE_B);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setTs_listWithDuplicateModules_throwsDuplicateModuleException() {
        List<Module> listWithDuplicateModules = Arrays.asList(MODULE_A, MODULE_A);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setTs(listWithDuplicateModules));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueModuleList.asUnmodifiableObservableList().remove(0));
    }
}
