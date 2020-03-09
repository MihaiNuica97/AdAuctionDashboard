package segGroupCW;

import javafx.fxml.FXML;

import java.io.IOException;

public class GraphController
{

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("fileUpload");
    }
}
