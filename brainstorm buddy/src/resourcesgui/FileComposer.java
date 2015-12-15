package resourcesgui;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is class FileComposer
 * It outputs the file of all the outputs from each function
 * @author meredithmargulies
 *
 */
public class FileComposer {
	//instance variables
	private HashMap<String, Boolean> printedKeyWords = new HashMap<String, Boolean>();
	private HashMap<String, ArrayList<String>> encyclopediaHashMap;
	private HashMap<String, ArrayList<String>> newsSourcesHashMap;
	private HashMap<String, String> dictionaryHashMap;
	private PrintWriter writer = new PrintWriter("results.txt");
	
	/**
	 * This is the FileComposer constructor
	 * @param encyclopediaHashMap the HashMap from the Encyclopedia
	 * @param newsSourcesHashMap the HashMap from the NewsSources
	 * @param dictionaryHashMap the HashMap from the Dictionary
	 * @throws FileNotFoundException
	 */
	public FileComposer (HashMap<String, ArrayList<String>> encyclopediaHashMap, HashMap<String, ArrayList<String>> newsSourcesHashMap, HashMap<String, String> dictionaryHashMap) throws FileNotFoundException {
		this.encyclopediaHashMap = encyclopediaHashMap;
		this.newsSourcesHashMap = newsSourcesHashMap;
		this.dictionaryHashMap = dictionaryHashMap;
		printEncyclopediaWords();
		printNewsSourcesWords();
		printDictionaryWords();
		writer.close();
	}
	
	/**
	 * This method prints all related outputs for keywords in Encyclopedia
	 */
	private void printEncyclopediaWords() {
		if(!encyclopediaHashMap.isEmpty()) {
			String[] words = encyclopediaHashMap.keySet().toArray(new String[encyclopediaHashMap.keySet().size()]);
			for(String i: words) {
				printedKeyWords.put(i, true);
				writer.println();
				writer.println("Keyword: "+ i);
				writer.println("Encyclopedia Links: ");
				ArrayList<String> links = encyclopediaHashMap.get(i);
				for(String j: links) {
					writer.println(j);
				}
				if(!newsSourcesHashMap.isEmpty()) {
					if(newsSourcesHashMap.containsKey(i)) {
						writer.println("News Links:");
						ArrayList<String> newsLinks = newsSourcesHashMap.get(i);
						for(String k: newsLinks) {
							writer.println(k);
						}
					}
				}
				if(!dictionaryHashMap.isEmpty()) {
					if(dictionaryHashMap.containsKey(i)) {
						writer.println("Definition: ");
						writer.println(dictionaryHashMap.get(i));
					}
				}
			}
		}
	}
	
	/**
	 * This method prints all related outputs for keywords in NewsSources
	 */
	private void printNewsSourcesWords() {
		if(!newsSourcesHashMap.isEmpty()) {
			String[] words = newsSourcesHashMap.keySet().toArray(new String[newsSourcesHashMap.keySet().size()]);
			for(String i: words) {
				if(!printedKeyWords.containsKey(i)) {
					printedKeyWords.put(i, true);
					writer.println();
					writer.println("Keyword: "+ i);
					if(encyclopediaHashMap.containsKey(i)) {
						writer.println("Encyclopedia Links: ");
						ArrayList<String> links = encyclopediaHashMap.get(i);
						for(String j: links) {
							writer.println(j);
						}
					}
					writer.println("News Links:");
					ArrayList<String> newsLinks = newsSourcesHashMap.get(i);
					for(String k: newsLinks) {
						writer.println(k);
					}


					if(!dictionaryHashMap.isEmpty()) {
						if(dictionaryHashMap.containsKey(i)) {
							writer.println("Definition: ");
							writer.println(dictionaryHashMap.get(i));
						}
					}
				}

			}
		}
	}
	
	/**
	 * This method prints all related outputs for keywords in Dictionary
	 */
	private void printDictionaryWords() {
		if(!dictionaryHashMap.isEmpty()) {
			String[] words = dictionaryHashMap.keySet().toArray(new String[dictionaryHashMap.keySet().size()]);
			for(String i: words) {
				if(!printedKeyWords.containsKey(i)) {
					printedKeyWords.put(i, true);
					writer.println();
					writer.println("Keyword: "+ i);
					if(encyclopediaHashMap.containsKey(i)) {
						writer.println("Encyclopedia Links: ");
						ArrayList<String> links = encyclopediaHashMap.get(i);
						for(String j: links) {
							writer.println(j);
						}
					}
					if(!newsSourcesHashMap.isEmpty()) {
						if(newsSourcesHashMap.containsKey(i)) {
							writer.println("News Links:");
							ArrayList<String> newsLinks = newsSourcesHashMap.get(i);
							for(String k: newsLinks) {
								writer.println(k);
							}
						}
					}

					writer.println("Definition: ");
					writer.println(dictionaryHashMap.get(i));


				}

			}
		}
	}
}
