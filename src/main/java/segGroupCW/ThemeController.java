package segGroupCW;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ThemeController {
	private String currentTheme;
	private HashMap<String, String> themeURLs = new HashMap<>();



	private HashMap<String,String> displayNames = new HashMap<>();
	private ArrayList<String> themeNames;
	private Boolean packaged;

	//	gets initialized when App is initialized
//	grabs all filenames from the themes folder and puts them in a HashMap along with their urls
	public ThemeController(String themeFolder) {
		this.packaged = App.packaged;
		currentTheme = "default.css";
		themeNames = new ArrayList<>();
//	    grab all files from theme folder
		if(!packaged) {
			ArrayList<String> fileNames = getFilenamesFromFolder(themeFolder);
			for(String name: fileNames){
				String resource = getClass().getResource(themeFolder+name).toExternalForm();
				themeURLs.put(name, resource);
				themeNames.add(name);
			}
		}
		else{
			File[] fileList = getFilenamesFromSystem(themeFolder);
			for(File file:fileList){
				String filename = file.getName();
				themeURLs.put(filename, file.toURI().toString());
//				themeURLs.put(filename, "file:///" + file.getAbsolutePath().replace("\\", "/").replace(" ","%"));
				themeNames.add(filename);
			}
		}
		for(String name : themeNames){
			System.out.println(name);
			switch (name){
				case "default.css":
				{
					displayNames.put(name, "Default");
					break;
				}
				case "dark.css":
				{
					displayNames.put(name, "Night Theme");
					break;
				}
				case "dyslexic-friendly.css":
				{
					displayNames.put(name, "Dyslexic friendly");
					break;
				}case "colourblind-friendly.css":
				{
					displayNames.put(name, "Colourblind friendly");
					break;
				}
			}
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
	private File[] getFilenamesFromSystem(String folderName){
		File[] fileNames;
		File aux = new File("");
		String folderPath = aux.getAbsolutePath()+folderName;
		aux = new File(folderPath);
		fileNames = aux.listFiles();
		System.out.println(Arrays.toString(aux.listFiles()));

		return fileNames;
	}
	public HashMap<String, String> getDisplayNames() {
		return displayNames;
	}
}
