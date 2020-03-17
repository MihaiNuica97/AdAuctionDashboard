package segGroupCW;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ThemeController {
	private String currentTheme;
	private HashMap<String, String> themeURLs = new HashMap<>();
	private ArrayList<String> themeNames;

//	gets initialized when App is initialized
//	grabs all filenames from the themes folder and puts them in a HashMap along with their urls
	public ThemeController(String themeFolder){
	    currentTheme = "default.css";
	    URL folderURL = getClass().getResource(themeFolder);
		themeNames = new ArrayList<>();
//	    grab all files from theme folder
		File[] files = new File[0];
		try {
			files = new File(folderURL.toURI()).listFiles();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
//		generate themes HashMap
		for (File file: files){
	    	String filename = file.getName();
	    	String fileURL = getClass().getResource(themeFolder+filename).toExternalForm();
	    	themeURLs.put(filename,fileURL);
	    	themeNames.add(filename);
		}
	    this.applyTheme(currentTheme);
	}

//	updates current theme and applies the new one
	public void changeTheme(String newTheme){
		this.currentTheme = newTheme;
		applyTheme(newTheme);
	}

	private void applyTheme(String newTheme){
		App.scene.getStylesheets().add(this.themeURLs.get(newTheme));
	}

	public String getCurrentTheme()
	{
		return currentTheme;
	}

	public ArrayList<String> getThemeNames(){
		return themeNames;
	}




}
