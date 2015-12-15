package resourcesgui;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * this is clas JSoup that uses JSoup to scrape HTML
 * had to be created because I had to create a different Document
 * @author meredithmargulies
 *
 */
public class JSoup {
	//instance variables
	private String myUrl;
	
	/**
	 * This is a constructor for the class JSoup
	 * @param url the url to scrape from
	 */
	public JSoup(String url) {
		myUrl=url;
	}
	
	/**
	 * This returns the contents in the form of a string
	 * @return
	 */
	public String getContents() {
		Document doc;
		try {
			doc = Jsoup.connect(myUrl).get();
			String contents = doc.toString();
			return contents;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("fail");
			return "Error in search";
		}
	}
}
