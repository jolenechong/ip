package simon.ui.components;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import simon.ui.MainWindow;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;

    /**
     * Creates a DialogBox with the specified text and speaker type.
     *
     * @param text   The text to be displayed in the dialog box.
     * @param isUser True if the dialog box is for the user, false for Simon.
     */
    private DialogBox(String text, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load DialogBox FXML", e);
        }

        dialog.setText(text);
        dialog.setWrapText(true);
        dialog.setMaxWidth(260);

        if (isUser) {
            setAlignment(Pos.TOP_RIGHT);
            dialog.setStyle(
                "-fx-background-color: #26a7c7; "
                + "-fx-background-radius: 12; "
                + "-fx-padding: 8; "
                + "-fx-text-fill: white;"
            );
        } else {
            setAlignment(Pos.TOP_LEFT);
            dialog.setStyle(
                "-fx-background-color: #ededed; "
                + "-fx-background-radius: 12; "
                + "-fx-padding: 8; "
                + "-fx-text-fill: black;"
            );
        }
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for the user.
     *
     * @param text The text to be displayed in the dialog box.
     * @return A DialogBox representing the user's message.
     */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text, true);
    }

    /**
     * Creates a dialog box for Simon.
     *
     * @param text The text to be displayed in the dialog box.
     * @return A DialogBox representing Simon's message.
     */
    public static DialogBox getSimonDialog(String text) {
        var db = new DialogBox(text, false);
        db.flip();
        return db;
    }
}
