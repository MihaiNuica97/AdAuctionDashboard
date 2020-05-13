package segGroupCW;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
import java.util.List;


public class DashboardController implements Initializable {

    @FXML
    private JFXButton changeCampaignButton;   //do we want this?

    @FXML
    private Label noImprLabel, noClicksLabel, noUniqueLabel, noBounceLabel, noConversionLabel, totalCostLabel, ctrLabel, cpaLabel, cpcLabel, cpmLabel, bounceRateLabel;

    @FXML
    private Label noClicksTitle, noImprTitle, noUniqueTitle, noBounceTitle, noConversionTitle, totalCostTitle, ctrTitle, cpaTitle, cpcTitle, cpmTitle, bounceRateTitle;

    @FXML
    private JFXButton bounceDefinitionButton, settingsButton, homeButton, printButton;

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

    private static DateFormat dateformat = new DateFormat();

    private HashMap<String, Double> initialLabels = new HashMap<>();
    private HashMap<String, XYChart.Series> initialSeries = new HashMap<>();

    @FXML
    private VBox graphVBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane graphAnchorPane;
    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calcInitLabels();
        setInitLabels();
        initGraphs();
//    DONT DELETE ^

        printButton.setOnAction( e -> print());

        Text settingsIcon = GlyphsDude.createIcon(FontAwesomeIcons.COG, "40px");
        settingsButton.setGraphic(settingsIcon);

        Text homeIcon = GlyphsDude.createIcon(FontAwesomeIcons.HOME, "40px");
        homeButton.setGraphic(homeIcon);

        Text bounceIcon = GlyphsDude.createIcon(FontAwesomeIcons.PENCIL, "20px");
        bounceDefinitionButton.setGraphic(bounceIcon);

        Text printIcon = GlyphsDude.createIcon(FontAwesomeIcons.PRINT, "40px");
        printButton.setGraphic(printIcon);

        Tooltip tooltip = new Tooltip("Change definition of a Bounce");
        tooltip.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipImpressions = new Tooltip("When a user views an Ad");
        tooltipImpressions.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipClicks = new Tooltip("When a user clicks an Ad");
        tooltipImpressions.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipUnique = new Tooltip("No. of unique users that click on an Ad");
        tooltipUnique.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipBounce = new Tooltip("User fails to interact with website");
        tooltipBounce.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipConversion = new Tooltip("User acts on an Ad");
        tooltipConversion.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipTotalCost = new Tooltip("Cost of displaying Ad");
        tooltipTotalCost.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipCTR = new Tooltip("Average clicks per impression");
        tooltipCTR.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipCPA = new Tooltip("Average amount spent on Ad campaign for each conversion");
        tooltipCPA.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipCPC = new Tooltip("Average amount spent on Ad campaign for each click ");
        tooltipCPC.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipCPM = new Tooltip("Average amount spent on Ad campaign for every 1,000 impressions");
        tooltipCPM.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipBounceRate = new Tooltip("Average no. of bounces per click");
        tooltipBounceRate.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipSettings = new Tooltip("Change dashboard theme");
        tooltipSettings.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipHome = new Tooltip("Return to file upload");
        tooltipHome.setShowDelay(Duration.seconds(0.05));

        //All labels popup
        bounceDefinitionButton.setTooltip(tooltip);
        noImprTitle.setTooltip(tooltipImpressions);
        noClicksTitle.setTooltip(tooltipClicks);
        noUniqueTitle.setTooltip(tooltipUnique);
        noBounceTitle.setTooltip(tooltipBounce);
        noConversionTitle.setTooltip(tooltipConversion);
        totalCostTitle.setTooltip(tooltipTotalCost);
        ctrTitle.setTooltip(tooltipCTR);
        cpaTitle.setTooltip(tooltipCPA);
        cpcTitle.setTooltip(tooltipCPC);
        cpmTitle.setTooltip(tooltipCPM);
        bounceRateTitle.setTooltip(tooltipBounceRate);
        //buttons
        settingsButton.setTooltip(tooltipSettings);
        homeButton.setTooltip(tooltipHome);

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

