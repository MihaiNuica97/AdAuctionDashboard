package segGroupCW;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private VBox campaignVBox;

    @FXML
    private Label campaignLabel;

    @FXML
    private JFXButton campaign1Button;

    @FXML
    private JFXButton campaign2Button;

    @FXML
    private JFXButton accountButton;

    @FXML
    private ImageView accountImageView;

    @FXML
    private Pane topHBox;

    @FXML
    private Label dashBoardLabel;

    @FXML
    private JFXButton changeCampaignButton;

    @FXML
    private JFXButton filterButton;

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
    private JFXButton bounceDefinitionButton;

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
    protected JFXRadioButton LineChartRadioGraph;

    @FXML
    private JFXRadioButton pieChartRadioButton;

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
    private LineChart<?, ?> NoImpressionsChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;


    @FXML
    private LineChart<?, ?> NoImpressionsChart1;

    @FXML
    private CategoryAxis x1;

    @FXML
    private NumberAxis y1;

    @FXML
    private LineChart<?, ?> NoImpressionsChart2;

    @FXML
    private CategoryAxis x2;

    @FXML
    private NumberAxis y2;

    @FXML
    private LineChart<?, ?> NoImpressionsChart3;

    @FXML
    private CategoryAxis x3;

    @FXML
    private NumberAxis y3;

    @FXML
    private LineChart<?, ?> NoImpressionsChart31;

    @FXML
    private CategoryAxis x31;

    @FXML
    private NumberAxis y31;

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
        App.getScene().getWindow().setHeight(500);
        App.getScene().getWindow().setWidth(560);

    }


    @FXML
    void bounceChange(ActionEvent event) {

        Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("Bounce Change");

        VBox box = new VBox(10);
        Label label = new Label("Choose bounce definition:");
        box.getChildren().add(label);

        Pane pane1 = new Pane();
        //hBox1.setPrefHeight(150);
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();

        box.getChildren().add(pane1);
        box.getChildren().add(pane2);
        box.getChildren().add(pane3);

        ToggleGroup bounceToggle = new ToggleGroup();

        JFXRadioButton bounceDefinition1 = new JFXRadioButton("Entry date & exit date are too close together");
        JFXRadioButton bounceDefinition2 = new JFXRadioButton("Number of pages viewed is below a threshold");
        JFXRadioButton bounceDefinition3 = new JFXRadioButton("Conversion did not take place");

        bounceDefinition1.setToggleGroup(bounceToggle);
        bounceDefinition2.setToggleGroup(bounceToggle);
        bounceDefinition3.setToggleGroup(bounceToggle);

        bounceDefinition1.setSelected(true);

        box.getChildren().add(bounceDefinition1);
        box.getChildren().add(bounceDefinition2);
        box.getChildren().add(bounceDefinition3);

        pane1.getChildren().add(bounceDefinition1);
        pane2.getChildren().add(bounceDefinition2);
        pane3.getChildren().add(bounceDefinition3);


        Scene newScene = new Scene(box, 350, 120);
        newStage.setScene(newScene);
        newStage.showAndWait();
        
        

/*

        box.getChildren().add(hBox1);
        box.getChildren().add(hBox2);
        box.getChildren().add(hBox3);

        ToggleGroup bounceToggle = new ToggleGroup();

        RadioButton bounceDefinition1 = new RadioButton("def 1");
        RadioButton bounceDefinition2 = new RadioButton("def2");
        RadioButton bounceDefinition3 = new RadioButton("def3");

        bounceDefinition1.setToggleGroup(bounceToggle);
        bounceDefinition2.setToggleGroup(bounceToggle);
        bounceDefinition3.setToggleGroup(bounceToggle);

        box.getChildren().add(bounceDefinition1);
        box.getChildren().add(bounceDefinition2);
        box.getChildren().add(bounceDefinition3);

        hBox1.getChildren().add(bounceDefinition1);
        hBox2.getChildren().add(bounceDefinition2);
        hBox3.getChildren().add(bounceDefinition3);

*/




/*

        box.getChildren().add(hBox1);
        box.getChildren().add(hBox2);
        box.getChildren().add(hBox3);

        ToggleGroup bounceToggle = new ToggleGroup();

        RadioButton bounceDefinition1 = new RadioButton("def 1");
        RadioButton bounceDefinition2 = new RadioButton("def2");
        RadioButton bounceDefinition3 = new RadioButton("def3");

        bounceDefinition1.setToggleGroup(bounceToggle);
        bounceDefinition2.setToggleGroup(bounceToggle);
        bounceDefinition3.setToggleGroup(bounceToggle);

        box.getChildren().add(bounceDefinition1);
        box.getChildren().add(bounceDefinition2);
        box.getChildren().add(bounceDefinition3);

        hBox1.getChildren().add(bounceDefinition1);
        hBox2.getChildren().add(bounceDefinition2);
        hBox3.getChildren().add(bounceDefinition3);

*/

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       
        // InitLabels
        DatabaseHandler db = new DatabaseHandler();
        ArrayList<String> statements = new SQLCreator().initializeTotals();
        for (String each : statements) {
            System.out.println(each);
        }
        try {
            ResultSet result = db.querySQL(statements.get(0));
            System.out.println(result.getMetaData());
            result.first();
            int imps = result.getInt(1);
            System.out.println(result.getString(1));
            noImprLabel.setText(result.getString(1));

            result = db.querySQL(statements.get(1));
            result.first();
            int clicks = result .getInt(1);
            System.out.println(result.getString(1));
            noClicksLabel.setText(result.getString(1));

            result = db.querySQL(statements.get(2));
            result.first();
            System.out.println(result.getString(1));
            noUniqueLabel.setText(result.getString(1));

            result = db.querySQL(statements.get(3));
            result.first();
            float bounces = result.getInt(1);
            System.out.println(result.getString(1));
            noBounceLabel.setText(result.getString(1));

            result = db.querySQL(statements.get(4));
            result.first();
            int convs = result.getInt(1);
            System.out.println(result.getString(1));
            noConversionLabel.setText(result.getString(1));

            result = db.querySQL(statements.get(5));
            result.first();
            float impCost = result.getFloat(1);
            result = db.querySQL(statements.get(6));
            result.first();
            float totalCost = impCost + result.getFloat(1);
            double total = Math.round(totalCost * 100.0) / 100.0;
            System.out.println(totalCost);
            System.out.println(total);
            totalCostLabel.setText(Double.toString(total));

            if (imps == 0) {
                ctrLabel.setText("0");
            } else {
                float ctr = (float) clicks / imps;
                double ctrRound = Math.round(ctr * 100.0) / 100.0;
                System.out.println(ctr);
                System.out.println(ctrRound);
                ctrLabel.setText(Double.toString(ctrRound));
            }

            if (convs == 0) {
                cpaLabel.setText("0");
            } else {
                float cpa = totalCost / convs;
                double cpaRound = Math.round(cpa * 100.0) / 100.0;
                System.out.println(cpa);
                System.out.println(cpaRound);
                cpaLabel.setText(Double.toString(cpaRound));
            }

            if (clicks == 0) {
                cpcLabel.setText("0");
            } else {
                float cpc = totalCost / clicks;
                double cpcRound = Math.round(cpc * 100.0) / 100.0;
                System.out.println(cpc);
                System.out.println(cpcRound);
                cpcLabel.setText(Double.toString(cpcRound));
            }

            if (imps == 0) {
                cpmLabel.setText("0");
            } else {
                float cpm = totalCost / (imps * 1000);
                double cpmRound = Math.round(cpm * 100.0) / 100.0;
                System.out.println(cpm);
                System.out.println(cpmRound);
                cpmLabel.setText(Double.toString(cpmRound));
            }

            if (clicks == 0) {
                bounceRateLabel.setText("0");
            } else {
                float br = bounces / clicks;
                double brRound = Math.round(br * 100.0) / 100.0;
                System.out.println(br);
                System.out.println(brRound);
                bounceRateLabel.setText(Double.toString(brRound));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // InitCharts
       
        XYChart.Series series = new XYChart.Series();
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series31 = new XYChart.Series();

        series.getData().add(new XYChart.Data("1", 23));
        series.getData().add(new XYChart.Data("2", 48));
        series.getData().add(new XYChart.Data("3", 70));
        series.getData().add(new XYChart.Data("4", 90));
        series.getData().add(new XYChart.Data("5", 123));
        series.getData().add(new XYChart.Data("6", 245));
        series.getData().add(new XYChart.Data("7", 109));

        series1.getData().add(new XYChart.Data("1", 12));
        series1.getData().add(new XYChart.Data("2", 30));
        series1.getData().add(new XYChart.Data("3", 45));
        series1.getData().add(new XYChart.Data("4", 13));
        series1.getData().add(new XYChart.Data("5", 70));
        series1.getData().add(new XYChart.Data("6", 25));
        series1.getData().add(new XYChart.Data("7", 109));

        series2.getData().add(new XYChart.Data("1", 45));
        series2.getData().add(new XYChart.Data("2", 30));
        series2.getData().add(new XYChart.Data("3", 89));
        series2.getData().add(new XYChart.Data("4", 20));
        series2.getData().add(new XYChart.Data("5", 7));
        series2.getData().add(new XYChart.Data("6", 25));
        series2.getData().add(new XYChart.Data("7", 230));

        series3.getData().add(new XYChart.Data("1", 90));
        series3.getData().add(new XYChart.Data("2", 34));
        series3.getData().add(new XYChart.Data("3", 89));
        series3.getData().add(new XYChart.Data("4", 105));
        series3.getData().add(new XYChart.Data("5", 279));
        series3.getData().add(new XYChart.Data("6", 300));
        series3.getData().add(new XYChart.Data("7", 299));

        series31.getData().add(new XYChart.Data("1", 400));
        series31.getData().add(new XYChart.Data("2", 398));
        series31.getData().add(new XYChart.Data("3", 321));
        series31.getData().add(new XYChart.Data("4", 345));
        series31.getData().add(new XYChart.Data("5", 311));
        series31.getData().add(new XYChart.Data("6", 280));
        series31.getData().add(new XYChart.Data("7", 299));

        System.out.println("Hello Dil");
        NoImpressionsChart.getData().addAll(series);
        NoImpressionsChart1.getData().addAll(series1);
        NoImpressionsChart2.getData().addAll(series2);
        NoImpressionsChart3.getData().addAll(series3);
        NoImpressionsChart31.getData().addAll(series31);


    }
}