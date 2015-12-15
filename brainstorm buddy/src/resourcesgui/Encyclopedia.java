package resourcesgui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
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

/**
 * This is an Encyclopedia class
 * It pulls concepts from a file and then pulls encyclopedia links and encyclopedia previews
 * @author meredithmargulies
 *
 */
public class Encyclopedia {
	private ArrayList<String> kw = new ArrayList<String>();
	private String conceptOutput;
	private HashMap<String, ArrayList<String>> keywordsAndLinks = new HashMap<String, ArrayList<String>>();
	private boolean showPreview;
	String inputFile;
	
	/**
	 * This is a constructor for the encyclopedia class
	 * @param file_name this is the file to pull concepts from
	 * @param showPreviews this is a boolean to say if previews were selected or not
	 * @throws IOException
	 * @throws Exception
	 */
	public Encyclopedia(String file_name, boolean showPreviews) throws IOException, Exception {
		inputFile = file_name;
		showPreview = showPreviews;
		getConcepts();
		getLinks(conceptOutput);
		
	}
	/**
	 * This is a getter for the HashMap of concepts and links
	 * @return the hashmap
	 */
	public HashMap<String, ArrayList<String>> getEncyclopediaHashMap() {
		return keywordsAndLinks;
	}
	/**
	 * This method pulls concepts from the AlchemyAPI
	 * @throws Exception
	 * @throws IOException
	 */
	public void getConcepts() throws Exception, IOException{
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
		String txtDoc = getFileContents(inputFile);
		Document doc = alchemyObj.TextGetRankedConcepts(txtDoc);
		conceptOutput = getStringFromDocument(doc);
		keywordPatternMatcher(conceptOutput);
	}
	
	/**
	 * This method gets the previews from the url
	 * @param url this will be a dpPedia URL
	 * @return
	 * @throws IOException
	 */
	public String getPreview(String url) throws IOException {
			JSoup j = new JSoup(url);
			String search = j.getContents();
			
			
			return previewMatcher(search);
			

	}
	
	/**
	 * This method matches the needed content from the output of dpPedia to pull the abstract
	 * @param output this is the output from JSoup
	 * @return the preview from the entry
	 */
	public String previewMatcher(String output) {
		Pattern pattern = Pattern.compile("<li><span class=\"literal\"><span property=\"dbo:abstract\" xmlns:dbo=\"http://dbpedia.org/ontology/\" xml:lang=\"en\">(.*)</span></span></li>");

		Matcher match = pattern.matcher(output);

		if(match.find()) {
			return "Preview: " + match.group(1);
		} else {
			return "Preview: No Preview Available";
		}
	}
	
	/**
	 * This method gets the links related to each concept from alchemy output
	 * @param searchString
	 * @throws IOException
	 */
	public void getLinks(String searchString) throws IOException {
		String[] eo = searchString.split("<concept>");

		for(int i=1; i<	eo.length; i++){
			ArrayList<String> myLinks = linkPatternMatcher(eo[i]);
			if(showPreview) {
			if(!myLinks.get(0).equals("There are no links for this")) {
			for(int k=0; k<myLinks.size();k++) {
				if (myLinks.get(k).contains("dbpedia")) {
					myLinks.add(getPreview(myLinks.get(k)));
				} 
			}
			} else {
				myLinks.add("No Preview Available");
			}
			}
			keywordsAndLinks.put(kw.get(i-1), myLinks);
			
		}
	}
	
	/**
	 * This method matches the links from the output from each concept
	 * @param output the alchemy content associated with each concept
	 * @return
	 */
	public ArrayList<String> linkPatternMatcher(String output) {
		ArrayList<String> links = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile("(http://.*)</");

		Matcher match = pattern.matcher(output);

		while(match.find()) {
			links.add(match.group(1));
		}
		
		if(links.isEmpty()) {
			links.add("There are no links for this");
		}
		
		return links;
	}
	
	/**
	 * This matches the concepts from the alchemy output
	 * @param searchString the output from alchemy
	 */
	private void keywordPatternMatcher(String searchString) {
		Pattern pattern = Pattern.compile("<text>(.*)</text>");

		Matcher match = pattern.matcher(searchString);

		while(match.find()) {
			kw.add(match.group(1));
		}
		
	}
	
    // utility function, from a tester class in the Alchemy API
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

    // utility method, from a tester class given with the Alchemy API
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
