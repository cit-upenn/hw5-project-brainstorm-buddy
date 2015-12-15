package resourcesgui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * This the BrainStormBuddy Choices Class
 * It is the main connection between the GUI and the backend.
 * @author meredithmargulies and gabecolton
 *
 */
public class BrainStormBuddyChoices {
	//instance variables
	private boolean dictionary	;
	private boolean newsSources;
	private boolean newYorkTimes = true;
	private boolean jstor;
	private boolean encyclopedia;
	private boolean preview;
	private boolean links;
	/**
	 * This is the constructor for the class BrainStormBuddyChoices
	 */
	BrainStormBuddyChoices() {
		
	}
	
	/**
	 * This method creates the needed classes and then prints the outputs to a file
	 * @param fileName this is the file to be read
	 */
	public void gatherResources(String fileName) {
		HashMap<String,String> dict = new HashMap<String,String>();
		//creates dictionary
		if(dictionary) {
			try {
				Dictionary d = new Dictionary(fileName);
				dict = d.getDictionaryHashMap();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		HashMap<String, ArrayList<String>> ency = new HashMap<String, ArrayList<String>>();
		//creates encyclopedia
		if(encyclopedia) {
			try {
				Encyclopedia e = new Encyclopedia(fileName, preview);
				ency = e.getEncyclopediaHashMap();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		HashMap<String, ArrayList<String>> news = new HashMap<String, ArrayList<String>>();
		//creates news
		if(newsSources) {
			try {
				NewsSources ns = new NewsSources(fileName, newYorkTimes, jstor);
				news = ns.getNewsSourcesHashMap();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// creates the fileComposer
		try {
			FileComposer fc = new FileComposer(ency, news, dict);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * setter for the dictionary
	 * @param choice true or false to create
	 */
	public void setDictionary(boolean choice) {
		dictionary = choice;
	}
	/**
	 * setter for the newsSources
	 * @param choice true or false to create
	 */
	public void setNewsSources(boolean choice) {
		newsSources = choice;
	}
	
	/**
	 * setter for newYorkTimes
	 * @param choice true or false use
	 */
	public void setNewYorktimes(boolean choice) {
		newYorkTimes = choice;
	}
	
	/**
	 * setter for Jstor
	 * @param choice true or false to use
	 */
	public void setJstor(boolean choice) {
		jstor = choice;
	}
	
	/**
	 * setter for Encyclopedia
	 * @param choice
	 */
	public void setEncyclopedia(boolean choice) {
		encyclopedia = choice;
	}
	
	/**
	 * setter for preview
	 * @param choice true or false to create
	 */
	public void setPreview(boolean choice) {
		preview = choice;
	}
	
	/**
	 * setter for links
	 * @param choice true or false to create
	 */
	public void setLinks(boolean choice) {
		links= choice;
	}
}
