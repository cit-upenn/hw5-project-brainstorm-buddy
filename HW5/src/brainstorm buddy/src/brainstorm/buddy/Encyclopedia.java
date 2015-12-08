package brainstorm.buddy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import com.alchemyapi.api.AlchemyAPI;

public class Encyclopedia {
	private ArrayList<Double> re = new ArrayList<Double>();
	private ArrayList<String> kw = new ArrayList<String>();
	private String entityOutput;
	private HashMap<String, ArrayList<String>> keywordsAndLinks = new HashMap<String, ArrayList<String>>();
	private boolean showPreview;
	private ArrayList<String> previews = new ArrayList<String>();

	
	public Encyclopedia(String file_name, boolean showPreviews) throws IOException, Exception {
		showPreview = showPreviews;
		getEntities();
		getLinks(entityOutput);
		PrintWriter writer = new PrintWriter("results.txt");
		for(int i = 0; i<kw.size(); i++) {
			writer.println("\n");
			writer.println("Keyword: " + kw.get(i));
			writer.println("Links: ");
			ArrayList<String> link = keywordsAndLinks.get(kw.get(i));
			for(String j: link) {
				writer.println(j);
			}
			if(showPreview) {
			writer.println("Preview:");
			writer.println(previews.get(i));
			}
		}
		writer.close();
		
	}
	
	public void getEntities() throws Exception, IOException{
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
		String txtDoc = getFileContents("test.txt");
		Document doc = alchemyObj.TextGetRankedNamedEntities(txtDoc);
		entityOutput = getStringFromDocument(doc);
		//System.out.println(entityOutput);
		keywordPatternMatcher(entityOutput);
	}
	
	public String getPreview(String url) throws IOException {
			JSoup j = new JSoup(url);
			String search = j.getContents();
			
			
			return previewMatcher(search);
			

	}
	
	private String previewMatcher(String output) {
		//Pattern pattern = Pattern.compile("<p>(.*)</p>");
		Pattern pattern = Pattern.compile("<li><span class=\"literal\"><span property=\"dbo:abstract\" xmlns:dbo=\"http://dbpedia.org/ontology/\" xml:lang=\"en\">(.*)</span></span></li>");

		Matcher match = pattern.matcher(output);

		if(match.find()) {
			return match.group(1);
		} else {
			return "No Preview Available";
		}
	}
	
	
	public void getLinks(String searchString) throws IOException {
		String[] eo = searchString.split("<entity>");

		for(int i=1; i<	eo.length; i++){
			ArrayList<String> myLinks = linkPatternMatcher(eo[i]);
			if(!myLinks.get(0).equals("There are no links for this")) {
			for(int k=0; k<myLinks.size();k++) {
				if (myLinks.get(k).contains("dbpedia")) {
					previews.add(getPreview(myLinks.get(k)));
				} 
			}
			} else {
				previews.add("No Preview Available");
			}
			keywordsAndLinks.put(kw.get(i-1), myLinks);
			
		}
	}
	
	private ArrayList<String> linkPatternMatcher(String output) {
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
	
	private void keywordPatternMatcher(String searchString) {
		Pattern pattern = Pattern.compile("<text>(.*)</text>");

		Matcher match = pattern.matcher(searchString);

		while(match.find()) {
			kw.add(match.group(1));
		}
		
		//for (int i=0; i < kw.size(); i++) {
			//System.out.println(kw.get(i));
		//}
		
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