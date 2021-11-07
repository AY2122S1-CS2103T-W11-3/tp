package manageme.ui.module;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import manageme.model.module.Module;
import manageme.ui.UiPart;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/ManageMe-level4/issues/336">The issue on ManageMe level 4</a>
     */

    public final Module module;

    @FXML
    private HBox moduleCardPane;
    @FXML
    private Label name;
    @FXML
    private Label moduleId;

    /**
     * Creates a {@code moduleCode} with the given {@code module} and index to display.
     */
    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        moduleId.setText(displayedIndex + ". ");
        name.setText(module.getName().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        ModuleCard card = (ModuleCard) other;
        return moduleId.getText().equals(card.moduleId.getText())
                && module.equals(card.module);
    }
}
