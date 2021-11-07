package manageme.ui.link;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import manageme.model.link.Link;
import manageme.ui.UiPart;

/**
 * An UI component that displays information of a {@code Link}.
 */
public class LinkCard extends UiPart<Region> {

    private static final String FXML = "LinkListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/ManageMe-level4/issues/336">The issue on ManageMe level 4</a>
     */

    public final Link link;

    @FXML
    private VBox linkCardPane;
    @FXML
    private Label name;
    @FXML
    private Label linkId;
    @FXML
    private Label linkAddress;
    @FXML
    private Label linkModule;
    @FXML
    private Button copyButton;

    /**
     * Creates a {@code linkCode} with the given {@code link} and index to display.
     */
    public LinkCard(Link link, int displayedIndex) {
        super(FXML);
        this.link = link;
        linkId.setText(displayedIndex + ". ");
        name.setText(link.getName().value);
        linkAddress.setText(link.getAddress().value);
        linkModule.setText(link.getLinkModule().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LinkCard)) {
            return false;
        }

        // state check
        LinkCard card = (LinkCard) other;
        return linkId.getText().equals(card.linkId.getText())
                && link.equals(card.link);
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(link.getAddress().value);
        clipboard.setContent(url);
    }
}
