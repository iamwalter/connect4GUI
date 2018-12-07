package Views;

import java.io.IOException;

import Controllers.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeWindow {

    public static final String MAIN_MENU = "Main.fxml";
    public static final String HOW_TO_PLAY = "HowToPlay.fxml";
    public static final String BOARD = "FXMLBoard.fxml";

    public static void changeWindow(String path, ActionEvent event) {
        ChangeWindow window = new ChangeWindow();

        window.change(path, event);
    }

    @FXML
    private void change(String path, ActionEvent event) {
        Scene scene = new Scene(loadFXMLFile(path));
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        appStage.setTitle("Connect 4");
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    private Parent loadFXMLFile(String filepath) {
        System.out.println(filepath);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filepath));

            Parent root = fxmlLoader.load();

            return root;

        } catch (IOException e) {
            System.out.println("Something went wrong with loading the FXML file.");
        }

        return null;
    }
}