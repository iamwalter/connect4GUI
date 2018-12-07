package Controllers;

import Views.ChangeWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HowToController {
    @FXML
    public void onQuitAction(ActionEvent event) {
        ChangeWindow.changeWindow(ChangeWindow.MAIN_MENU, event);
    }
}
