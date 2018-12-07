package Controllers;

import Views.ChangeWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Main.fxml"));

        primaryStage.setTitle("Connect 4");

        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private void onePlayerAction(ActionEvent event) {
        GameProperties.multiplayer = false;

        ChangeWindow.changeWindow(ChangeWindow.BOARD, event);
    }

    @FXML
    private void twoPlayerAction(ActionEvent event) {
        GameProperties.multiplayer = true;

        ChangeWindow.changeWindow(ChangeWindow.BOARD, event);
    }

    @FXML
    private void howToAction(ActionEvent event) {
        ChangeWindow.changeWindow(ChangeWindow.HOW_TO_PLAY, event);
    }
}
