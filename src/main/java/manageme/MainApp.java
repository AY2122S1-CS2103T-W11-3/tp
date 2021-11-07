package manageme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import manageme.commons.core.Config;
import manageme.commons.core.LogsCenter;
import manageme.commons.core.Version;
import manageme.commons.exceptions.DataConversionException;
import manageme.commons.util.ConfigUtil;
import manageme.commons.util.StringUtil;
import manageme.logic.Logic;
import manageme.logic.LogicManager;
import manageme.model.ManageMe;
import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.ReadOnlyManageMe;
import manageme.model.ReadOnlyUserPrefs;
import manageme.model.UserPrefs;
import manageme.model.util.SampleDataUtil;
import manageme.storage.JsonManageMeStorage;
import manageme.storage.JsonUserPrefsStorage;
import manageme.storage.ManageMeStorage;
import manageme.storage.Storage;
import manageme.storage.StorageManager;
import manageme.storage.UserPrefsStorage;
import manageme.time.Time;
import manageme.time.TimeManager;
import manageme.ui.Ui;
import manageme.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Time time;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ManageMe ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ManageMeStorage manageMeStorage = new JsonManageMeStorage(userPrefs.getManageMeFilePath());
        storage = new StorageManager(manageMeStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        time = new TimeManager(model.getManageMe());

        logic = new LogicManager(model, storage, time);

        ui = new UiManager(logic);

    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyManageMe> manageMeOptional;
        ReadOnlyManageMe initialData;
        try {
            manageMeOptional = storage.readManageMe();
            if (!manageMeOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ManageMe");
            }
            initialData = manageMeOptional.orElseGet(SampleDataUtil::getSampleManageMe);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ManageMe");
            initialData = new ManageMe();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ManageMe");
            initialData = new ManageMe();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ManageMe");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ManageMe " + MainApp.VERSION);
        ui.start(primaryStage);
        time.startTime();
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            ui.stop();
            time.stopTime();
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
