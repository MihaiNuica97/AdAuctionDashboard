package segGroupCW;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GraphController implements Initializable
{
    @FXML
    private Label mainMetricLabel;

    @FXML
    private Pane topHBox;

    @FXML
    private JFXButton changeCampaignButton;

    @FXML
    private ImageView houseImageView;

    @FXML
    private LineChart<?, ?> mainChart;

    @FXML
    private JFXRadioButton hoursRadioButoon;

    @FXML
    private ToggleGroup timeToggle;

    @FXML
    private JFXRadioButton daysRadioButton;

    @FXML
    private JFXRadioButton weeksRadioButton;

    @FXML
    private JFXRadioButton hoursRadioButoon1;

    @FXML
    private ToggleGroup timeToggle1;

    @FXML
    private JFXRadioButton daysRadioButton1;

    @FXML
    private JFXRadioButton weeksRadioButton1;

    @FXML
    private JFXRadioButton monthsRadioButton1;

    @FXML
    private VBox testPane;

    @FXML
    private JFXCheckBox femaleCheckBox;

    @FXML
    private JFXCheckBox maleCheckbox;

    @FXML
    private JFXCheckBox age1Checkbox;

    @FXML
    private JFXCheckBox age2Checkbox;

    @FXML
    private JFXCheckBox age3Checkbox;

    @FXML
    private JFXCheckBox age4Checkbox;

    @FXML
    private JFXCheckBox age5Checkbox;

    @FXML
    private JFXCheckBox lowIncomeCheckbox;

    @FXML
    private JFXCheckBox MediumIncomeCheckbox;

    @FXML
    private JFXCheckBox highIncomeCheckbox;

    @FXML
    private JFXCheckBox shoppingCheckbox;

    @FXML
    private JFXCheckBox newsCheckbox;

    @FXML
    private JFXCheckBox blogCheckbox;

    @FXML
    private JFXCheckBox socialMCheckbox;

    @FXML
    private JFXCheckBox hobbiesCheckbox;

    @FXML
    private JFXCheckBox travelCheckbox;

    List<JFXCheckBox> checkBoxList;

    @FXML
    private JFXButton addFilterButton;

    @FXML
    private VBox filterVbox;

    @FXML
    void addFilterButton(ActionEvent event) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Label filter = new Label("Filters: ");
        hbox.getChildren().add(filter);
        for (JFXCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                Label label = new Label();
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
                filterVbox.getChildren().remove(hbox);
            }
        });
        hbox.getChildren().add(button);
        filterVbox.getChildren().add(hbox);
        for (JFXCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                checkBox.setSelected(false);
            }
        }


    }

    @FXML
    void dashboardReturn(ActionEvent event) throws IOException {
        App.setRoot("dashboard");
    }


    /**
     * Brings you to the main Input page
     * @throws IOException
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("fileUpload");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
