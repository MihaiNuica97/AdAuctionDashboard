package segGroupCW;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class SecondaryController {

    @FXML
    private HBox topHBox;

    //topHBox.setSpacing(50);

    @FXML
    private Button accountButton;

    @FXML
    private Button changeCampaignButton;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}