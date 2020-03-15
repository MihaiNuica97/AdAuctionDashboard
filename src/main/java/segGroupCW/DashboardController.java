package segGroupCW;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.h2.store.DataHandler;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label dashBoardLabel;

    @FXML
    private JFXButton campaign1Button;

    @FXML
    private JFXButton campaign2Button;

    @FXML
    private JFXButton changeCampaignButton;

    @FXML
    private Label noImprLabel;

    @FXML
    private Label noClicksLabel;

    @FXML
    private Label noUniqueLabel;

    @FXML
    private Label noBounceLabel;

    @FXML
    private JFXButton bounceDefinitionButton;

    @FXML
    private Label noConversionLabel;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Label ctrLabel;

    @FXML
    private Label cpaLabel;

    @FXML
    private Label cpcLabel;

    @FXML
    private Label cpmLabel;

    @FXML
    private Label bounceRateLabel;

    @FXML
    private Pane noImprPane;

    @FXML
    private LineChart<?, ?> NoImpressionsChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Pane noClicksPane;

    @FXML
    private LineChart<?, ?> noOfClicksChart;

    @FXML
    private CategoryAxis x1;

    @FXML
    private NumberAxis y1;

    @FXML
    private Pane noOfUniques;

    @FXML
    private LineChart<?, ?> NoUniquesChart;

    @FXML
    private CategoryAxis x2;

    @FXML
    private NumberAxis y2;

    @FXML
    private Pane noBouncePane;

    @FXML
    private LineChart<?, ?> NoBouncesChart;

    @FXML
    private CategoryAxis x3;

    @FXML
    private NumberAxis y3;

    @FXML
    private Pane noOfConversionsPane;

    @FXML
    private LineChart<?, ?> NoConversionsChart;

    @FXML
    private CategoryAxis x31;

    @FXML
    private NumberAxis y31;

    @FXML
    private Pane totalCostPane;

    @FXML
    private LineChart<?, ?> totalCostChart;

    @FXML
    private JFXRadioButton LineChartRadioGraph;

    @FXML
    private ToggleGroup group1;

    @FXML
    private JFXRadioButton pieChartRadioButton;

    @FXML
    private JFXRadioButton histogramRadioButton;

    @FXML
    private Pane ctrPane;

    @FXML
    private LineChart<?, ?> ctrChart;

    @FXML
    private Pane cpaPane;

    @FXML
    private LineChart<?, ?> cpaChart;

    @FXML
    private Pane cpcPane;

    @FXML
    private LineChart<?, ?> cpcChart;

    @FXML
    private Pane cpmPane;

    @FXML
    private LineChart<?, ?> cpmChart;

    @FXML
    private Pane bounceRatePane;

    @FXML
    private LineChart<?, ?> bounceRateChart;

    private PieChart pieChart;

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> histogram = new BarChart<>(xAxis,yAxis);


    /**
     * Change total cost chart to pie chart
     */
    @FXML
    void pieChartChange() {
        if (totalCostPane.getChildren() == totalCostChart){
            totalCostPane.getChildren().remove(totalCostChart);
        }

        else{
            totalCostPane.getChildren().remove(histogram);
        }

        totalCostPane.getChildren().remove(totalCostChart);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Click Cost", 13),
                        new PieChart.Data("Impression Cost", 25));
        pieChart = new PieChart(pieChartData);
        totalCostPane.getChildren().add(pieChart);

        //pieChart.autosize();
        pieChart.setMaxHeight(50);
    }

    /**
     * Change total cost chart to histogram
     * @param event
     */
    @FXML
    void histogramChange(ActionEvent event) {
        if (totalCostPane.getChildren() == pieChart){
            totalCostPane.getChildren().remove(pieChart);
        }

        else{
            totalCostPane.getChildren().remove(totalCostChart);
        }


        totalCostPane.getChildren().remove(totalCostChart);
        histogram.setCategoryGap(0);
        histogram.setBarGap(0);

        xAxis.setLabel("Time");
        yAxis.setLabel("Total Cost");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Histogram");
        //series1.getData().add(new XYChart.Data("0-10", group[0]));
        histogram.getData().addAll(series1);

        totalCostPane.getChildren().add(histogram);

        histogram.setMaxHeight(50);
    }

    /**
     * Change total cost chart to line chart
     */
    void lineChartChange() {
        if (totalCostPane.getChildren() == pieChart){
            totalCostPane.getChildren().remove(pieChart);
        }

        else{
            totalCostPane.getChildren().remove(histogram);
        }
        totalCostPane.getChildren().add(totalCostChart);

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

    /**
     * Brings you to main Input Page
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("fileUpload");
        App.getScene().getWindow().setHeight(500);
        App.getScene().getWindow().setWidth(560);

    }

    /**
     * Functionality for changing bounce change
     * @param event
     */
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

    }

    /**
     * Populates the labels with data from the database
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//    initLabels();
//    DONT DELETE ^

    }

    /**
     * Brings you to the graph page by clicking on pane
     * @param event
     * @throws IOException
     */
    @FXML
    void graphViewImpressions(MouseEvent event) throws IOException {
        App.setRoot("graphView");

    }

    /**
     * Brings you to the dashboard page
     * @param event
     * @throws IOException
     */
    @FXML
    void dashboardReturn(ActionEvent event) throws IOException {
        App.setRoot("dashboard");

    }

    private void initLabels() {
        try {
            DatabaseHandler db = new DatabaseHandler();
            SQLCreator sql = new SQLCreator();
            setImpsLabel(db, sql);
            setClicksLabel(db, sql);
            setUniquesLabel(db, sql);
            setNoBounceLabelPages(db, sql, "2");
            setNoConvLabel(db, sql);
            setTotalCostLabel(db, sql);
            setCtrLabel(db, sql);
            setCpaLabel(db, sql);
            setCpcLabel(db, sql);
            setCpmLabel(db, sql);
            setBounceRateLabelPages(db, sql, "2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setImpsLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfImps());
        result.first();
        System.out.println(result.getString(1));
        noImprLabel.setText(result.getString(1));
    }

    public void setClicksLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfClicks());
        result.first();
        System.out.println(result.getString(1));
        noClicksLabel.setText(result.getString(1));
    }

    public void setUniquesLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfUniques());
        result.first();
        System.out.println(result.getString(1));
        noUniqueLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelPages(DatabaseHandler db, SQLCreator sql, String pages) throws SQLException {
        ResultSet result = db.querySQL(sql.bounceByPages(pages));
        result.first();
        System.out.println(result.getString(1));
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelTime(DatabaseHandler db, SQLCreator sql, String unit, String time) throws SQLException {
        ResultSet result = db.querySQL(sql.bounceByTime(unit, time));
        result.first();
        System.out.println(result.getString(1));
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelConv(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.bounceByConv());
        result.first();
        System.out.println(result.getString(1));
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoConvLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfConvs());
        result.first();
        System.out.println(result.getString(1));
        noConversionLabel.setText(result.getString(1));
    }

    public void setTotalCostLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ArrayList<String> statements = sql.totalCost();
        ResultSet result = db.querySQL(statements.get(0));
        result.first();
        float impCost = result.getFloat(1);
        result = db.querySQL(statements.get(1));
        result.first();
        float totalCost = impCost + result.getFloat(1);
        double total = Math.round(totalCost * 100.0) / 100.0;
        System.out.println(total);
        totalCostLabel.setText(Double.toString(total));
    }

    public void setCtrLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ArrayList<String> statements = sql.ctr();
        ResultSet result = db.querySQL(statements.get(1));
        result.first();
        int imps = result.getInt(1);
        if (imps == 0) {
            ctrLabel.setText("N/A");
        } else {
            result = db.querySQL(statements.get(0));
            result.first();
            int clicks = result.getInt(1);
            float ctr = (float) clicks / imps;
            double ctrRound = Math.round(ctr * 100.0) / 100.0;
            System.out.println(ctrRound);
            ctrLabel.setText(Double.toString(ctrRound));
        }
    }

    public void setCpaLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ArrayList<String> statements = sql.cpa();
        ResultSet result = db.querySQL(statements.get(2));
        result.first();
        int convs = result.getInt(1);
        if (convs == 0) {
            cpaLabel.setText("N/A");
        } else {
            result = db.querySQL(statements.get(0));
            result.first();
            float impCost = result.getFloat(1);
            result = db.querySQL(statements.get(1));
            result.first();
            float totalCost = impCost + result.getFloat(1);
            float cpa = totalCost / convs;
            double cpaRound = Math.round(cpa * 100.0) / 100.0;
            System.out.println(cpa);
            System.out.println(cpaRound);
            cpaLabel.setText(Double.toString(cpaRound));
        }
    }

    public void setCpcLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ArrayList<String> statements = sql.cpc();
        ResultSet result = db.querySQL(statements.get(2));
        result.first();
        int clicks = result.getInt(1);
        if (clicks == 0) {
            cpcLabel.setText("N/A");
        } else {
            result = db.querySQL(statements.get(0));
            result.first();
            float impCost = result.getFloat(1);
            result = db.querySQL(statements.get(1));
            result.first();
            float totalCost = impCost + result.getFloat(1);
            float cpc = totalCost / clicks;
            double cpcRound = Math.round(cpc * 100.0) / 100.0;
            System.out.println(cpcRound);
            cpcLabel.setText(Double.toString(cpcRound));
        }
    }

    public void setCpmLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ArrayList<String> statements = sql.cpm();
        ResultSet result = db.querySQL(statements.get(2));
        result.first();
        int imps = result.getInt(1);
        if (imps == 0) {
            cpmLabel.setText("N/A");
        } else {
            result = db.querySQL(statements.get(0));
            result.first();
            float impCost = result.getFloat(1);
            result = db.querySQL(statements.get(1));
            result.first();
            float totalCost = impCost + result.getFloat(1);
            float cpm = totalCost / (imps * 1000);
            double cpmRound = Math.round(cpm * 100.0) / 100.0;
            System.out.println(cpm);
            System.out.println(cpmRound);
            cpmLabel.setText(Double.toString(cpmRound));
        }
    }

    public void setBounceRateLabelPages(DatabaseHandler db, SQLCreator sql, String pages) throws SQLException {
        ArrayList<String> statements = sql.bounceRatePage(pages);
        ResultSet result = db.querySQL(statements.get(1));
        result.first();
        int clicks = result.getInt(1);
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            result = db.querySQL(statements.get(0));
            result.first();
            int bounces = result.getInt(1);
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            System.out.println(brRound);
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelTime(DatabaseHandler db, SQLCreator sql, String unit, String time) throws SQLException{
        ArrayList<String> statements = sql.bounceRateTime(unit, time);
        ResultSet result = db.querySQL(statements.get(1));
        result.first();
        int clicks = result.getInt(1);
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            result = db.querySQL(statements.get(0));
            result.first();
            int bounces = result.getInt(1);
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            System.out.println(brRound);
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelConv(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ArrayList<String> statements = sql.bounceRateConv();
        ResultSet result = db.querySQL(statements.get(1));
        result.first();
        int clicks = result.getInt(1);
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            result = db.querySQL(statements.get(0));
            result.first();
            int bounces = result.getInt(1);
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            System.out.println(brRound);
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    /*
        * create function that gets first and last dates of tables
        * recieves time interval and range and creates a list of sql statements, retruns a list of values
        * turn values intop chart elemetns
     */

    /**
     *
     * @param start  start date of data required - yyyy-MM-dd
     * @param end    end date of data required - yyyy-MM-dd
     * @param interval  the array list containing the list of intervals
     * @return
     */

    public static ArrayList<String> iterTimeIntervals(String start, String end, int interval){
        ArrayList<String> intervals = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        intervals.add(startDate.toString());

        while (startDate.isBefore(endDate)){
            startDate = startDate.plusDays(interval);
            intervals.add(startDate.toString());
        }
        return intervals;
    }

}