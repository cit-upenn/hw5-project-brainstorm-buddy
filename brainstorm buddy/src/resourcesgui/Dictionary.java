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
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.dsig.XMLObject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.*;

import com.alchemyapi.api.AlchemyAPI;

public class Dictionary {
	private String file_name;
	HashMap<String, Double> keywordsAndRelevance = new HashMap<String, Double>();
	ArrayList<String> kw = new ArrayList<String>();
	ArrayList<Double> re = new ArrayList<Double>();
	ArrayList<String> de = new ArrayList<String>();
	HashMap<String, String> def = new HashMap<String, String>();
	private MyFileReader f;
	private ArrayList<String> dictionary;
	private String stringLines;

	
	public Dictionary(String file_name) throws Exception {
		getKeywords();
		getDefinitions();
	}
	
	public void getKeywords() throws IOException, SAXException,
    ParserConfigurationException, XPathExpressionException {
		
	AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
	String txtDoc = getFileContents("test.txt");
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
    	   getKeywordDefinition(kw.get(i));
       }
	}
	
	private String getKeywordDefinition(String word) throws Exception {
		String fixed = word.replace(' ', '_');
		fixed = "sad";
		URL myUrl = new URL("http://www.dictionaryapi.com/api/v1/references/collegiate/xml/"+ fixed + "?key=720ae1e5-6d26-4216-b0a7-7e4a9dd1a53b");
        URLConnection myConnection = myUrl.openConnection();
        
        //XMLObject = xml;
        String results;
        BufferedReader in = new BufferedReader(
            	new InputStreamReader(
                myConnection.getInputStream()));
        
        while ((results = in.readLine()) != null) {
        	
        	de.add(results);
        }
        String def = dictionaryPatternMatcher(word, de.get(2));
        
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
