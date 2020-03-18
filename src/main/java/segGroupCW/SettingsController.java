package segGroupCW;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class SettingsController {

    @FXML
    void backToDashboard(ActionEvent event) throws IOException {
        App.setRoot("dashboard");

    }
}
