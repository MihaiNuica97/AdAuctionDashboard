package segGroupCW;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable
{
    @FXML
    private VBox testPane;

    @FXML
    private JFXComboBox<String> genderBox;


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

       // genderBox.getItems().removeAll(genderBox.getItems());
        //genderBox.getItems().setAll("Female", "Male");
       // genderBox.getSelectionModel().select("Male");

        ObservableList<String> elements = FXCollections.observableArrayList(
                new String("Element 1"),
                new String("Element 2")
        );
/*

        genderBox.setItems(elements);
        genderBox.setVisible(true);


        Label label = new Label("Label");
        testPane.getChildren().add(label);
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Tab 1");

        tabPane.getTabs().add(tab1);

        testPane.getChildren().add(tabPane);
*/
    }



}
