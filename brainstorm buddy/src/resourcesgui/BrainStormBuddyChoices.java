package resourcesgui;

import java.io.IOException;

public class BrainStormBuddyChoices {
	
	private boolean dictionary	;
	private boolean newsSources;
	private boolean cnn;
	private boolean googleScholar;
	private boolean encyclopedia;
	private boolean preview;
	private boolean links;
	
	BrainStormBuddyChoices(String fileName) {
		if(dictionary) {
			try {
				Dictionary d = new Dictionary(fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(encyclopedia) {
			try {
				Encyclopedia e = new Encyclopedia(fileName, preview);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void setDictionary(boolean choice) {
		dictionary = choice;
	}
	
	public void setNewsSources(boolean choice) {
		newsSources = choice;
	}
	
	public void setCnn(boolean choice) {
		cnn = choice;
	}
	
	public void setGoogleScholar(boolean choice) {
		googleScholar = choice;
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
