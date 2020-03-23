package segGroupCW;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ThemeController {
	private String currentTheme;
	private HashMap<String, String> themeURLs = new HashMap<>();
	private ArrayList<String> themeNames;

	//	gets initialized when App is initialized
//	grabs all filenames from the themes folder and puts them in a HashMap along with their urls
	public ThemeController(String themeFolder) {
		currentTheme = "default.css";
		getFilenamesFromFolder(themeFolder);
		themeNames = new ArrayList<>();
//	    grab all files from theme folder

		ArrayList<String> fileNames = getFilenamesFromFolder(themeFolder);
		for(String name: fileNames){
			String resource = getClass().getResource(themeFolder+name).toExternalForm();
			themeURLs.put(name, resource);
			themeNames.add(name);
		}

		this.applyTheme(currentTheme);
	}


	//	updates current theme and applies the new one
	public void changeTheme(String newTheme) {
		App.scene.getStylesheets().remove(this.getCurrentThemeUrl());
		this.currentTheme = newTheme;
		applyTheme(newTheme);
	}

	private void applyTheme(String newTheme) {
		App.scene.getStylesheets().add(this.themeURLs.get(newTheme));
	}

	public String getCurrentTheme() {
		return currentTheme;
	}

	public String getCurrentThemeUrl() {
		return themeURLs.get(currentTheme);
	}

	public String getThemeURL(String themeName) {
		return themeURLs.get(themeName);
	}

	public ArrayList<String> getThemeNames() {
		return themeNames;
	}

	private ArrayList<String> getFilenamesFromFolder(String folderName) {
		ArrayList<String> fileNames = new ArrayList<>();
		try {
			InputStream stream = getClass().getResourceAsStream(folderName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			String l;
			while ((l = reader.readLine()) != null) {
				fileNames.add(l);
			}
			stream.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return fileNames;
	}
}
