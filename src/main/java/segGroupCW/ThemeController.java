package segGroupCW;

import javafx.fxml.FXMLLoader;

public class ThemeController {

	public static void addStyle(String style){
		App.getScene().getStylesheets().add(FXMLLoader.getDefaultClassLoader().getResource(style).toExternalForm());
	}

	public static void getStyles(){
		System.out.println(App.getScene().getStylesheets());
	}
}
