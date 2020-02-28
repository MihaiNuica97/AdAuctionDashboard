package segGroupCW;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

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

	@FXML
	private Button dashboardBtn;
	
	public void initialize(){
		fileChooser = new FileChooser();;
        browseClicksBtn.setOnAction( e -> browseFiles(clicksPathLabel,"Clicks Log"));
		browseImprBtn.setOnAction( e -> browseFiles(imprPathLabel,"Impressions"));
		browseServerBtn.setOnAction( e -> browseFiles(serverPathLabel,"Server Log"));
		dashboardBtn.setDisable(true);
	}



	public File browseFiles(TextField field, String fileType){
		System.out.println("Choosing file with type: " + fileType);
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter(fileType,"*.csv");
		fileChooser.getExtensionFilters().add(fileExtensions);
		fileChooser.setSelectedExtensionFilter(fileExtensions);

		File file = fileChooser.showOpenDialog(App.getScene().getWindow());
		field.setText(file.getAbsolutePath());
		if(isDBReady()){
			dashboardBtn.setDisable(false);
		}
		return file;
	}
	
	@FXML
	private void goToDashboard() throws IOException
	{
		App.setRoot("primary");
	}
	
	private Boolean isDBReady(){
		return true;
	}


}