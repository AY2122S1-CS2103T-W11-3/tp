package manageme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import manageme.commons.exceptions.IllegalValueException;
import manageme.commons.util.JsonUtil;
import manageme.model.ManageMe;
import manageme.testutil.Assert;
import manageme.testutil.TypicalManageMe;
import manageme.testutil.TypicalTasks;

public class JsonSerializableManageMeTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableManageMeTest");
    private static final Path TYPICAL_MANAGE_ME_FILE = TEST_DATA_FOLDER.resolve("typicalManageMe.json");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksManageMe.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskManageMe.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskManageMe.json");
    private static final Path INVALID_LINK_FILE = TEST_DATA_FOLDER.resolve("invalidLinkManageMe.json");
    private static final Path DUPLICATE_LINK_FILE = TEST_DATA_FOLDER.resolve("duplicateLinkManageMe.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleManageMe.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableManageMe dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableManageMe.class).get();
        ManageMe manageMeFromFile = dataFromFile.toModelType();
        ManageMe typicalTasksManageMe = TypicalTasks.getTypicalManageMe();
        assertEquals(manageMeFromFile, typicalTasksManageMe);
    }

    @Test
    public void toModelType_invalidTasksFile_throwsIllegalValueException() throws Exception {
        JsonSerializableManageMe dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableManageMe.class).orElse(null);
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalManageMeFile_success() throws Exception {
        JsonSerializableManageMe dataFromFile = JsonUtil.readJsonFile(TYPICAL_MANAGE_ME_FILE,
                JsonSerializableManageMe.class).get();
        ManageMe manageMeFromFile = dataFromFile.toModelType();
        ManageMe typicalManageMe = TypicalManageMe.getTypicalManageMe();
        assertEquals(manageMeFromFile, typicalManageMe);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableManageMe dataFromFile = JsonUtil.readJsonFile(INVALID_LINK_FILE,
                JsonSerializableManageMe.class).orElse(null);
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateLinks_throwsIllegalValueException() throws Exception {
        JsonSerializableManageMe dataFromFile = JsonUtil.readJsonFile(DUPLICATE_LINK_FILE,
                JsonSerializableManageMe.class).orElse(null);
        Assert.assertThrows(IllegalValueException.class, JsonSerializableManageMe.MESSAGE_DUPLICATE_LINK,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableManageMe dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableManageMe.class).orElse(null);
        Assert.assertThrows(IllegalValueException.class, JsonSerializableManageMe.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        JsonSerializableManageMe dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
                JsonSerializableManageMe.class).orElse(null);
        Assert.assertThrows(IllegalValueException.class, JsonSerializableManageMe.MESSAGE_DUPLICATE_TASK,
                dataFromFile::toModelType);
    }
}
