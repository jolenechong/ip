package simon.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import simon.Response;
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
    @FXML
    private HBox inputBox;
    @FXML
    private javafx.scene.control.Button sendButton;

    private Simon simon;

    /**
     * Initializes the controller. Binds scroll and dialog container padding so content is visible.
     */
    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Simon instance.
     *
     * @param simon the Simon instance.
     */
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
        Response resp = simon.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getSimonDialog(resp.getMessage())
        );
        userInput.clear();

        if (resp.isExitRequested()) {
            Platform.runLater(() -> {
                PauseTransition delay = new PauseTransition(Duration.millis(500));
                delay.setOnFinished(evt -> Platform.exit());
                delay.play();
            });
        }
    }
}
