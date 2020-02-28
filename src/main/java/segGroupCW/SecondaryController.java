package segGroupCW;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SecondaryController {


    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane pane1;

    @FXML
    private Label value1;

    @FXML
    private Pane pane2;

    @FXML
    private Label value2;

    @FXML
    private Pane pane3;

    @FXML
    private Label value3;

    @FXML
    private Pane pane4;

    @FXML
    private Label value4;

    @FXML
    private Pane pane5;

    @FXML
    private Label value5;

    @FXML
    private Pane pane6;

    @FXML
    private Label value6;

    @FXML
    private LineChart<?, ?> lineChart1;

    @FXML
    private LineChart<?, ?> lineChart2;

    @FXML
    private LineChart<?, ?> lineChart3;

    @FXML
    private LineChart<?, ?> lineChart4;

    @FXML
    private LineChart<?, ?> lineChart5;

    @FXML
    private LineChart<?, ?> lineChart6;

    @FXML
    private VBox campaignVBox;

    @FXML
    private Label campaignLabel;

    @FXML
    private Button campaign1Button;

    @FXML
    private Button campaign2Button;

    @FXML
    private Button accountButton;

    @FXML
    private ImageView accountImageView;

    @FXML
    private Pane topHBox;

    @FXML
    private Label dashBoardLabel;

    @FXML
    private Button changeCampaignButton;

    @FXML
    private Button filterButton;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

}