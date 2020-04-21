package segGroupCW;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class FileLoadController {

	private FileChooser fileChooser;

	private HashMap<String, File> fileMap = new HashMap<String, File>(3);

	private CSVParser csvParser = new CSVParser();

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
//		ThemeController.addStyle("@/css/fileUpload.css");
//		ThemeController.getStyles();
//		System.out.println(App.getScene().getStylesheets().toString());
//		App.themeController.changeTheme("dark.css");
		fileChooser = new FileChooser();
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

		dashboardBtn.setDisable(false);

		return file;
	}
	
	@FXML
	private void goToDashboard() throws IOException
	{
		loadFiles();
		App.setRoot("dashboard");
		App.getScene().getWindow().setHeight(925);
//		App.getScene().getWindow().setWidth(1000);
	}

	@FXML
	private void test(){
		App.themeController.changeTheme("dark.css");
	}

	private void loadFiles() {
		App.dataHandler = new DataHandler(fileMap.get("Impressions"), fileMap.get("Clicks Log"), fileMap.get("Server Log"));
		System.out.println("Data loaded from files");
	}
}