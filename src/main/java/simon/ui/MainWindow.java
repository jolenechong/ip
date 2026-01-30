package simon.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simon.Simon;
import simon.ui.components.DialogBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private HBox headerBox;

    private Simon simon;

    /**
     * Initializes the controller. Binds scroll and dialog container padding so content is visible.
     */
    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // ensure the topmost dialog isn't hidden under the header by adding top padding equal to header height
        dialogContainer.paddingProperty().bind(
                Bindings.createObjectBinding(
                        () -> new Insets(headerBox.getHeight(), 0.0, 0.0, 0.0),
                        headerBox.heightProperty()
                )
        );
    }

    /** Injects the Simon instance */
    public void setSimon(Simon simon) {
        this.simon = simon;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = simon.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getSimonDialog(response)
        );
        userInput.clear();
    }
}
