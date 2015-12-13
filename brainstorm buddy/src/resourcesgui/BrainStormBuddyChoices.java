package resourcesgui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BrainStormBuddyChoices {
	
	private boolean dictionary	;
	private boolean newsSources;
	private boolean newYorkTimes = true;
	private boolean jstor;
	private boolean encyclopedia;
	private boolean preview;
	private boolean links;
	
	BrainStormBuddyChoices() {
//		gatherResources(fileName);
		
	}

	public void gatherResources(String fileName) {
		HashMap<String,String> dict = null;
		if(dictionary) {
			try {
				Dictionary d = new Dictionary(fileName);
				dict = d.getDictionaryHashMap();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		HashMap<String, ArrayList<String>> ency = null;
		
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
		
		HashMap<String, ArrayList<String>> news = null;
		
		if(newsSources) {
			try {
				NewsSources ns = new NewsSources(fileName, newYorkTimes, jstor);
				news = ns.getNewsSourcesHashMap();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			FileComposer fc = new FileComposer(ency, news, dict);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setDictionary(boolean choice) {
		dictionary = choice;
	}
	
	public void setNewsSources(boolean choice) {
		newsSources = choice;
	}
	
	public void setNewYorktimes(boolean choice) {
		newYorkTimes = choice;
	}
	
	public void setJstor(boolean choice) {
		jstor = choice;
	}
	
	public void setEncyclopedia(boolean choice) {
		encyclopedia = choice;
	}
	
	public void setPreview(boolean choice) {
		preview = choice;
	}
	
	public void setLinks(boolean choice) {
		links= choice;
	}
}
