package resourcesgui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

/**
 * This is the class Dictionary
 * It pulls keywords from the document and gets definitions from Merriam Webster
 * @author meredithmargulies
 *
 */
public class Dictionary {
	//instance variables
	private String fileName;
	private ArrayList<String> kw = new ArrayList<String>();
	private ArrayList<String> de = new ArrayList<String>();
	private HashMap<String, String> hashMapdefinitions = new HashMap<String, String>();
	
	/**
	 * This is a constructor for the Dictionary class
	 * @param file this is the file to pull keywords from
	 * @throws Exception
	 */
	public Dictionary(String file) throws Exception {
		fileName = file;
		getKeywords();
		getDefinitions();
		//creates the hash map
		for (int i=0;i<kw.size(); i++) {
		hashMapdefinitions.put(kw.get(i), de.get(i));
		}
	}
	
	/**
	 * This is a getter for the DictionaryHashMap
	 * @return the hash map of keywords and definitions
	 */
	public HashMap<String, String> getDictionaryHashMap() {
		return hashMapdefinitions;
	}
	
	/**
	 * This method pulls keywords from the alchemy API
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 */
	public void getKeywords() throws IOException, SAXException,
    ParserConfigurationException, XPathExpressionException {
		
	AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
	String txtDoc = getFileContents(fileName);
	Document doc = alchemyObj.TextGetRankedKeywords(txtDoc);
	String keywordOutput = getStringFromDocument(doc);
	kw = keywordPatternMatcher(keywordOutput);
    
	}
	
	/**
	 * This method gets each word's definition
	 * @throws Exception
	 */
	public void getDefinitions() throws Exception{
       for(int i =0; i<kw.size(); i++) {
    	   Document xml = callDictionaryAPI(kw.get(i));
    	   de.add(getKeywordDefinition(xml));
    	   
       }
	}
	
	/**
	 * This method calls the MW API for each word
	 * @param word this is the word to get the definition of
	 * @return the XML output from the API
	 */
	private Document callDictionaryAPI(String word) {
		String fixed = word.replace(' ', '_');
		try {
		URL myUrl = new URL("http://www.dictionaryapi.com/api/v1/references/collegiate/xml/"+ fixed + "?key=720ae1e5-6d26-4216-b0a7-7e4a9dd1a53b");
        URLConnection myConnection = myUrl.openConnection();
        
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(myConnection.getInputStream());
                return doc;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * This parses the xml for the definition
	 * @param xmlDoc this is the output from the API
	 * @return the definition of the word
	 * @throws Exception
	 */
	public String getKeywordDefinition(Document xmlDoc) throws Exception {
                Document doc = xmlDoc;
				doc.getDocumentElement().normalize();
                String def;
                try {
                NodeList defs = doc.getElementsByTagName("dt");
									
				Element titleElem = (Element) defs.item(0);
				String meaning = " ";
				for (int k = 0; k < titleElem.getChildNodes().getLength(); k++){
					Node titleNode = titleElem.getChildNodes().item(k);
					meaning = meaning + titleNode.getTextContent();
				}
				def = meaning.split(":")[1];
                } catch (Exception e) {
                	def = "There is no definition for this keyword";
                }
                
        return def;
        
	}
	
	/**
	 * This method is a matcher to get each keyword from the alchemy output
	 * @param searchString this is the string of output
	 * @return the word
	 */
	public ArrayList<String> keywordPatternMatcher(String searchString) {
		ArrayList<String> keywords = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<text>(.*)</text>");

		Matcher match = pattern.matcher(searchString);

		while(match.find()) {
			if(!match.group(1).contains(" ")) {
			keywords.add(match.group(1));
			}
		}
		
		return keywords;
		
	}
	
    // utility function, from API tester classes given with API
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

    // utility method, from Alchemy tester classes given with API
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
