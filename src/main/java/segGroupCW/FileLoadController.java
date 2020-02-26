package segGroupCW;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.IOException;

public class FileLoadController {

    private FileChooser fileChooser;
    @FXML
    private Button browseClicksBtn;
    @FXML
    private Button browseImprBtn;
    @FXML
    private Button browseServerBtn;

    public void initialize(){
        browseClicksBtn.setOnAction( e -> browseFiles("clicks"));
        browseImprBtn.setOnAction( e -> browseFiles("impressions"));
        browseServerBtn.setOnAction( e -> browseFiles("server"));
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }


    public void browseFiles(String fileType){
        System.out.println("Choosing file with type: " + fileType);
//        fileChooser.showOpenDialog(.publicStage.getWindow());


    }


}