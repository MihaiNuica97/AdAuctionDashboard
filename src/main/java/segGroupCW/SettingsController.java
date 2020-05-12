package segGroupCW;

import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    public VBox stylesVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> stylesList = App.themeController.getThemeNames();
        ToggleGroup btnGroup = new ToggleGroup();

//        dynamically generates radio buttons based on what css files are present in the theme folder
        for(String name:stylesList){
            JFXRadioButton newButton = new JFXRadioButton();
            newButton.setToggleGroup(btnGroup);
            newButton.getStyleClass().add("text-secondary");
            if(name.equals(App.themeController.getCurrentTheme())){
                newButton.setSelected(true);
            }
            newButton.setOnAction(e -> changeTheme(name));
            newButton.setText(App.themeController.getDisplayNames().get(name));
            stylesVBox.getChildren().add(newButton);
        }
    }

//    changes the overall app theme. called by the dynamically generated buttons
    private void changeTheme(String themeName){
        stylesVBox.getScene().getStylesheets().remove(App.themeController.getCurrentThemeUrl());
        stylesVBox.getScene().getStylesheets().add(App.themeController.getThemeURL(themeName));
        App.themeController.changeTheme(themeName);
    }

//    closes the dialog
    @FXML
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) stylesVBox.getScene().getWindow();
        stage.close();
    }
}