        noImprPane.setOnMouseClicked( e -> graphView("Impressions"));
        noClicksPane.setOnMouseClicked( e -> graphView("Clicks"));
        noOfUniques.setOnMouseClicked( e -> graphView("Uniques"));
        noBouncePane.setOnMouseClicked( e -> graphView("Bounces"));
        noOfConversionsPane.setOnMouseClicked( e -> graphView("Conversions"));
        totalCostPane.setOnMouseClicked( e -> graphView("Total Cost"));
        ctrPane.setOnMouseClicked( e -> graphView("Click Through Rate"));
        cpaPane.setOnMouseClicked( e -> graphView("CPA"));
        cpcPane.setOnMouseClicked( e -> graphView("Cost per Click"));
        cpmPane.setOnMouseClicked( e -> graphView("CPM"));
        bounceRatePane.setOnMouseClicked( e -> graphView("Bounce Rate"));
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
            ArrayList<String> contexts = new ArrayList<>();
            if (selectedCheckBoxes.containsAll(DataHandler.context)) {
                selectedCheckBoxes.removeAll(DataHandler.context);
            } else {
                for (String each : selectedCheckBoxes) {
                    if (DataHandler.context.contains(each)) {
                        contexts.add(each);
                    }
                }
            }

            if (!selectedCheckBoxes.isEmpty()) {
                List<String> users = App.dataHandler.filterUsers(selectedCheckBoxes);
                List<Impression> impressions;
                List<Server> server;
                List<Click> clicks;
                if (!contexts.isEmpty()) {
                    impressions = App.dataHandler.filterImpressions(users, contexts);
                    server = App.dataHandler.filterCServers(impressions);
                    clicks = App.dataHandler.filterCClicks(impressions);
                } else {
                    impressions = App.dataHandler.filterImpressions(users);
                    server = App.dataHandler.filterServers(users);
                    clicks = App.dataHandler.filterClicks(users);
                }
                int noImps = App.dataHandler.calcImpressions(impressions);
                noImprLabel.setText(Integer.toString(noImps));
                int noClicks = App.dataHandler.calcClicks(clicks);
                noClicksLabel.setText(Integer.toString(noClicks));
                noUniqueLabel.setText(Integer.toString(App.dataHandler.calcUniques(clicks)));
                int bounces;
                XYChart.Series series1 = new XYChart.Series();
                XYChart.Series series2 = new XYChart.Series();
                ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
                switch (App.bounceDef) {
                    case "Page":
                        bounces = App.dataHandler.calcBouncePage(server, App.bounceValue);
                        noBounceLabel.setText(Integer.toString(bounces));
                        bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRatePages(bounces, noClicks)));
                        for(LocalDate date: dates){
                            series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bouncePageAtDate(date,server, App.bounceValue)));
                            series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRatePageAtDate(date, server, App.bounceValue)));
                        }
                        NoBouncesChart.getData().clear();
                        NoBouncesChart.getData().add(series1);
                        bounceRateChart.getData().clear();
                        bounceRateChart.getData().add(series2);
                        break;
                    case "Conv":
                        bounces = App.dataHandler.calcBounceConv(server);
                        noBounceLabel.setText(Integer.toString(bounces));
                        bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateConv(bounces, noClicks)));
                        for(LocalDate date: dates){
                            series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceConvAtDate(date,server)));
                            series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRateConvAtDate(date, server)));
                        }
                        NoBouncesChart.getData().clear();
                        NoBouncesChart.getData().add(series1);
                        bounceRateChart.getData().clear();
                        bounceRateChart.getData().add(series2);
                        break;
                    case "Time":
                        bounces = App.dataHandler.calcBounceTime(server, App.bounceValue);
                        noBounceLabel.setText(Integer.toString(bounces));
                        bounceRateLabel.setText(Double.toString(App.dataHandler.calcBounceRateTime(bounces, noClicks)));
                        for(LocalDate date: dates){
                            series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceTimeAtDate(date,server,App.bounceValue)));
                            series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRateTimeAtDate(date, server, App.bounceValue)));
                        }
                        NoBouncesChart.getData().clear();
                        NoBouncesChart.getData().add(series1);
                        bounceRateChart.getData().clear();
                        bounceRateChart.getData().add(series2);
                        break;
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
                setInitLabels();
                setInitGraphs();
            }

        } else {
            setInitLabels();
            setInitGraphs();
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
        setInitLabels();
        setInitGraphs();
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

    @FXML
    void print(){
        WritableImage snapshot = graphAnchorPane.snapshot(new SnapshotParameters(), null);
        ImageView printImage = new ImageView(snapshot);
        printImage.setFitWidth(450);
        printImage.setFitHeight(740);

        Node printNode = (Node) printImage;

        PrintController printer = new PrintController(printNode);
        printer.print();

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
                    App.bounceDef = "Time";
                    App.bounceValue = bounce1;
                }
                else if(bounceToggle.getSelectedToggle().equals(bounceDefinition2)){
                    //Number of pages viewed is below a threshold
                    Double bounce2 = definition2Slider.getValue();
                    System.out.println(bounce2);
                    App.bounceDef = "Page";
                    App.bounceValue = bounce2;
                }
                else{
                    //Conversion didn't take place
                    App.bounceDef = "Conv";
                }
                refreshBounceLabels();
                refreshBounceGraph();
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



    @FXML
    void graphView(String graphName){
        System.out.println(graphName);
        GraphOptions options = new GraphOptions(graphName);
        options.chartData = initialSeries.get(graphName);

        try {
            App.switchToGraphView(options,graphName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initGraphs(){
        initImpressionsGraph();
        initClicksGraph();
        initUniquesGraph();
        refreshBounceGraph();
        initConvsGraph();
        initTotalCostGraph();
        initCTRGraph();
        initCPAGraph();
        initCPCGraph();
        initCPMGraph();
    }

    private void calcInitLabels() {
        int impressions = App.dataHandler.calcImpressions();
        initialLabels.put("Impressions", (double) impressions);
        int clicks = App.dataHandler.calcClicks();
        initialLabels.put("Clicks", (double) clicks);
        int uniques = App.dataHandler.calcUniques();
        initialLabels.put("Uniques", (double) uniques);

        int bounces = App.dataHandler.calcBouncePage(App.bounceValue);
        System.out.println(bounces);
        initialLabels.put("Bounces", (double) bounces);
        initialLabels.put("Bounce Rate", App.dataHandler.calcBounceRatePages(bounces, clicks));

        int convs = App.dataHandler.calcConversions();
        initialLabels.put("Conversions", (double) convs);
        double totalCost = App.dataHandler.calcTotalCost();
        initialLabels.put("TotalCost", totalCost);
        initialLabels.put("CTR", App.dataHandler.calcCTR(clicks, impressions));
        initialLabels.put("CPA",App.dataHandler.calcCPA(totalCost, convs));
        initialLabels.put("CPC",App.dataHandler.calcCPC(totalCost, clicks));
        initialLabels.put("CPM",App.dataHandler.calcCPM(totalCost, impressions));
    }

    void refreshBounceLabels() {
        int bounces;
        double bounceRate;
        System.out.println(App.bounceDef);
        switch (App.bounceDef) {

            case "Page":
                bounces = App.dataHandler.calcBouncePage(App.bounceValue);
                bounceRate = App.dataHandler.calcBounceRatePages(bounces, (initialLabels.get("Clicks").intValue()));
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(bounceRate));
                initialLabels.put("Bounces", (double) bounces);
                initialLabels.put("Bounce Rate", bounceRate);
                break;
            case "Conv":
                bounces = App.dataHandler.calcBounceConv();
                bounceRate = App.dataHandler.calcBounceRateConv(bounces, (initialLabels.get("Clicks").intValue()));
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(bounceRate));
                initialLabels.put("Bounces", (double) bounces);
                initialLabels.put("Bounce Rate", bounceRate);
                break;
            case "Time":
                bounces = App.dataHandler.calcBounceTime(App.bounceValue);
                bounceRate = App.dataHandler.calcBounceRateTime(bounces, (initialLabels.get("Clicks").intValue()));
                noBounceLabel.setText(Integer.toString(bounces));
                bounceRateLabel.setText(Double.toString(bounceRate));
                initialLabels.put("Bounces", (double) bounces);
                initialLabels.put("Bounce Rate", bounceRate);
                break;
            default:
                bounces = 0;
                bounceRate = 0;
                noBounceLabel.setText("n/a");
                bounceRateLabel.setText("n/a");
        }
        System.out.println(bounces);
    }

    private void refreshBounceGraph(){
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        switch (App.bounceDef) {
            case "Page":
                for(LocalDate date: dates){
                    series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bouncePageAtDate(date,App.bounceValue)));
                    series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRatePageAtDate(date,App.bounceValue)));
                }
                NoBouncesChart.getData().clear();
                NoBouncesChart.getData().add(series1);
                initialSeries.put("Bounces", series1);
                bounceRateChart.getData().clear();
                bounceRateChart.getData().add(series2);
                initialSeries.put("Bounce Rate", series2);
                break;
            case "Conv":
                for(LocalDate date: dates){
                    series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceConvAtDate(date)));
                    series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRateConvAtDate(date)));
                }
                NoBouncesChart.getData().clear();
                NoBouncesChart.getData().add(series1);
                initialSeries.put("Bounces", series1);
                bounceRateChart.getData().clear();
                bounceRateChart.getData().add(series2);
                initialSeries.put("Bounce Rate", series2);
                break;
            case "Time":
                for(LocalDate date: dates){
                    series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceTimeAtDate(date,App.bounceValue)));
                    series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRateTimeAtDate(date,App.bounceValue)));
                }
                NoBouncesChart.getData().clear();
                NoBouncesChart.getData().add(series1);
                initialSeries.put("Bounces", series1);
                bounceRateChart.getData().clear();
                bounceRateChart.getData().add(series2);
                initialSeries.put("Bounce Rate", series2);
                break;
        }
    }

    void setInitLabels() {
        noImprLabel.setText(Integer.toString(initialLabels.get("Impressions").intValue()));
        noClicksLabel.setText(Integer.toString(initialLabels.get("Clicks").intValue()));
        noUniqueLabel.setText(Integer.toString(initialLabels.get("Uniques").intValue()));

        refreshBounceLabels();

        noConversionLabel.setText(Integer.toString(initialLabels.get("Conversions").intValue()));
        totalCostLabel.setText(Double.toString(initialLabels.get("TotalCost")));
        ctrLabel.setText(Double.toString(initialLabels.get("CTR")));
        cpaLabel.setText(Double.toString(initialLabels.get("CPA")));
        cpcLabel.setText(Double.toString(initialLabels.get("CPC")));
        cpmLabel.setText(Double.toString(initialLabels.get("CPM")));
    }

    void setInitGraphs() {
        NoImpressionsChart.getData().clear();
        NoImpressionsChart.getData().add(initialSeries.get("Impressions"));
        noOfClicksChart.getData().clear();
        noOfClicksChart.getData().add(initialSeries.get("Clicks"));
        NoUniquesChart.getData().clear();
        NoUniquesChart.getData().add(initialSeries.get("Uniques"));
        NoConversionsChart.getData().clear();
        NoConversionsChart.getData().add(initialSeries.get("Conversions"));
        refreshBounceGraph();
        totalCostChart.getData().clear();
        totalCostChart.getData().add(initialSeries.get("Total Cost"));
        ctrChart.getData().clear();
        ctrChart.getData().add(initialSeries.get("Click Through Rate"));
        cpaChart.getData().clear();
        cpaChart.getData().add(initialSeries.get("CPA"));
        cpcChart.getData().clear();
        cpcChart.getData().add(initialSeries.get("Cost per Click"));
        cpmChart.getData().clear();
        cpmChart.getData().add(initialSeries.get("CPM"));
    }

    private void initImpressionsGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.impressionsAtDate(date)));
        }
        initialSeries.put("Impressions", series);
        NoImpressionsChart.getData().add(series);
    }

    private void initClicksGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.clicksAtDate(date)));
        }
        initialSeries.put("Clicks", series);
        noOfClicksChart.getData().add(series);
    }

    private void initUniquesGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.uniquesAtDate(date)));
        }
        initialSeries.put("Uniques", series);
        NoUniquesChart.getData().add(series);
    }

    private void initBounceGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bouncePageAtDate(date,App.bounceValue)));
        }
        initialSeries.put("Bounces", series);
        NoBouncesChart.getData().add(series);
    }

    private void initConvsGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.conversionsAtDate(date)));
        }
        initialSeries.put("Conversions", series);
        NoConversionsChart.getData().add(series);
    }

    private void initTotalCostGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.totalCostAtDate(date)));
        }
        initialSeries.put("Total Cost", series);
        totalCostChart.getData().add(series);
    }

    private void initCTRGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.ctrAtDate(date)));
        }
        initialSeries.put("Click Through Rate", series);
        ctrChart.getData().add(series);
    }

    private void initCPAGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpaAtDate(date)));
        }
        initialSeries.put("CPA", series);
        cpaChart.getData().add(series);
    }

    private void initCPCGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpcAtDate(date)));
        }
        initialSeries.put("Cost per Click", series);
        cpcChart.getData().add(series);
    }

    private void initCPMGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpmAtDate(date)));
        }
        initialSeries.put("CPM", series);
        cpmChart.getData().add(series);
    }

    private void initBounceRateGraph(){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI("days",1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRatePageAtDate(date,App.bounceValue)));
        }
        initialSeries.put("Bounces", series);
        bounceRateChart.getData().add(series);
    }

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