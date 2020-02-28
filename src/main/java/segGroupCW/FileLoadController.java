package segGroupCW;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class FileLoadController {

	private FileChooser fileChooser;

	private Scene scene;
	@FXML
	private Button browseClicksBtn;

	@FXML
	private TextField clicksPathLabel;

	@FXML
	private Button browseImprBtn;
	@FXML
	private TextField imprPathLabel;

	@FXML
	private TextField serverPathLabel;
	@FXML
	private Button browseServerBtn;

	public void initialize(){
		fileChooser = new FileChooser();;
        browseClicksBtn.setOnAction( e -> browseFiles(clicksPathLabel,"Clicks Log"));
		browseImprBtn.setOnAction( e -> browseFiles(imprPathLabel,"Impressions"));
		browseServerBtn.setOnAction( e -> browseFiles(serverPathLabel,"Server Log"));
	}



	public File browseFiles(TextField field, String fileType){
		System.out.println("Choosing file with type: " + fileType);
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter(fileType,"*.csv");
		fileChooser.getExtensionFilters().add(fileExtensions);
		fileChooser.setSelectedExtensionFilter(fileExtensions);

		File file = fileChooser.showOpenDialog(App.getScene().getWindow());
		field.setText(file.getAbsolutePath());

		return file;
	}



}