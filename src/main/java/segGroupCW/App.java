package segGroupCW;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {


	public static Scene scene;
	public static ThemeController themeController;
	public static Boolean packaged = false;
	public static DataHandler dataHandler;
	public static Stage thisStage;
	public static String currentGraph;
	public static String bounceDef = "Page";
	public static Double bounceValue = 1.0;

	public static Scene getScene(){
		return scene;
	}
	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML("fileUpload"));
		if(packaged) {
			themeController = new ThemeController(File.separator+"css" + File.separator + "themes" + File.separator);
		}else{
			themeController = new ThemeController("css/themes/");
		}
		thisStage = stage;
		stage.setScene(scene);
		stage.show();
		stage.setHeight(500);
//		stage.setWidth(600);
	}

	public static void switchToGraphView(GraphOptions options, String name) throws IOException {
		currentGraph = name;
		FXMLLoader loader = new FXMLLoader(App.class.getResource("graphView.fxml"));
		scene.setRoot(loader.load());
		GraphController controller = loader.getController();
		controller.setGraphsOptions(options);
		thisStage.sizeToScene();
		thisStage.setResizable(false);
	}

	public static void changeTheme(String newTheme){
		themeController.changeTheme(newTheme);
	}

 	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
		thisStage.sizeToScene();
		thisStage.setResizable(false);

	}
	
	
	
	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}

}