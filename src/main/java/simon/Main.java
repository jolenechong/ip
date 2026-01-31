package simon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simon.ui.MainWindow;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Simon simon = new Simon();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // load the dialog-box stylesheet from resources/css
            scene.getStylesheets().add(Main.class.getResource("/css/dialog-box.css").toExternalForm());

            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/simon.png")));

            fxmlLoader.<MainWindow>getController().setSimon(simon);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
