package simon.ui;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private ArrayList<String> inputHistory = new ArrayList<>();
    private int historyPointer = -1;
    private String input;

    /**
     * Initializes the controller. Binds scroll and dialog container padding so content is visible.
     */
    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        userInput.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.UP) {
                handleKeyUp();
                e.consume();
            } else if (e.getCode() == KeyCode.DOWN) {
                handleKeyDown();
                e.consume();
            }
        });
    }

    private void handleKeyUp() {
        if (inputHistory.size() > 0 && historyPointer < inputHistory.size() - 1) {
            historyPointer++;
            userInput.setText(inputHistory.get(inputHistory.size() - 1 - historyPointer));
            userInput.positionCaret(userInput.getText().length());
        }
    }

    private void handleKeyDown() {
        if (historyPointer > 0) {
            historyPointer--;
            userInput.setText(inputHistory.get(inputHistory.size() - 1 - historyPointer));
        } else {
            historyPointer = -1;
            userInput.clear();
        }
    }

    /**
     * Injects the Simon instance.
     *
     * @param simon the Simon instance.
     */
    public void setSimon(Simon simon) {
        this.simon = simon;
    }

    private void setUpUserInput() {
        input = userInput.getText();
        if (input.isBlank()) {
            return;
        }
        inputHistory.add(input);
        historyPointer = -1;
    }

    private Response setUpDialogContainer() {
        Response resp = simon.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getSimonDialog(resp.getMessage())
        );
        userInput.clear();
        return resp;
    }

    private void handleResponseExit(Response resp) {
        if (resp.isExitRequested()) {
            Platform.runLater(() -> {
                PauseTransition delay = new PauseTransition(Duration.millis(500));
                delay.setOnFinished(evt -> Platform.exit());
                delay.play();
            });
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {

        setUpUserInput();
        Response resp = setUpDialogContainer();
        handleResponseExit(resp);

    }
}
