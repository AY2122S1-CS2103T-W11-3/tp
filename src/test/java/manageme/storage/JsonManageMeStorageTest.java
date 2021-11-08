package manageme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import manageme.commons.exceptions.DataConversionException;
import manageme.model.ManageMe;
import manageme.model.ReadOnlyManageMe;
import manageme.testutil.Assert;
import manageme.testutil.TypicalLinks;
import manageme.testutil.TypicalManageMe;

public class JsonManageMeStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonManageMeStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readManageMe_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readManageMe(null));
    }

    private java.util.Optional<ReadOnlyManageMe> readManageMe(String filePath) throws Exception {
        return new JsonManageMeStorage(Paths.get(filePath)).readManageMe(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readManageMe("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readManageMe("notJsonFormatManageMe.json"));
    }

    @Test
    public void readManageMe_invalidPersonManageMe_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readManageMe("invalidLinkManageMe.json"));
    }

    @Test
    public void readManageMe_invalidTaskManageMe_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readManageMe("invalidTaskManageMe.json"));
    }

    @Test
    public void readManageMe_invalidAndValidPersonManageMe_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readManageMe("invalidAndValidLinkManageMe.json"));
    }

    @Test
    public void readManageMe_invalidAndValidTaskManageMe_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readManageMe("invalidAndValidTaskManageMe.json"));
    }

    @Test
    public void readAndSaveManageMe_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempManageMe.json");
        ManageMe original = TypicalManageMe.getTypicalManageMe();
        JsonManageMeStorage jsonManageMeStorage = new JsonManageMeStorage(filePath);

        // Save in new file and read back
        jsonManageMeStorage.saveManageMe(original, filePath);
        ReadOnlyManageMe readBack = jsonManageMeStorage.readManageMe(filePath).get();
        assertEquals(original, new ManageMe(readBack));

        // Modify data, overwrite exiting file, and read back
        original.remove(TypicalLinks.LINK_B);
        jsonManageMeStorage.saveManageMe(original, filePath);
        readBack = jsonManageMeStorage.readManageMe(filePath).get();
        assertEquals(original, new ManageMe(readBack));

        // Save and read without specifying file path
        original.add(TypicalLinks.LINK_B);
        jsonManageMeStorage.saveManageMe(original); // file path not specified
        readBack = jsonManageMeStorage.readManageMe().get(); // file path not specified
        assertEquals(original, new ManageMe(readBack));

    }

    @Test
    public void saveManageMe_nullManageMe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveManageMe(null, "SomeFile.json"));
    }

    /**
     * Saves {@code ManageMe} at the specified {@code filePath}.
     */
    private void saveManageMe(ReadOnlyManageMe manageMe, String filePath) {
        try {
            new JsonManageMeStorage(Paths.get(filePath))
                    .saveManageMe(manageMe, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveManageMe_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveManageMe(new ManageMe(), null));
    }
}
