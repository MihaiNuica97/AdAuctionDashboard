package segGroupCW;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class GraphController implements Initializable
{
    @FXML
    private Label mainMetricLabel, timeLabel, graphLabel;

    @FXML
    private JFXButton changeCampaignButton, settingsButton, homeButton, backButton;

    @FXML
    private Pane chartPane;

    @FXML
    private LineChart<?, ?> mainChart;

    @FXML
    private JFXRadioButton lineChartButton, pieChartButton, histogramChartButton;

    @FXML
    private JFXRadioButton hoursRadioButoon1, daysRadioButton1, weeksRadioButton1, monthsRadioButton1;

    @FXML
    private JFXCheckBox femaleCheckBox, maleCheckbox, age1Checkbox, age2Checkbox, age3Checkbox, age4Checkbox, age5Checkbox, lowIncomeCheckbox, MediumIncomeCheckbox;

    @FXML
    private JFXCheckBox highIncomeCheckbox, shoppingCheckbox, newsCheckbox, blogCheckbox, socialMCheckbox, hobbiesCheckbox, travelCheckbox;

    @FXML
    private JFXButton addFilterButton;

    /**
     * VBox for applied filters
     */
    @FXML
    private VBox filterVbox;

    @FXML
    private Label dashBoardLabel;

    /**
     * list that holds all the filter checkboxes
     */
    List<JFXCheckBox> checkBoxList;

    private PieChart pieChart;

    //histogram features
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> histogram = new BarChart<>(xAxis,yAxis);
    private String currentTG = "days";
    List <List<String>> filterSet = new ArrayList<>();


    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Text settingsIcon = GlyphsDude.createIcon(FontAwesomeIcons.COG, "40px");
        settingsButton.setGraphic(settingsIcon);

        Text homeIcon = GlyphsDude.createIcon(FontAwesomeIcons.HOME, "40px");
        homeButton.setGraphic(homeIcon);

        Text backIcon = GlyphsDude.createIcon(FontAwesomeIcons.ARROW_LEFT, "40px");
        backButton.setGraphic(backIcon);

        Tooltip tooltipSettings = new Tooltip("Change dashboard theme");
        tooltipSettings.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipHome = new Tooltip("Return to file upload");
        tooltipHome.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipBack = new Tooltip("Return to dashboard");
        tooltipBack.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipPrint = new Tooltip("Print view");
        tooltipPrint.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipAddFilter = new Tooltip("Add a filtered line to graph");
        tooltipAddFilter.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipTime = new Tooltip("Change time scale of graph");
        tooltipTime.setStyle("-fx-font-size: 10");
        tooltipTime.setShowDelay(Duration.seconds(0.05));
        Tooltip tooltipGraph = new Tooltip("Change type of graph");
        tooltipGraph.setStyle("-fx-font-size: 10");
        tooltipGraph.setShowDelay(Duration.seconds(0.05));

        //All labels popup
        //buttons
        settingsButton.setTooltip(tooltipSettings);
        homeButton.setTooltip(tooltipHome);
        backButton.setTooltip(tooltipBack);
        addFilterButton.setTooltip(tooltipAddFilter);
        timeLabel.setTooltip(tooltipTime);
        graphLabel.setTooltip(tooltipGraph);

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
     * Button that adds filters to the HBox
     * @param event
     */
    @FXML
    void addFilterButton(ActionEvent event) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Label filter = new Label("Filters: ");
        filter.setStyle("-fx-text-fill: -label");
        hbox.getChildren().add(filter);
        for (JFXCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                Label label = new Label();
                label.setStyle("-fx-text-fill: -label");
                label.setText(checkBox.getText());
                hbox.getChildren().add(label);
            }
        }
        JFXButton button = new JFXButton();
        button.setStyle("-fx-background-color: #ff0000");
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setText("X");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = filterVbox.getChildren().indexOf(hbox);
                filterSet.remove(index);
                mainChart.getData().remove(index+1);
                filterVbox.getChildren().remove(hbox);
            }
        });
        hbox.getChildren().add(button);
        filterVbox.getChildren().add(hbox);

        List<String> selectedCheckBoxes = new ArrayList<>();
        for (JFXCheckBox checkBox : checkBoxList) {
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
                filterSet.add(selectedCheckBoxes);
                HashSet<String> users = App.dataHandler.filterUsers(selectedCheckBoxes);
                HashSet<Impression> impressions = App.dataHandler.filterImpressions(users);
                List<Server> server = App.dataHandler.filterServers(users);
                List<Click> clicks = App.dataHandler.filterClicks(users);
                refreshGraph(new ArrayList<>(impressions),clicks,server,new ArrayList<>(users));

            }

        }

        //deleting the selected checkboxes after adding them
        for (JFXCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                System.out.println(checkBox.getText());
                checkBox.setSelected(false);
            }
        }
    }

    /**
     * Returns to the dashboard page
     * @param event
     * @throws IOException
     */

    @FXML
    void goHome(ActionEvent event) throws IOException {
        App.setRoot("dashboard");
        App.getScene().getWindow().setHeight(925);
    }

    /**
     * Returns to the settings page
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
     * Change total cost chart to pie chart
     */
    @FXML
    void pieChartChange() {
        chartPane.getChildren().clear();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Click Cost", 13),
                        new PieChart.Data("Impression Cost", 25));
        pieChart = new PieChart(pieChartData);
        chartPane.getChildren().add(pieChart);
    }

    /**
     * Change total cost chart to histogram
     * @param event
     */
    @FXML
    void histogramChange(ActionEvent event) {
        chartPane.getChildren().clear();
        chartPane.getChildren().remove(mainChart);

        histogram.setCategoryGap(0);
        histogram.setBarGap(0);

        xAxis.setLabel("Time");
        yAxis.setLabel("Total Cost");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Histogram");
        //series1.getData().add(new XYChart.Data("0-10", group[0]));
        histogram.getData().addAll(series1);

        chartPane.getChildren().add(histogram);
    }

    /**
     * Change total cost chart to line chart
     */
    @FXML
    void lineChartChange() {
        chartPane.getChildren().clear();
        chartPane.getChildren().add(mainChart);
    }


    private void refreshGraph(List<Impression> impressionList, List<Click> clickList, List<Server> serverList, List<String> userList){
        switch (App.currentGraph) {
            case "Impressions":
                refreshImpressionsGraph(impressionList);
                break;
            case "CLicks":
                refreshClicksGraph(clickList);
                break;
            case "Uniques":
                refreshUniquesGraph(clickList);
                break;
            case "Bounces":
                refreshBounceGraph(serverList);
                break;
            case "Conversions":
                refreshConvsGraph(serverList);
                break;
            case "Total Cost":
                refreshTotalCostGraph(clickList, impressionList);
                break;
            case "Click Through Rate":
                refreshCTRGraph(clickList, impressionList);
                break;
            case "CPA":
                refreshCPAGraph(clickList, impressionList, serverList);
                break;
            case "Cost per Click":
                refreshCPCGraph(clickList, impressionList);
                break;
            case "CPM":
                refreshCPMGraph(clickList, impressionList);
                break;
            case "Bounce Rate":
                refreshBounceRateGraph(serverList);
                break;
            case "":
                refreshImpressionsGraph(impressionList);
                break;
        }
    }


    private void refreshImpressionsGraph(List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.impressionsAtDate(date,impressionList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshClicksGraph(List<Click> clicksList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.clicksAtDate(date,clicksList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshUniquesGraph(List<Click> clicksList ){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.uniquesAtDate(date,clicksList)));
        }
        mainChart.getData().add(series);
    }

    /*
    private void refreshBouncePagesGraph(String start, String end){
        NoUniquesChart.getData().add(null);
    }
     */

    private void refreshBounceGraph(List<Server> server){
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        switch (App.bounceDef) {
            case "Page":
                for(LocalDate date: dates){
                    series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bouncePageAtDate(date, server, App.bounceValue)));
                }
                mainChart.getData().add(series1);
                break;
            case "Conv":
                for(LocalDate date: dates){
                    series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceConvAtDate(date,server)));
                }
                mainChart.getData().add(series1);
                break;
            case "Time":
                for(LocalDate date: dates){
                    series1.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceTimeAtDate(date,server,App.bounceValue)));
                }
                mainChart.getData().add(series1);
                break;
        }
    }

    private void refreshConvsGraph( List<Server> serverList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.conversionsAtDate(date,serverList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshTotalCostGraph(List<Click> clicksList, List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.totalCostAtDate(date,clicksList,impressionList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshCTRGraph( List<Click> clicksList, List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialClicksTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.ctrAtDate(date,clicksList,impressionList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshCPAGraph(List<Click> clicksList, List<Impression> impressionList, List<Server> serverList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpaAtDate(date,clicksList,impressionList,serverList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshCPCGraph(List<Click> clicksList, List<Impression> impressionList){
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        for(LocalDate date: dates){
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpcAtDate(date,clicksList,impressionList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshCPMGraph(List<Click> clicksList, List<Impression> impressionList) {
        XYChart.Series series = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG, 1);
        for (LocalDate date : dates) {
            series.getData().add(new XYChart.Data(date.toString(), App.dataHandler.cpmAtDate(date, clicksList, impressionList)));
        }
        mainChart.getData().add(series);
    }

    private void refreshBounceRateGraph(List<Server> server){
        XYChart.Series series2 = new XYChart.Series();
        ArrayList<LocalDate> dates = App.dataHandler.initialImprTI(currentTG,1);
        switch (App.bounceDef) {
            case "Page":
                for(LocalDate date: dates){
                    series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRatePageAtDate(date,server,App.bounceValue)));
                }
                mainChart.getData().add(series2);
                break;
            case "Conv":
                for(LocalDate date: dates){
                    series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRateConvAtDate(date,server)));
                }
                mainChart.getData().add(series2);
                break;
            case "Time":
                for(LocalDate date: dates){
                    series2.getData().add(new XYChart.Data(date.toString(), App.dataHandler.bounceRateTimeAtDate(date,server,App.bounceValue)));
                }
                mainChart.getData().add(series2);
                break;
        }
    }


    public void setGraphsOptions(GraphOptions options){
        dashBoardLabel.setText(options.labelName);
        mainChart.getData().add(options.chartData);



//        mainMetricLabel.setText(options.labelName);
//        initGraph();
    }

    @FXML
    public void changeTimeDays(ActionEvent actionEvent) {
        currentTG = "days";
        refreshTimeGranularity();
    }

    @FXML
    public void changeTimeWeeks(ActionEvent actionEvent) {
        System.out.println("here");
        currentTG = "weeks";
        refreshTimeGranularity();

    }

    @FXML
    public void changeTimeMonths(ActionEvent actionEvent) {
        currentTG = "months";
        refreshTimeGranularity();
    }

    private void refreshTimeGranularity(){
        mainChart.getData().clear();
        filterVbox.getChildren().clear();


        List<Impression> impressions = App.dataHandler.getImpressions();
        List<Server> server = App.dataHandler.getServerLogs();
        List<Click> clicks = App.dataHandler.getClicks();
        refreshGraph(impressions,clicks,server,null);
    }

    /*
    private void refreshTimeGranularity(){
        mainChart.getData().clear();
        for(List<String> filters: filterSet) {
            System.out.println("list loop");
            if (!filters.isEmpty()) {

                if (filters.containsAll(DataHandler.genders)) {
                    filters.removeAll(DataHandler.genders);
                }
                if (filters.containsAll(DataHandler.income)) {
                    filters.removeAll(DataHandler.income);
                }
                if (filters.containsAll(DataHandler.ages)) {
                    filters.removeAll(DataHandler.ages);
                }
                if (!filters.isEmpty()) {
                    filterSet.add(filters);
                    List<String> users = App.dataHandler.filterUsers(filters);
                    List<Impression> impressions = App.dataHandler.filterImpressions(users);
                    List<Server> server = App.dataHandler.filterServers(users);
                    List<Click> clicks = App.dataHandler.filterClicks(users);
                    refreshGraph(impressions, clicks, server, users);

                }

            }
        }
    }
    */
}
