package resourcesgui;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class DictionaryTester {

		private Dictionary d;
		DOMParser parser = null;
		Document document = null;
		
		@Before
		public void setUp() throws Exception {
			d =  new Dictionary("test.txt");
			parser = new DOMParser();
		}
		
		@Test
		public void testEmptyStringforMatcher() {
			ArrayList<String> test = d.keywordPatternMatcher("");
			assertNotNull(test);
		}
		
		@Test
		public void testMatchingStringOnMatcher() {
			ArrayList<String> test = d.keywordPatternMatcher("<text>This_is_a_test</text>");
			assertEquals("This_is_a_test", test.get(0));
		}
		
		@Test
		public void testMatchingMultipleString() {
			ArrayList<String> test = d.keywordPatternMatcher("<text>This_is_a_test</text>\n<text>my_word</text>");
			assertEquals("This_is_a_test", test.get(0));
			assertEquals("my_word", test.get(1));
		}
		
		@Test
		public void testValidXML() {
			try {
				parser.parse("testValidXML.txt");
				document = parser.getDocument();
				String test = d.getKeywordDefinition(document);
				assertEquals("a fleshy fruit", test);
			} catch (SAXException e) {
				fail("SAXException: " + e);
			} catch (IOException e) {
				fail("IOException: " + e);
			} catch (Exception e) {
				fail("Exception: " + e);
			}
		}
		@Test
		public void testNoDefinitionXML() {
			try {
				parser.parse("testNoDefinitionXML.txt");
				document = parser.getDocument();
				String test = d.getKeywordDefinition(document);
				assertEquals("There is no definition for this keyword", test);
			} catch (SAXException e) {
				fail("SAXException: " + e);
			} catch (IOException e) {
				fail("IOException: " + e);
			} catch (Exception e) {
				fail("Exception: " + e);
			}
		}
}


