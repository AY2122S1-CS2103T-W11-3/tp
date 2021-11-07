package manageme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import manageme.commons.exceptions.DataConversionException;
import manageme.model.ReadOnlyManageMe;
import manageme.model.ReadOnlyUserPrefs;
import manageme.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ManageMeStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getManageMeFilePath();

    @Override
    Optional<ReadOnlyManageMe> readManageMe() throws DataConversionException, IOException;

    @Override
    void saveManageMe(ReadOnlyManageMe manageMe) throws IOException;

}
