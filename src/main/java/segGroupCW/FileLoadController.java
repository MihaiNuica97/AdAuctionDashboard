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
	private SQLCreator sqlcreator = new SQLCreator();
	private DatabaseHandler dbHandler = new DatabaseHandler();

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
		if(isDBReady()){
			dashboardBtn.setDisable(false);
		}
		return file;
	}
	
	@FXML
	private void goToDashboard() throws IOException
	{
		System.out.println("Dashboard button clicked");
		 try {
		 	dbHandler.sendSQL(sqlcreator.createDB());
		 	loadFilesToDB();
		 } catch (SQLException e) {
		  System.out.println("Database not created");
	 		e.printStackTrace();
		 }
		App.setRoot("dashboard");
//		App.getScene().getWindow().setHeight(925);
//		App.getScene().getWindow().setWidth(1000);
	}
	private Boolean isDBReady(){
		return true;
	}

	@FXML
	private void test(){
		App.themeController.changeTheme("dark.css");
	}

	private void loadFilesToDB() throws IOException, SQLException {
		dbHandler.sendSQL(csvParser.parseImpression(fileMap.get("Impressions")));
		dbHandler.sendSQL(csvParser.parseClicks(fileMap.get("Clicks Log")));
		dbHandler.sendSQL(csvParser.parseServer(fileMap.get("Server Log")));
	}


}