package segGroupCW;

import javafx.fxml.FXMLLoader;

import java.util.ArrayList;

public class ThemeController {
	private String mainTheme;
	private ArrayList<String> themes;
	
	public ThemeController(){
	    mainTheme = "css/themes/default.css";
//	    private File[] files = new File()
	}
	public String getMainTheme()
	{
		return mainTheme;
	}
	
	public static void addTheme(String style){
		App.getScene().getStylesheets().add(FXMLLoader.getDefaultClassLoader().getResource(style).toExternalForm());
	}

	public static void getStyles(){
		System.out.println(App.getScene().getStylesheets());
	}
}
