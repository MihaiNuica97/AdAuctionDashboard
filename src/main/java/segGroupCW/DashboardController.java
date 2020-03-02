package segGroupCW;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {


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
    private Button bounceDefinitionButton;

    @FXML
    private Pane pane5;

    @FXML
    private Label value5;

    @FXML
    private Pane pane6;

    @FXML
    private Label value6;

    @FXML
    private Pane pane61;

    @FXML
    private Label value61;

    @FXML
    private Pane pane62;

    @FXML
    private Label value62;

    @FXML
    private Pane pane63;

    @FXML
    private Label value63;

    @FXML
    private Pane pane64;

    @FXML
    private Label value64;

    @FXML
    private Pane pane65;

    @FXML
    private Label value65;

    @FXML
    private LineChart<?, ?> lineChart1;

    @FXML
    private LineChart<?, ?> lineChart11;

    @FXML
    private LineChart<?, ?> lineChart12;

    @FXML
    private LineChart<?, ?> lineChart13;

    @FXML
    private LineChart<?, ?> lineChart131;

    @FXML
    private Pane totalCostPane;

    @FXML
    private LineChart<?, ?> lineChart132;

    @FXML
    protected RadioButton LineChartRadioGraph;

    @FXML
    private RadioButton pieChartRadioButton;

    @FXML
    private LineChart<?, ?> lineChart133;

    @FXML
    private LineChart<?, ?> lineChart134;

    @FXML
    private LineChart<?, ?> lineChart1341;

    @FXML
    private LineChart<?, ?> lineChart1342;

    @FXML
    private LineChart<?, ?> lineChart1343;

    private PieChart pieChart;




    @FXML
    void pieChartChange() {
        totalCostPane.getChildren().remove(lineChart132);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Click Cost", 13),
                        new PieChart.Data("Impression Cost", 25));
        pieChart = new PieChart(pieChartData);
        totalCostPane.getChildren().add(pieChart);

        //pieChart.autosize();
        pieChart.setMaxHeight(50);
    }

    void lineChartChange() {
        totalCostPane.getChildren().remove(pieChart);
        totalCostPane.getChildren().add(lineChart132);

        //pieChart.autosize();
        pieChart.setMaxHeight(50);
    }

    @FXML
    void lineChartChange(ActionEvent event) {
        lineChartChange();
    }

    public void pieChartOption(ActionEvent actionEvent) {
        pieChartChange();
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("fileUpload");
    }

    @FXML
    private void clicksLabel(){
        value1.setText("chickennnnn");
    }


    @FXML
    void bounceChange(MouseEvent event) {



        /*
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();


        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(theStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();

         */
    }



}