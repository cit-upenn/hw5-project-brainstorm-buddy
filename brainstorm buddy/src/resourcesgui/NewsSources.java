package resourcesgui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.alchemyapi.api.AlchemyAPI;

import org.json.*;

/**
 * This is the class NewsSources
 * It will find links to NYT articles or JSTOR articles
 * @author meredithmargulies
 *
 */
public class NewsSources {
	//instance variables
	private String inputFile;
	private String entityOutput;
	private ArrayList<String> kw = new ArrayList<String>();
	private HashMap<String, ArrayList<String>> kwlink = new HashMap<String, ArrayList<String>>();
	private boolean newYorkTimes;
	private boolean jstor;
	
	/**
	 * This is the constructor for the NewsSources class
	 * @param file_name this is the file to pull entities from
	 * @param newYorkTimes this is a boolean for if NewYorkTimes was selected
	 * @param jstor this is a boolean for if JSTOR was selected
	 * @throws IOException
	 * @throws Exception
	 */
	public NewsSources(String file_name, boolean newYorkTimes, boolean jstor) throws IOException, Exception {
		inputFile = file_name;
		getEntities();
		this.newYorkTimes = newYorkTimes;
		this.jstor = jstor;
		getAllNewsStories();
		
	}
	
	/**
	 * This is a getter for the NewsSources HashMap
	 * @return the hash map of keywords and links to articles
	 */
	public HashMap<String, ArrayList<String>> getNewsSourcesHashMap() {
		return kwlink;
	}
	
	/**
	 * This pulls entities from the file from the Alchemy API
	 * @throws Exception
	 * @throws IOException
	 */
	public void getEntities() throws Exception, IOException{
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
		String txtDoc = getFileContents(inputFile);
		Document doc = alchemyObj.TextGetRankedNamedEntities(txtDoc);
		entityOutput = getStringFromDocument(doc);
		//System.out.println(entityOutput);
		keywordPatternMatcher(entityOutput);
	}
	
	/**
	 * This method puts all words and news stories into the hash map
	 * @throws IOException
	 */
	public void getAllNewsStories() throws IOException {
		for (String s: kw) {
			if(newYorkTimes) {
				kwlink.put(s, getNYTNewsStories(s));
			} else if(jstor) {
				kwlink.put(s, getJstorNewsStories(s));
			}
		}
	}
	
	/**
	 * This method gets the most relevant links from a JSTOR search for an entity
	 * @param word the word to search
	 * @return the links to articles
	 * @throws IOException
	 */
	public ArrayList<String> getJstorNewsStories(String word) throws IOException {
		ArrayList<String> allWordLinks = new ArrayList<String>();
		String searchTerm = word.replace(' ', '+');
		JSoup j = new JSoup("http://dfr.jstor.org/?view=text&qk0=ft&qw0=1.0&qv0="+searchTerm+"&qf0=any");
		String contents = j.getContents();
		allWordLinks = linkMatcher(contents);
		return allWordLinks;
	}
	
	/**
	 * This method matches the links in the output of the html scrape of JSTOR
	 * @param siteContents the string of site contents
	 * @return the article links
	 */
	public ArrayList<String> linkMatcher(String siteContents) {
			ArrayList<String> articleLinks = new ArrayList<String>();
			Pattern pattern = Pattern.compile("href=\"(http://www.jstor.org/stable/.*)\"");

			Matcher match = pattern.matcher(siteContents);

			while(match.find()) {
				String myMatch = match.group(1);
				String[] matches = myMatch.split("\"");
				articleLinks.add(matches[0]);
			} 
			if(articleLinks.isEmpty()){
				articleLinks.add("No Articles Available");
			}
			
			return articleLinks;
		}
	
	/**
	 * This class parses JSON output for websites
	 * @param JSON the string of JSON
	 * @return the links for each word
	 */
	public ArrayList<String> parseOutput(String JSON) {
		ArrayList<String> wordLinks = new ArrayList<String>();
		JSONObject js = new JSONObject(JSON);
    	JSONArray links = js.getJSONObject("response").getJSONArray("docs");
    	for(int i = 0; i < links.length(); i++) {
    		JSONObject link = links.getJSONObject(i);
    		String linkText = link.getString("web_url");
    		wordLinks.add(linkText);
    	}
    	return wordLinks;
	}
	
	/**
	 * Calls the new york times API and calls helper method to get articles
	 * @param word the word to search the API
	 * @return the links associated with each word
	 */
	private ArrayList<String> getNYTNewsStories(String word) {
			ArrayList<String> allWordLinks = new ArrayList<String>();
			String searchTerm = word.replace(' ', '+');
			//String searchTerm = "barack+obama";
			try {
				URL myUrl = new URL("http://api.nytimes.com/svc/search/v2/articlesearch.json?q="+searchTerm+"&page=1&fl=web_url&api-key=109b5f88a3bef895d57ce3e7fcf1dfb8:2:73698227");
				URLConnection myConnection = myUrl.openConnection();
		        
		        String results;

		        BufferedReader in = new BufferedReader(
		                            	new InputStreamReader(
		                                myConnection.getInputStream()));
		        
		        while ((results = in.readLine()) != null) {
		        	allWordLinks = parseOutput(results);
		        }

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println();
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return allWordLinks;
	}
	
	/**
	 * The matcher to get all entities
	 * @param searchString the output of the alchemy API
	 */
	private void keywordPatternMatcher(String searchString) {
		Pattern pattern = Pattern.compile("<text>(.*)</text>");

		Matcher match = pattern.matcher(searchString);

		while(match.find()) {
			kw.add(match.group(1));
		}
		
	}
	
    // utility function, helper method from the Alchemy tester classes given
    private static String getFileContents(String filename)
        throws IOException, FileNotFoundException
    {
        File file = new File(filename);
        StringBuilder contents = new StringBuilder();

        BufferedReader input = new BufferedReader(new FileReader(file));

        try {
            String line = null;

            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } finally {
            input.close();
        }

        return contents.toString();
    }

    // utility method, helper method from the Alchemy tester classes given
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
