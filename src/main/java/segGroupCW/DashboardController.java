package segGroupCW;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

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

    @FXML
    private VBox leftVBox;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLabels();
//    DONT DELETE ^

        leftVBox.getChildren().addAll(GlyphsDude.createIcon(FontAwesomeIcons.COG,"40px"));


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
        initLabels();
        initGraphs();

    }

    /**
     * Getting selected filter checkboxes
     * @param event
     */
    @FXML
    void applyFilter(ActionEvent event) {
        List<String> selectedCheckBoxes = new ArrayList<>();
        for (JFXCheckBox checkBox : checkBoxList){
            if (checkBox.isSelected()) {
                selectedCheckBoxes.add(checkBox.getText());
               // System.out.println(checkBox.getText());
            }
        }
        if (!selectedCheckBoxes.isEmpty()) {
            try {
                setImpsLabel(db, sqlCreator, selectedCheckBoxes);
                setClicksLabel(db, sqlCreator, selectedCheckBoxes);
                setUniquesLabel(db, sqlCreator, selectedCheckBoxes);
                setNoBounceLabelPages(db, sqlCreator, "2", selectedCheckBoxes);
                setNoConvLabel(db, sqlCreator, selectedCheckBoxes);
                setTotalCostLabel(db, sqlCreator, selectedCheckBoxes);
                setCtrLabel(db, sqlCreator, selectedCheckBoxes);
                setCpaLabel(db, sqlCreator, selectedCheckBoxes);
                setCpcLabel(db, sqlCreator, selectedCheckBoxes);
                setCpmLabel(db, sqlCreator, selectedCheckBoxes);
                setBounceRateLabelPages(db, sqlCreator, "2", selectedCheckBoxes);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            initLabels();
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
        initLabels();
    }


    /**
     * Takes user to settings page
     * @throws IOException
     */
    @FXML
    void settingsPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Scene settingsScene = new Scene(loader.load());
        Stage settingsStage = new Stage();
        settingsScene.getStylesheets().add(App.themeController.getCurrentThemeUrl());
        settingsStage.setScene(settingsScene);
        settingsStage.show();
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

        JFXSlider definition1Slider = new JFXSlider(0, 360, 60);

        JFXSlider definition2Slider = new JFXSlider(0, 10, 2);

        bounceDefinition1.setToggleGroup(bounceToggle);
        bounceDefinition2.setToggleGroup(bounceToggle);
        bounceDefinition3.setToggleGroup(bounceToggle);

        bounceDefinition1.setSelected(true);

        box.getChildren().add(bounceDefinition1);
        box.getChildren().add(bounceDefinition2);
        box.getChildren().add(bounceDefinition3);

        pane1.getChildren().add(bounceDefinition1);
        pane1.getChildren().add(definition1Slider);
        pane2.getChildren().add(bounceDefinition2);
        pane2.getChildren().add(definition2Slider);
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

    private DatabaseHandler db = new DatabaseHandler();
    private SQLCreator sqlCreator = new SQLCreator();

    /**
     * return array - 0: start impressinos, 1: end Imp, 2: start clicks, 3: end clicks
     * @return
     */
    private String[] getFirstLastDates(){
        String[] dates = new String[10];
        try {
            ResultSet rs = db.querySQL(sqlCreator.FirstLastDate("impressions"));
            rs.first();
            dates[0] = rs.getString(1).split(" ")[0];
            dates[1] = rs.getString(2).split(" ")[0];

            rs = db.querySQL(sqlCreator.FirstLastDate("clicks"));
            rs.first();
            dates[2] = rs.getString(1).split(" ")[0];
            dates[3] = rs.getString(2).split(" ")[0];

            rs = db.querySQL(sqlCreator.FirstLastDate("server"));
            rs.first();
            dates[4] = rs.getString(1).split(" ")[0];
            dates[5] = rs.getString(2).split(" ")[0];
            dates[6] = rs.getString(3).split(" ")[0];
            dates[7] = rs.getString(4).split(" ")[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    private void initGraphs(){
        String[] dates = getFirstLastDates();
        refreshImpressionsGraph(dates[0], dates[1]);
        refreshClicksGraph(dates[2], dates[3]);
        refreshUniquesGraph(dates[2],dates[3]);
        refreshConvsGraph(dates[4], dates[5]);
        refreshTotalCostGraph(dates[0],dates[1]);
        refreshCTRGraph(dates[2],dates[3]);
        refreshCPAGraph(dates[0],dates[1]);
        refreshCPCGraph(dates[0],dates[1]);
        refreshCPMGraph(dates[0],dates[1]);
        //refreshBRPageGraph(dates[2],dates[3]);

    }

    private void initLabels() {
        try {
            setImpsLabel(db, sqlCreator);
            setClicksLabel(db, sqlCreator);
            setUniquesLabel(db, sqlCreator);
            setNoBounceLabelPages(db, sqlCreator, "2");
            setNoConvLabel(db, sqlCreator);
            setTotalCostLabel(db, sqlCreator);
            setCtrLabel(db, sqlCreator);
            setCpaLabel(db, sqlCreator);
            setCpcLabel(db, sqlCreator);
            setCpmLabel(db, sqlCreator);
            setBounceRateLabelPages(db, sqlCreator, "2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setImpsLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfImps());
        result.first();
        noImprLabel.setText(result.getString(1));
    }

    public void setClicksLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfClicks());
        result.first();
        noClicksLabel.setText(result.getString(1));
    }

    public void setUniquesLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfUniques());
        result.first();
        noUniqueLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelPages(DatabaseHandler db, SQLCreator sql, String pages) throws SQLException {
        ResultSet result = db.querySQL(sql.bounceByPages(pages));
        result.first();
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelTime(DatabaseHandler db, SQLCreator sql, String unit, String time) throws SQLException {
        ResultSet result = db.querySQL(sql.bounceByTime(unit, time));
        result.first();
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelConv(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.bounceByConv());
        result.first();
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoConvLabel(DatabaseHandler db, SQLCreator sql) throws SQLException {
        ResultSet result = db.querySQL(sql.numOfConvs());
        result.first();
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
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setImpsLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ResultSet result = db.querySQL(sql.filterImps(filters));
        result.first();
        noImprLabel.setText(result.getString(1));
    }

    public void setClicksLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ResultSet result = db.querySQL(sql.filterClicks(filters));
        result.first();
        noClicksLabel.setText(result.getString(1));
    }

    public void setUniquesLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ResultSet result = db.querySQL(sql.filterUniques(filters));
        result.first();
        noUniqueLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelPages(DatabaseHandler db, SQLCreator sql, String pages, List<String> filters) throws SQLException {
        ResultSet result = db.querySQL(sql.filterBouncePages(filters, pages));
        result.first();
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelTime(DatabaseHandler db, SQLCreator sql, String unit, String time, List<String> filters) throws SQLException {
        ResultSet result = db.querySQL(sql.filterBounceTime(filters, unit, time));
        result.first();
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoBounceLabelConv(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ResultSet result = db.querySQL(sql.filterBounceConv(filters));
        result.first();
        noBounceLabel.setText(result.getString(1));
    }

    public void setNoConvLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ResultSet result = db.querySQL(sql.filterConvs(filters));
        result.first();
        noConversionLabel.setText(result.getString(1));
    }

    public void setTotalCostLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ArrayList<String> statements = sql.filterTotalCost(filters);
        ResultSet result = db.querySQL(statements.get(0));
        result.first();
        float impCost = result.getFloat(1);
        result = db.querySQL(statements.get(1));
        result.first();
        float totalCost = impCost + result.getFloat(1);
        double total = Math.round(totalCost * 100.0) / 100.0;
        totalCostLabel.setText(Double.toString(total));
    }

    public void setCtrLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ArrayList<String> statements = sql.filterCTR(filters);
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
            ctrLabel.setText(Double.toString(ctrRound));
        }
    }

    public void setCpaLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ArrayList<String> statements = sql.filterCPA(filters);
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
            cpaLabel.setText(Double.toString(cpaRound));
        }
    }

    public void setCpcLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ArrayList<String> statements = sql.filterCPC(filters);
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
            cpcLabel.setText(Double.toString(cpcRound));
        }
    }

    public void setCpmLabel(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ArrayList<String> statements = sql.filterCPM(filters);
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
            cpmLabel.setText(Double.toString(cpmRound));
        }
    }

    public void setBounceRateLabelPages(DatabaseHandler db, SQLCreator sql, String pages, List<String> filters) throws SQLException {
        ArrayList<String> statements = sql.filterBounceRatePage(filters, pages);
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
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelTime(DatabaseHandler db, SQLCreator sql, String unit, String time, List<String> filters) throws SQLException{
        ArrayList<String> statements = sql.filterBounceRateTime(filters, unit, time);
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
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelConv(DatabaseHandler db, SQLCreator sql, List<String> filters) throws SQLException {
        ArrayList<String> statements = sql.filterBounceRateConv(filters);
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
     * @param interval  the string indicating the measurement of intervals
     * @param num    the number of the given measurement, of which we should jump per interval
     * @return
     */

//int for interval num and string for day/month
    private static ArrayList<String> iterTimeIntervals(String start, String end, String interval, int num){
        ArrayList<String> intervals = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end).plusDays(1);
        intervals.add(startDate.toString());

        switch  (interval){
            case "days":
                while (startDate.isBefore(endDate)){
                    startDate = startDate.plusDays(num);
                    intervals.add(startDate.toString());
                }
                break;
            case "months":
                while (startDate.isBefore(endDate)){
                    startDate = startDate.plusMonths(num);
                    intervals.add(startDate.toString());
                }
                break;
            case "weeks":
                while (startDate.isBefore(endDate)){
                    startDate = startDate.plusWeeks(num);
                    intervals.add(startDate.toString());
                }
                break;
        }
        return intervals;
    }

    private void refreshImpressionsGraph(String start, String end){
        NoImpressionsChart.getData().add(getSeries(start, end, "days", 1, 0));
    }

    private void refreshClicksGraph(String start, String end){
        noOfClicksChart.getData().add(getSeries(start, end ,"days",1,1));
    }

    private void refreshUniquesGraph(String start, String end){
        NoUniquesChart.getData().add(getSeries(start, end ,"days",1,2));
    }

    /*
    private void refreshBouncePagesGraph(String start, String end){
        NoUniquesChart.getData().add(getSeries(start, end ,"days",1,));
    }
     */

    private void refreshConvsGraph(String start, String end){
        NoConversionsChart.getData().add(getSeries(start, end ,"days",1,3));
    }

    private void refreshTotalCostGraph(String start, String end){
        totalCostChart.getData().add(getSeries(start, end ,"days",1,4));
    }

    private void refreshCTRGraph(String start, String end){
        ctrChart.getData().add(getSeries(start, end ,"days",1,5));
    }

    private void refreshCPAGraph(String start, String end){
        cpaChart.getData().add(getSeries(start, end ,"days",1,6));
    }

    private void refreshCPCGraph(String start, String end){
        cpcChart.getData().add(getSeries(start, end ,"days",1,7));
    }

    private void refreshCPMGraph(String start, String end){
        cpmChart.getData().add(getSeries(start, end ,"days",1,8));
    }

    /*
    private void refreshBRPageGraph(String start, String end){
        bounceRateChart.getData().add(getBouncePageSeries(start, end ,"days", "2", 1));
    }
*/

    /**
     *
     * @param start
     * @param end
     * @param interval
     * @param num
     * @param graphNum
     * @return
     */
    private XYChart.Series getSeries(String start, String end, String interval, int num, int graphNum){
        ArrayList<String> dates = iterTimeIntervals(start, end, interval, num);
        XYChart.Series series = new XYChart.Series();
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;

        for(int i = 0; i < dates.size();i++){
            try {
                switch (graphNum) {
                    case 0:
                        rs = db.querySQL(sqlCreator.betweenDateImps(dates.get(i), dates.get(i + 1)));
                        break;
                    case 1:
                        rs = db.querySQL(sqlCreator.betweenDateClicks(dates.get(i), dates.get(i + 1)));
                        break;
                    case 2:
                        rs = db.querySQL(sqlCreator.betweenDateUniques(dates.get(i), dates.get(i + 1)));
                        break;
                    case 3:
                        rs = db.querySQL(sqlCreator.betweenDateConvs(dates.get(i), dates.get(i + 1)));
                        break;
                    case 4:
                        rs = db.querySQL(sqlCreator.betweenDateTotalCost(dates.get(i), dates.get(i + 1)).get(0));
                        rs2 = db.tempQuery1(sqlCreator.betweenDateTotalCost(dates.get(i), dates.get(i + 1)).get(1));
                        break;
                    case 5:
                        rs = db.querySQL(sqlCreator.betweenDateCTR(dates.get(i), dates.get(i + 1)).get(0));
                        rs2 = db.tempQuery1(sqlCreator.betweenDateCTR(dates.get(i), dates.get(i + 1)).get(1));
                        break;
                    case 6:
                        rs = db.querySQL(sqlCreator.betweenDateCPA(dates.get(i), dates.get(i + 1)).get(0));
                        rs2 = db.tempQuery1(sqlCreator.betweenDateCPA(dates.get(i), dates.get(i + 1)).get(1));
                        rs3 = db.tempQuery2(sqlCreator.betweenDateCPA(dates.get(i), dates.get(i + 1)).get(2));
                        break;
                    case 7:
                        rs = db.querySQL(sqlCreator.betweenDateCPC(dates.get(i), dates.get(i + 1)).get(0));
                        rs2 = db.tempQuery1(sqlCreator.betweenDateCPC(dates.get(i), dates.get(i + 1)).get(1));
                        rs3 = db.tempQuery2(sqlCreator.betweenDateCPC(dates.get(i), dates.get(i + 1)).get(2));
                        break;
                    case 8:
                        rs = db.querySQL(sqlCreator.betweenDateCPM(dates.get(i), dates.get(i + 1)).get(0));
                        rs2 = db.tempQuery1(sqlCreator.betweenDateCPM(dates.get(i), dates.get(i + 1)).get(1));
                        break;

                }
                if(graphNum == 0 || graphNum == 1 || graphNum == 2 || graphNum == 3) {
                    while (rs.next()) {
                        rs.first();
                        series.getData().add(new XYChart.Data(dates.get(i), rs.getInt(1)));
                    }
                }
                else if (graphNum == 4){
                    while(rs.next() && rs2.next()){
                        rs.first();
                        series.getData().add(new XYChart.Data(dates.get(i), rs.getInt(1) + rs2.getInt(1)));
                    }
                }
                else if (graphNum == 5 || graphNum == 8){
                    while(rs.next() && rs2.next()){
                        rs.first();
                        if(rs2.getInt(1) == 0){
                            series.getData().add(new XYChart.Data(dates.get(i), 0));
                        }else {
                            series.getData().add(new XYChart.Data(dates.get(i), (float) (rs.getInt(1) / rs2.getInt(1))));
                        }
                    }
                }
                else if (graphNum == 6 || graphNum == 7){
                    while(rs.next() && rs2.next() && rs3.next()){
                        rs.first();
                        if(rs3.getInt(1) == 0){
                            series.getData().add(new XYChart.Data(dates.get(i), 0));
                        }else {
                            series.getData().add(new XYChart.Data(dates.get(i), (float) ((rs.getInt(1) + rs2.getInt(1)) / rs3.getInt(1))));
                        }
                    }
                }
                else if (graphNum == 8){
                    while(rs.next() && rs2.next() && rs3.next()){
                        rs.first();
                        if(rs3.getInt(1) == 0){
                            series.getData().add(new XYChart.Data(dates.get(i), 0));
                        }else {
                            series.getData().add(new XYChart.Data(dates.get(i), (float) ((rs.getInt(1) + rs2.getInt(1)) / rs3.getInt(1)) * 1000));
                        }
                    }
                }

            }
            catch (SQLException e) { e.printStackTrace(); }
            catch (IndexOutOfBoundsException e){}
            catch (NullPointerException e){e.printStackTrace();}
        }
        return series;
    }

    /**
     * 
     * @param start
     * @param end
     * @param intervalTime
     * @param intervalPage
     * @param num
     * @return
     */
    /*
    private XYChart.Series getBouncePageSeries(String start, String end, String intervalTime, String intervalPage, int num){
        ArrayList<String> dates = iterTimeIntervals(start, end, intervalTime, num);
        XYChart.Series series = new XYChart.Series();
        ResultSet rs = null;
        ResultSet rs2 = null;

        for(int i = 0; i < dates.size();i++) {
            try {
                rs = db.querySQL(sqlCreator.betweenDateBRPage(dates.get(i), dates.get(i + 1), intervalPage).get(0));
                rs2 = db.querySQL(sqlCreator.betweenDateBRPage(dates.get(i), dates.get(i + 1), intervalPage).get(1));

                while(rs.next() && rs2.next()){
                    if(rs2.getInt(1) == 0){
                        series.getData().add(new XYChart.Data(dates.get(i), 0));
                    }else {
                        series.getData().add(new XYChart.Data(dates.get(i), (float) (rs.getInt(1) / rs2.getInt(1))));
                    }
                }
            }
            catch (SQLException e) { e.printStackTrace(); }
            catch (IndexOutOfBoundsException e){}
            catch (NullPointerException e){e.printStackTrace();}
        }
        return series;
    }
*/
}