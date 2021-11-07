package manageme.model;

import static manageme.model.Model.PREDICATE_SHOW_ALL_LINKS;
import static manageme.testutil.TypicalLinks.LINK_A;
import static manageme.testutil.TypicalLinks.LINK_B;
import static manageme.testutil.TypicalModules.MODULE_A;
import static manageme.testutil.TypicalModules.MODULE_B;
import static manageme.testutil.TypicalTasks.TASK_A;
import static manageme.testutil.TypicalTasks.TASK_B;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import manageme.commons.core.GuiSettings;
import manageme.testutil.Assert;
import manageme.testutil.ManageMeBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ManageMe(), new ManageMe(modelManager.getManageMe()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setManageMeFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setManageMeFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setManageMeFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setManageMeFilePath(null));
    }

    @Test
    public void setManageMeFilePath_validPath_setsManageMeFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setManageMeFilePath(path);
        assertEquals(path, modelManager.getManageMeFilePath());
    }

    @Test
    public void has_nullLink_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.has(null));
    }

    @Test
    public void has_linkNotInManageMe_returnsFalse() {
        assertFalse(modelManager.has(LINK_A));
    }

    @Test
    public void has_linkInManageMe_returnsTrue() {
        modelManager.add(LINK_A);
        assertTrue(modelManager.has(LINK_A));
    }

    @Test
    public void getFilteredLinkList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredLinkList().remove(0));
    }

    @Test
    public void has_nullModule_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.has(null));
    }

    @Test
    public void has_moduleNotInManageMe_returnsFalse() {
        assertFalse(modelManager.has(MODULE_A));
    }

    @Test
    public void has_moduleInManageMe_returnsTrue() {
        modelManager.add(MODULE_A);
        assertTrue(modelManager.has(MODULE_A));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

    @Test
    public void has_nullTask_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.has(null));
    }

    @Test
    public void has_taskNotInManageMe_returnsFalse() {
        assertFalse(modelManager.has(TASK_A));
    }

    @Test
    public void has_taskInManageMe_returnsTrue() {
        modelManager.add(TASK_A);
        assertTrue(modelManager.has(TASK_A));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTaskList().remove(0));
    }

    @Test
    public void equals() {
        ManageMe manageMe = new ManageMeBuilder().withModule(MODULE_A)
                .withModule(MODULE_B)
                .withLink(LINK_A).withLink(LINK_B).withTask(TASK_A)
                .withTask(TASK_B).build();
        ManageMe differentManageMe = new ManageMe();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(manageMe, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(manageMe, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different ManageMe -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentManageMe, userPrefs)));

        // different filteredList -> returns false
        //String[] keywords = TypicalLinks.ALICE.getName().fullName.split("\\s+");
        //modelManager.updateFilteredLinkList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        //assertFalse(modelManager.equals(new ModelManager(manageMe, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredLinkList(PREDICATE_SHOW_ALL_LINKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setManageMeFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(manageMe, differentUserPrefs)));
    }
}
