package segGroupCW;

import javafx.fxml.FXML;

import java.io.IOException;

public class GraphController
{

    /**
     * Brings you to the main Input page
     * @throws IOException
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("fileUpload");
    }
}
