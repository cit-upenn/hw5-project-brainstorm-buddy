package resourcesgui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.dsig.XMLObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.*;

import com.alchemyapi.api.AlchemyAPI;

public class Dictionary {
	private String fileName;
	HashMap<String, Double> keywordsAndRelevance = new HashMap<String, Double>();
	ArrayList<String> kw = new ArrayList<String>();
	ArrayList<Double> re = new ArrayList<Double>();
	ArrayList<String> de = new ArrayList<String>();
	HashMap<String, String> hashMapdefinitions = new HashMap<String, String>();
	private MyFileReader f;
	private ArrayList<String> dictionary;
	private String stringLines;

	
	public Dictionary(String file) throws Exception {
		fileName = file;
		getKeywords();
		getDefinitions();
		for (int i=0;i<kw.size(); i++) {
		hashMapdefinitions.put(kw.get(i), de.get(i));
		}
	}
	
	public HashMap<String, String> getDictionaryHashMap() {
		return hashMapdefinitions;
	}
	
	
	public void getKeywords() throws IOException, SAXException,
    ParserConfigurationException, XPathExpressionException {
		
	AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
	String txtDoc = getFileContents(fileName);
	Document doc = alchemyObj.TextGetRankedKeywords(txtDoc);
	String keywordOutput = getStringFromDocument(doc);
	keywordPatternMatcher(keywordOutput);
	for(int i = 0; i < kw.size(); i++) {
		keywordsAndRelevance.put(kw.get(i), re.get(i));
	}
    //System.out.println(keywordOutput);
    
	}
	
	public void getDefinitions() throws Exception{
       for(int i =0; i<kw.size(); i++) {
    	   de.add(getKeywordDefinition(kw.get(i)));
    	   
       }
	}
	
	private String getKeywordDefinition(String word) throws Exception {
		String fixed = word.replace(' ', '_');
		//fixed = "mus";
		URL myUrl = new URL("http://www.dictionaryapi.com/api/v1/references/collegiate/xml/"+ fixed + "?key=720ae1e5-6d26-4216-b0a7-7e4a9dd1a53b");
        URLConnection myConnection = myUrl.openConnection();
        
        //XMLObject = xml;
        //String results;
        //BufferedReader in = new BufferedReader(
            	//new InputStreamReader(
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(myConnection.getInputStream());
                doc.getDocumentElement().normalize();
                String def;
                try {
                NodeList defs = doc.getElementsByTagName("dt");
                Node dt = defs.item(0);
									
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
                
    			/*
    			NodeList items = doc.getElementsByTagName("entry");
    			for (int i = 0; i &lt; items.getLength(); i++)
    			{
    				Node n = items.item(i);
     
    				if (n.getNodeType() != Node.ELEMENT_NODE)
    					continue;
     
     
    				Element e = (Element) n;
     
    				//Check if the entry word is equal to the given word
    				NodeList chkList = e.getElementsByTagName("ew");
    				Element chkElem = (Element) chkList.item(0);
    				Node chkNode = chkElem.getChildNodes().item(0);
    				System.out.println(chkNode.getNodeValue());
    				//System.out.println(word);
     
    				NodeList titleList = e.getElementsByTagName("dt");
    				for (int j = 0; j &lt; titleList.getLength(); j++){
    					Node dt = titleList.item(j);
    					if (dt.getNodeType() != Node.ELEMENT_NODE)
    						continue;					
    					Element titleElem = (Element) titleList.item(j);
    					meaning = " ";
    					for (int k = 0; k &lt; titleElem.getChildNodes().getLength(); k++){
    						Node titleNode = titleElem.getChildNodes().item(k);
    						meaning = meaning.trim() + titleNode.getTextContent();
    					}
    					System.out.println(meaning);
    				}
    			}
    			*/
     
                
               
        
        //while ((results = in.readLine()) != null) {
        	
        	//de.add(results);
        //}
        //String def = dictionaryPatternMatcher(word, de.get(2));
        return def;
        
	}
	/*
	private void getKeywordDefinition(String word) {
		dictionaryPatternMatcher(word);
		
	}
	*/
	private String dictionaryPatternMatcher(String word, String stringMatch) {
		
		Pattern pattern = Pattern.compile(":(.*)</dt>");
		

		Matcher match = pattern.matcher(stringMatch);
		ArrayList<String> wordDefs = new ArrayList<String>();
		String definition;

		if(match.find()) {
			definition = match.group(1);
			String[] def = definition.split("</dt>");
			if(definition.contains(":")){
				def = definition.split(":");
			}
			definition = def[0];
			System.out.println(definition);
			return definition;
		}
		
		return "";
		
	
		/*
		ArrayList<String> wordDefs = new ArrayList<String>();
		String myWord = searchString.substring(12, word.length()+11);
		System.out.println(myWord + "s");
		if(searchString.startsWith("<")) {
			System.out.println(searchString + "problem first");
			return null;
		}	
		else if(!(word.contains(myWord))) {
			System.out.println(searchString + "problem second");
			return null;
		}else if(!(searchString.matches("(.*)<fl>noun</fl(.*)"))){
			System.out.println(searchString + "problem 3");
			return null;
		} else {
		Pattern pattern = Pattern.compile("<dt>(.*)</dt>");

		Matcher match = pattern.matcher(searchString);
		
		while(match.find()) {
			wordDefs.add(match.group(1));
		}
		
		return wordDefs;
		}
		*/
	}
	private void keywordPatternMatcher(String searchString) {
		Pattern pattern = Pattern.compile("<text>(.*)</text>");

		Matcher match = pattern.matcher(searchString);

		while(match.find()) {
			kw.add(match.group(1));
		}
		
		for (int i=0; i < kw.size(); i++) {
			System.out.println(kw.get(i));
		}
		
		pattern = Pattern.compile("<relevance>(.*)</relevance>");
		
		match = pattern.matcher(searchString);
		
		while(match.find()) {
			Double rel = Double.parseDouble(match.group(1));
			re.add(rel);
		}
		
		for (int i=0; i < re.size(); i++) {
			System.out.println(re.get(i));
		}
	}
	
    // utility function
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

    // utility method
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
