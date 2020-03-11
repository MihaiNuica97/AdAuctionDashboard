package segGroupCW;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileLoadController {

	private FileChooser fileChooser;

	private HashMap<String, File> fileMap = new HashMap<String, File>(3);

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
//		dashboardBtn.setDisable(true);
	}



	public File browseFiles(TextField field, String fileType){
		System.out.println("Choosing file with type: " + fileType);
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter(fileType,"*.csv");
		fileChooser.getExtensionFilters().add(fileExtensions);
		fileChooser.setSelectedExtensionFilter(fileExtensions);

		File file = fileChooser.showOpenDialog(App.getScene().getWindow());
		fileMap.put(fileType, file);
		field.setText(file.getAbsolutePath());
		if(isDBReady()){
			dashboardBtn.setDisable(false);
		}
		return file;
	}
	
	@FXML
	private void goToDashboard() throws IOException
	{
		System.out.println("Dashboard button clicked");
//		(new CSVParser()).parse(fileMap.get("Clicks Log"), fileMap.get("Server Log"), fileMap.get("Impressions"));
		App.setRoot("dashboard");
		App.getScene().getWindow().setHeight(900);
		App.getScene().getWindow().setWidth(968);
	}
	private Boolean isDBReady(){
		return true;
	}

	private void loadFilesToDB(){

	}


}