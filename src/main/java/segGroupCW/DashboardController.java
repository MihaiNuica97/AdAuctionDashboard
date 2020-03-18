package segGroupCW;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private JFXButton changeCampaignButton;   //do we want this?

    @FXML
    private Label noImprLabel, noClicksLabel, noUniqueLabel, noBounceLabel, noConversionLabel, totalCostLabel, ctrLabel, cpaLabel, cpcLabel, cpmLabel, bounceRateLabel;

    @FXML
    private JFXButton bounceDefinitionButton;

    @FXML
    private Pane noImprPane, noClicksPane, noOfUniques, noBouncePane, noOfConversionsPane, totalCostPane, ctrPane, cpaPane;

    @FXML
    private LineChart<?, ?> NoImpressionsChart, noOfClicksChart, NoUniquesChart, NoBouncesChart, NoConversionsChart, totalCostChart, ctrChart, cpaChart;

    @FXML
    private Pane cpcPane, cpmPane, bounceRatePane;

    @FXML
    private LineChart<?, ?> cpcChart, cpmChart, bounceRateChart;

    @FXML
    private JFXRadioButton LineChartRadioGraph, pieChartRadioButton, histogramRadioButton;

    List<JFXCheckBox> checkBoxList;

    @FXML
    private JFXCheckBox femaleCheckBox, maleCheckbox, age1Checkbox, age2Checkbox, age3Checkbox, age4Checkbox, age5Checkbox, lowIncomeCheckbox, MediumIncomeCheckbox;

    @FXML
    private JFXCheckBox highIncomeCheckbox, shoppingCheckbox, newsCheckbox, blogCheckbox, socialMCheckbox, hobbiesCheckbox, travelCheckbox;


    @FXML
    private JFXButton applyFilterButton, clearFilterButton;


    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//    initLabels();
//    DONT DELETE ^

        checkBoxList = new ArrayList<JFXCheckBox>();
        checkBoxList.add(femaleCheckBox);
        checkBoxList.add(maleCheckbox);
        checkBoxList.add(age1Checkbox);
        checkBoxList.add(age2Checkbox);
        checkBoxList.add(age3Checkbox);
        checkBoxList.add(age4Checkbox);
        checkBoxList.add(age5Checkbox);
        checkBoxList.add(lowIncomeCheckbox);
        checkBoxList.add(MediumIncomeCheckbox);
        checkBoxList.add(highIncomeCheckbox);
        checkBoxList.add(shoppingCheckbox);
        checkBoxList.add(newsCheckbox);
        checkBoxList.add(blogCheckbox);
        checkBoxList.add(socialMCheckbox);
        checkBoxList.add(hobbiesCheckbox);
        checkBoxList.add(travelCheckbox);

    }

    /**
     * Getting selected filter checkboxes
     * @param event
     */
    @FXML
    void applyFilter(ActionEvent event) {
        List<String> selectedCheckBoxes = new ArrayList<String>();
        for (JFXCheckBox checkBox : checkBoxList){
            if (checkBox.isSelected()) {
                selectedCheckBoxes.add(checkBox.getText());
               // System.out.println(checkBox.getText());
            }
        }
    }

    /**
     * Clearing the chosen filters
     * @param event
     */
    @FXML
    void clearFilter(ActionEvent event) {
        for (JFXCheckBox checkBox : checkBoxList){
            if (checkBox.isSelected()) {
                checkBox.setSelected(false);
               // System.out.println(checkBox.getText() + " is cleared" );
            }
        }
    }


    /**
     * Takes user to settings page
     * @param event
     * @throws IOException
     */
    @FXML
    void settingsPage(ActionEvent event) throws IOException {
        App.setRoot("settings");
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
     * Brings you to the Impressions graph page by clicking on pane
     * @param event
     * @throws IOException
     */
    @FXML
    void graphViewImpressions(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }


    /**
     * Brings you to the Bounce Rate graph page by clicking on pane
     * @param event
     * @throws IOException
     */
    @FXML
    void graphViewBounceRate(MouseEvent event) throws IOException {
        App.setRoot("graphView");

    }

    @FXML
    void graphViewCPA(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewCPC(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewCPM(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewCTR(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewNoBounces(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewNoClicks(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewNoConversions(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewNoUniques(MouseEvent event) throws IOException {
        App.setRoot("graphView");
    }

    @FXML
    void graphViewTotalCost(MouseEvent event) throws IOException {
        App.setRoot("graphView");
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