package resourcesgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
/**
 * This is a tester class for the Encyclopedia class
 * @author meredithmargulies
 *
 */
public class EncyclopediaTester {
	
	Encyclopedia e;
	
	@Before
	public void setUp() throws Exception {
		e =  new Encyclopedia("test.txt", true);
	}
	
	@Test
	public void testEmptyStringforLinkMatcher() {
		ArrayList<String> test = e.linkPatternMatcher("");
		assertNotNull(test);
	}
	
	@Test
	public void testMatchingStringOnLinkMatcher() {
		ArrayList<String> test = e.linkPatternMatcher("<link>http://www.google.com</link>");
		assertEquals("http://www.google.com", test.get(0));
	}
	
	@Test
	public void testLinkMatchingMultipleString() {
		ArrayList<String> test = e.linkPatternMatcher("<link>http://www.reddit.com</link>\n<link>http://www.facebook.com</link>");
		assertEquals("http://www.reddit.com", test.get(0));
		assertEquals("http://www.facebook.com", test.get(1));
	}
	
	@Test
	public void testMatchingStringOnPreviewMatcher() {
		String test = e.previewMatcher("<li><span class=\"literal\"><span property=\"dbo:abstract\" xmlns:dbo=\"http://dbpedia.org/ontology/\" xml:lang=\"en\">Brainstorm Buddy is the best product ever. We can't believe how fun it was to design</span></span></li>");
		assertEquals("Preview: Brainstorm Buddy is the best product ever. We can't believe how fun it was to design", test);
	}
	
	@Test
	public void testNotMatchingStringOnPreviewMatcher() {
		String test = e.previewMatcher("This string should not match");
		assertEquals("Preview: No Preview Available", test);
	}
	
}
