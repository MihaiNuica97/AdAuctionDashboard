package segGroupCW;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.*;
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
    private JFXButton bounceDefinitionButton, settingsButton, homeButton;

    @FXML
    private Pane noImprPane, noClicksPane, noOfUniques, noBouncePane, noOfConversionsPane, totalCostPane, ctrPane, cpaPane, topPane;

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

    //Page, Conv, or time
    private String bounceMethod = "Page";
    private int bounceValue = 1;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLabels();
        //initGraphs();
//    DONT DELETE ^

        Text settingsIcon = GlyphsDude.createIcon(FontAwesomeIcons.COG, "40px");
        settingsButton.setGraphic(settingsIcon);

        Text homeIcon = GlyphsDude.createIcon(FontAwesomeIcons.HOME, "40px");
        homeButton.setGraphic(homeIcon);

        Text bounceIcon = GlyphsDude.createIcon(FontAwesomeIcons.PENCIL, "20px");
        bounceDefinitionButton.setGraphic(bounceIcon);

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
        List<String> selectedCheckBoxes = new ArrayList<>();
        for (JFXCheckBox checkBox : checkBoxList){
            if (checkBox.isSelected()) {
                selectedCheckBoxes.add(checkBox.getText());
               // System.out.println(checkBox.getText());
            }
        }
        if (!selectedCheckBoxes.isEmpty()) {
            /*
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

             */
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
     * Brings you to the main Input page
     * @throws IOException
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("fileUpload");
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

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        javafx.geometry.Insets insets = new javafx.geometry.Insets(0,10,10,10);
        box.setPadding(insets);
        Label label = new Label("Choose bounce definition:");
        label.setStyle("-fx-font-size: 2em; -fx-text-fill: #5988FF");
        box.getChildren().add(label);

        HBox pane1 = new HBox();
        pane1.setSpacing(10);
        HBox pane2 = new HBox();
        pane2.setSpacing(10);
        HBox pane3 = new HBox();
        HBox pane4 = new HBox();
        pane4.setAlignment(Pos.CENTER);

        box.getChildren().add(pane1);
        box.getChildren().add(pane2);
        box.getChildren().add(pane3);
        box.getChildren().add(pane4);

        ToggleGroup bounceToggle = new ToggleGroup();

        JFXSlider definition1Slider = new JFXSlider(0, 360, 60);
        definition1Slider.setDisable(true);
        definition1Slider.valueProperty().addListener((obs, oldval, newVal) ->
                definition1Slider.setValue(Math.round(newVal.doubleValue())));

        JFXSlider definition2Slider = new JFXSlider(0, 10, 2);
        definition2Slider.setDisable(true);
        definition2Slider.valueProperty().addListener((obs, oldval, newVal) ->
                definition2Slider.setValue(Math.round(newVal.doubleValue())));
        definition2Slider.setBlockIncrement(1);

        EventHandler<ActionEvent> disableEvent1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                definition1Slider.setDisable(false);
                definition2Slider.setDisable(true);
            }
        };

        EventHandler<ActionEvent> disableEvent2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                definition2Slider.setDisable(false);
                definition1Slider.setDisable(true);
            }
        };

        EventHandler<ActionEvent> disableEvent3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                definition2Slider.setDisable(true);
                definition1Slider.setDisable(true);
            }
        };

        JFXRadioButton bounceDefinition1 = new JFXRadioButton("Entry date & exit date are too close together: ");
        bounceDefinition1.setStyle("-fx-font-size: 15;");
        bounceDefinition1.setOnAction(disableEvent1);
        JFXRadioButton bounceDefinition2 = new JFXRadioButton("Number of pages viewed is below a threshold: ");
        bounceDefinition2.setStyle("-fx-font-size: 15;");
        bounceDefinition2.setOnAction(disableEvent2);
        JFXRadioButton bounceDefinition3 = new JFXRadioButton("Conversion did not take place");
        bounceDefinition3.setStyle("-fx-font-size: 15;");
        bounceDefinition3.setOnAction(disableEvent3);


        JFXButton save = new JFXButton("Save");
        save.setButtonType(JFXButton.ButtonType.RAISED);
        save.setStyle("-fx-background-color: #55CF5A; -fx-text-fill: white; -fx-font-size: 15;" );
        save.setPrefSize(100, 20);
        // action event
        EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {

                if(bounceToggle.getSelectedToggle().equals(bounceDefinition1)){
                    //Entry date & exit date are too close together
                    Double bounce1 = definition1Slider.getValue();
                    System.out.println(bounce1);
                }
                else if(bounceToggle.getSelectedToggle().equals(bounceDefinition2)){
                    //Number of pages viewed is below a threshold
                    Double bounce2 = definition2Slider.getValue();
                    System.out.println(bounce2);
                }
                else{
                    //Conversion didn't take place
                }
                newStage.close();
            }
        };
        save.setOnAction(saveEvent);


        Label seconds = new Label("(Seconds)");
        seconds.setStyle("-fx-font-size: 10;");
        Label pages = new Label("(Pages)");
        pages.setStyle("-fx-font-size: 10;");

        bounceDefinition1.setToggleGroup(bounceToggle);
        bounceDefinition2.setToggleGroup(bounceToggle);
        bounceDefinition3.setToggleGroup(bounceToggle);

        bounceDefinition1.setSelected(true);

        box.getChildren().add(bounceDefinition1);
        box.getChildren().add(bounceDefinition2);
        box.getChildren().add(bounceDefinition3);

        pane1.getChildren().addAll(bounceDefinition1, definition1Slider, seconds );
        pane2.getChildren().addAll(bounceDefinition2, definition2Slider, pages);
        pane3.getChildren().add(bounceDefinition3);
        pane4.getChildren().add(save);


        Scene newScene = new Scene(box, 600, 300);
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


    /**
     * return array - 0: start impressinos, 1: end Imp, 2: start clicks, 3: end clicks
     * @return
     * possibly change to hashmap
     */
    private String[] getFirstLastDates(){
        return null;
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
        //App.dataHandler.applyFilters(null);
        int impressions = App.dataHandler.calcImpressions();
        noImprLabel.setText(Integer.toString(impressions));
        int clicks = App.dataHandler.calcClicks();
        noClicksLabel.setText(Integer.toString(clicks));
        noUniqueLabel.setText(Integer.toString(App.dataHandler.calcUniques()));
        int bounces;
        switch (bounceMethod) {
            case "Page":
                bounces = App.dataHandler.calcBouncePage(bounceValue);
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRatePages(bounces, clicks)));
                break;
            case "Conv":
                bounces = App.dataHandler.calcBounceConv();
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateConv(bounces, clicks)));
                break;
            case "Time":
                bounces = App.dataHandler.calcBounceTime(bounceValue);
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateTime(bounces, clicks)));
            default:
                bounces = 0;
                noBounceLabel.setText("n/a");
        }
        int convs = App.dataHandler.calcConversions();
        noConversionLabel.setText(Integer.toString(convs));
        double totalCost = App.dataHandler.calcTotalCost();
        totalCostLabel.setText(Double.toString(totalCost));
        ctrLabel.setText(Double.toString(App.dataHandler.calcCTR(clicks, impressions)));
        cpaLabel.setText(Double.toString(App.dataHandler.calcCPA(totalCost, convs)));
        cpcLabel.setText(Double.toString(App.dataHandler.calcCPC(totalCost, clicks)));
        cpmLabel.setText(Double.toString(App.dataHandler.calcCPM(totalCost, impressions)));
    }

    public void setImpsLabel() throws SQLException {
        noImprLabel.setText(null);
    }

    public void setClicksLabel() throws SQLException {
        noClicksLabel.setText(null);
    }

    public void setUniquesLabel() throws SQLException {
        noUniqueLabel.setText(null);
    }

    public void setNoBounceLabelPages(String pages) throws SQLException {
        noBounceLabel.setText(null);
    }

    public void setNoBounceLabelTime(String unit, String time) throws SQLException {
        noBounceLabel.setText(null);
    }

    public void setNoBounceLabelConv() throws SQLException {
        noBounceLabel.setText(null);
    }

    public void setNoConvLabel() throws SQLException {
        noConversionLabel.setText(null);
    }

    public void setTotalCostLabel() throws SQLException {
        float impCost = 0;
        float totalCost = impCost + 0;
        double total = Math.round(totalCost * 100.0) / 100.0;
        totalCostLabel.setText(Double.toString(total));
    }

    public void setCtrLabel() throws SQLException {
        int imps = 0;
        if (imps == 0) {
            ctrLabel.setText("N/A");
        } else {
            int clicks = 0;
            float ctr = (float) clicks / imps;
            double ctrRound = Math.round(ctr * 100.0) / 100.0;
            ctrLabel.setText(Double.toString(ctrRound));
        }
    }

    public void setCpaLabel() throws SQLException {
        int convs = 0;
        if (convs == 0) {
            cpaLabel.setText("N/A");
        } else {
            float impCost = 0;
            float totalCost = 0;
            float cpa = totalCost / convs;
            double cpaRound = Math.round(cpa * 100.0) / 100.0;
            cpaLabel.setText(Double.toString(cpaRound));
        }
    }

    public void setCpcLabel() throws SQLException {
        int clicks = 0;
        if (clicks == 0) {
            cpcLabel.setText("N/A");
        } else {
            float impCost = 0;
            float totalCost = 0;
            float cpc = totalCost / clicks;
            double cpcRound = Math.round(cpc * 100.0) / 100.0;
            cpcLabel.setText(Double.toString(cpcRound));
        }
    }

    public void setCpmLabel() throws SQLException {
        int imps = 0;
        if (imps == 0) {
            cpmLabel.setText("N/A");
        } else {
            float impCost = 0;
            float totalCost = 0;
            float cpm = totalCost / (imps * 1000);
            double cpmRound = Math.round(cpm * 100.0) / 100.0;
            cpmLabel.setText(Double.toString(cpmRound));
        }
    }

    public void setBounceRateLabelPages(String pages) throws SQLException {
        int clicks = 0;
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            int bounces = 0;
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelTime(String unit, String time) throws SQLException{
        int clicks = 0;
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            int bounces = 0;
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelConv() throws SQLException {
        int clicks = 0;
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            int bounces = 0;
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setImpsLabel(List<String> filters) throws SQLException {
        noImprLabel.setText(null);
    }

    public void setClicksLabel(List<String> filters) throws SQLException {
        noClicksLabel.setText(null);
    }

    public void setUniquesLabel(List<String> filters) throws SQLException {
        noUniqueLabel.setText(null);
    }

    public void setNoBounceLabelPages(String pages, List<String> filters) throws SQLException {
        noBounceLabel.setText(null);
    }

    public void setNoBounceLabelTime(String unit, String time, List<String> filters) throws SQLException {
        noBounceLabel.setText(null);
    }

    public void setNoBounceLabelConv(List<String> filters) throws SQLException {
        noBounceLabel.setText(null);
    }

    public void setNoConvLabel(List<String> filters) throws SQLException {
        noConversionLabel.setText(null);
    }

    public void setTotalCostLabel(List<String> filters) throws SQLException {
        float impCost = 0;
        float totalCost = 0;
        double total = Math.round(totalCost * 100.0) / 100.0;
        totalCostLabel.setText(Double.toString(total));
    }

    public void setCtrLabel(List<String> filters) throws SQLException {
        int imps = 0;
        if (imps == 0) {
            ctrLabel.setText("N/A");
        } else {
            int clicks = 0;
            float ctr = (float) clicks / imps;
            double ctrRound = Math.round(ctr * 100.0) / 100.0;
            ctrLabel.setText(Double.toString(ctrRound));
        }
    }

    public void setCpaLabel(List<String> filters) throws SQLException {
        int convs = 0;
        if (convs == 0) {
            cpaLabel.setText("N/A");
        } else {
            float impCost = 0;
            float totalCost = 0;
            float cpa = totalCost / convs;
            double cpaRound = Math.round(cpa * 100.0) / 100.0;
            cpaLabel.setText(Double.toString(cpaRound));
        }
    }

    public void setCpcLabel(List<String> filters) throws SQLException {
        int clicks = 0;
        if (clicks == 0) {
            cpcLabel.setText("N/A");
        } else {

            float impCost = 0;
            float totalCost = 0;
            float cpc = totalCost / clicks;
            double cpcRound = Math.round(cpc * 100.0) / 100.0;
            cpcLabel.setText(Double.toString(cpcRound));
        }
    }

    public void setCpmLabel(List<String> filters) throws SQLException {

        int imps = 0;
        if (imps == 0) {
            cpmLabel.setText("N/A");
        } else {

            float impCost = 0;
            float totalCost = 0;
            float cpm = totalCost / (imps * 1000);
            double cpmRound = Math.round(cpm * 100.0) / 100.0;
            cpmLabel.setText(Double.toString(cpmRound));
        }
    }

    public void setBounceRateLabelPages(String pages, List<String> filters) throws SQLException {

        int clicks = 0;
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            int bounces = 0;
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelTime(String unit, String time, List<String> filters) throws SQLException{
        int clicks = 0;
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            int bounces = 0;
            float br = (float) bounces / clicks;
            double brRound = Math.round(br * 100.0) / 100.0;
            bounceRateLabel.setText(Double.toString(brRound));
        }
    }

    public void setBounceRateLabelConv( List<String> filters) throws SQLException {
        int clicks = 0;
        if (clicks == 0) {
            bounceRateLabel.setText("N/A");
        } else {
            int bounces = 0;
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
        NoImpressionsChart.getData().add(null);
    }

    private void refreshClicksGraph(String start, String end){
        noOfClicksChart.getData().add(null);
    }

    private void refreshUniquesGraph(String start, String end){
        NoUniquesChart.getData().add(null);
    }

    /*
    private void refreshBouncePagesGraph(String start, String end){
        NoUniquesChart.getData().add(null);
    }
     */

    private void refreshConvsGraph(String start, String end){
        NoConversionsChart.getData().add(null);
    }

    private void refreshTotalCostGraph(String start, String end){
        totalCostChart.getData().add(null);
    }

    private void refreshCTRGraph(String start, String end){
        ctrChart.getData().add(null);
    }

    private void refreshCPAGraph(String start, String end){
        cpaChart.getData().add(null);
    }

    private void refreshCPCGraph(String start, String end){
        cpcChart.getData().add(null);
    }

    private void refreshCPMGraph(String start, String end){
        cpmChart.getData().add(null);
    }

    /*
    private void refreshBRPageGraph(String start, String end){
        bounceRateChart.getData().add(null);
    }
*/




}