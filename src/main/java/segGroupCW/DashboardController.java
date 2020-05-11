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
import org.w3c.dom.CDATASection;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

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
    private Double bounceValue = 1.0;

    private static DateFormat dateformat = new DateFormat();

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLabels();
        initGraphs();
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

            if (selectedCheckBoxes.containsAll(DataHandler.genders)) {
                selectedCheckBoxes.removeAll(DataHandler.genders);
            }
            if (selectedCheckBoxes.containsAll(DataHandler.income)) {
                selectedCheckBoxes.removeAll(DataHandler.income);
            }
            if (selectedCheckBoxes.containsAll(DataHandler.ages)) {
                selectedCheckBoxes.removeAll(DataHandler.ages);
            }

            if (!selectedCheckBoxes.isEmpty()) {
                List<String> users = App.dataHandler.filterUsers(selectedCheckBoxes);
                List<Impression> impressions = App.dataHandler.filterImpressions(users);
                List<Server> server = App.dataHandler.filterServers(users);
                List<Click> clicks = App.dataHandler.filterClicks(users);

                int noImps = App.dataHandler.calcImpressions(impressions);
                noImprLabel.setText(Integer.toString(noImps));
                int noClicks = App.dataHandler.calcClicks(clicks);
                noClicksLabel.setText(Integer.toString(noClicks));
                noUniqueLabel.setText(Integer.toString(App.dataHandler.calcUniques(clicks)));
                int bounces;
                switch (bounceMethod) {
                    case "Page":
                        bounces = App.dataHandler.calcBouncePage(server, bounceValue);
                        noBounceLabel.setText(Integer.toString(bounces));
                        bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRatePages(bounces, noClicks)));
                        break;
                    case "Conv":
                        bounces = App.dataHandler.calcBounceConv(server);
                        noBounceLabel.setText(Integer.toString(bounces));
                        bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateConv(bounces, noClicks)));
                        break;
                    case "Time":
                        bounces = App.dataHandler.calcBounceTime(server, bounceValue);
                        noBounceLabel.setText(Integer.toString(bounces));
                        bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateTime(bounces, noClicks)));
                    default:
                        bounces = 0;
                        noBounceLabel.setText("n/a");
                        bounceRateLabel.setText("n/a");
                }
                int convs = App.dataHandler.calcConversions(server);
                noConversionLabel.setText(Integer.toString(convs));
                double totalCost = App.dataHandler.calcTotalCost(clicks, impressions);
                totalCostLabel.setText(Double.toString(totalCost));
                ctrLabel.setText(Double.toString(App.dataHandler.calcCTR(noClicks, noImps)));
                cpaLabel.setText(Double.toString(App.dataHandler.calcCPA(totalCost, convs)));
                cpcLabel.setText(Double.toString(App.dataHandler.calcCPC(totalCost, noClicks)));
                cpmLabel.setText(Double.toString(App.dataHandler.calcCPM(totalCost, noImps)));

                refreshImpressionsGraph(impressions);
                refreshClicksGraph(clicks);
                refreshUniquesGraph(clicks);
                refreshConvsGraph(server);
                refreshTotalCostGraph(clicks,impressions);
                refreshCTRGraph(clicks,impressions);
                refreshCPAGraph(clicks,impressions,server);
                refreshCPCGraph(clicks,impressions);
                refreshCPMGraph(clicks,impressions);
            } else {
                initLabels();
                initGraphs();
            }

        } else {
            initLabels();
            initGraphs();
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
        Insets insets = new Insets(0,10,10,10);
        //box.setPadding(insets);
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
                    bounceMethod = "Time";
                    bounceValue = bounce1;
                }
                else if(bounceToggle.getSelectedToggle().equals(bounceDefinition2)){
                    //Number of pages viewed is below a threshold
                    Double bounce2 = definition2Slider.getValue();
                    System.out.println(bounce2);
                    bounceMethod = "Page";
                    bounceValue = bounce2;
                }
                else{
                    //Conversion didn't take place
                    bounceMethod = "Conv";
                }
                refreshBounceLabels();
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

        initImpressionsGraph();
        initClicksGraph();
        initUniquesGraph();
        initConvsGraph();
        initTotalCostGraph();
        initCTRGraph();
        initCPAGraph();
        initCPCGraph();
        initCPMGraph();
        //refreshBRPageGraph(dates[2],dates[3]);

    }

    private void initLabels() {
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

    void refreshBounceLabels() {
        int bounces;
        switch (bounceMethod) {
            case "Page":
                bounces = App.dataHandler.calcBouncePage(bounceValue);
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRatePages(bounceValue)));
                break;
            case "Conv":
                bounces = App.dataHandler.calcBounceConv();
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateConv()));
                break;
            case "Time":
                bounces = App.dataHandler.calcBounceTime(bounceValue);
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateTime(bounceValue)));
            default:
                noBounceLabel.setText("n/a");
                bounceRateLabel.setText("n/a");
        }
    }




    private void initImpressionsGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.impressionsAtDate(date)));
        }
        NoImpressionsChart.getData().add(series);
    }

    private void initClicksGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.clicksAtDate(date)));
        }
        noOfClicksChart.getData().add(series);
    }

    private void initUniquesGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.uniquesAtDate(date)));
        }
        NoUniquesChart.getData().add(series);
    }

    /*
    private void refreshBouncePagesGraph(String start, String end){
        NoUniquesChart.getData().add(null);
    }
     */

    private void initConvsGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.conversionsAtDate(date)));
        }
        NoConversionsChart.getData().add(series);
    }

    private void initTotalCostGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.totalCostAtDate(date)));
        }
        totalCostChart.getData().add(series);
    }

    private void initCTRGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.ctrAtDate(date)));
        }
        ctrChart.getData().add(series);
    }

    private void initCPAGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpaAtDate(date)));
        }
        cpaChart.getData().add(series);
    }

    private void initCPCGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpcAtDate(date)));
        }
        cpcChart.getData().add(series);
    }

    private void initCPMGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpmAtDate(date)));
        }
        cpmChart.getData().add(series);
    }

    /*
    private void refreshBRPageGraph(String start, String end){
        bounceRateChart.getData().add(null);
    }
*/

    private void refreshImpressionsGraph(List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.impressionsAtDate(date,impressionList)));
        }
        NoImpressionsChart.getData().clear();
        NoImpressionsChart.getData().add(series);
    }

    private void refreshClicksGraph(List<Click> clicksList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.clicksAtDate(date,clicksList)));
        }
        noOfClicksChart.getData().clear();
        noOfClicksChart.getData().add(series);
    }

    private void refreshUniquesGraph(List<Click> clicksList ){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.uniquesAtDate(date,clicksList)));
        }
        NoUniquesChart.getData().clear();
        NoUniquesChart.getData().add(series);
    }

    /*
    private void refreshBouncePagesGraph(String start, String end){
        NoUniquesChart.getData().add(null);
    }
     */

    private void refreshConvsGraph( List<Server> serverList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.conversionsAtDate(date,serverList)));
        }
        NoConversionsChart.getData().clear();
        NoConversionsChart.getData().add(series);
    }

    private void refreshTotalCostGraph(List<Click> clicksList, List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.totalCostAtDate(date,clicksList,impressionList)));
        }
        totalCostChart.getData().clear();
        totalCostChart.getData().add(series);
    }

    private void refreshCTRGraph( List<Click> clicksList, List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.ctrAtDate(date,clicksList,impressionList)));
        }
        ctrChart.getData().clear();
        ctrChart.getData().add(series);
    }

    private void refreshCPAGraph(List<Click> clicksList, List<Impression> impressionList, List<Server> serverList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpaAtDate(date,clicksList,impressionList,serverList)));
        }
        cpaChart.getData().clear();
        cpaChart.getData().add(series);
    }

    private void refreshCPCGraph(List<Click> clicksList, List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpcAtDate(date,clicksList,impressionList)));
        }
        cpcChart.getData().clear();
        cpcChart.getData().add(series);
    }

    private void refreshCPMGraph(List<Click> clicksList, List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpmAtDate(date,clicksList,impressionList)));
        }
        cpmChart.getData().clear();
        cpmChart.getData().add(series);
    }




}