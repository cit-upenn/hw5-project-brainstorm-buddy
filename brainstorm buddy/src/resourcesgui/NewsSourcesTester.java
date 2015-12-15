package resourcesgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
/**
 * This is a class for the NewsSources class
 * @author meredithmargulies
 *
 */
public class NewsSourcesTester {
	
		NewsSources ns;
	
		@Before
		public void setUp() throws Exception {
			ns =  new NewsSources("test.txt", true, false);
		}
		
		@Test
		public void testEmptyStringforLinkMatcher() {
			ArrayList<String> test = ns.linkMatcher("");
			assertNotNull(test);
		}
		
		@Test
		public void testMatchingStringOnLinkMatcher() {
			ArrayList<String> test = ns.linkMatcher("href=\"http://www.jstor.org/stable/12345\"");
			assertEquals("http://www.jstor.org/stable/12345", test.get(0));
		}
		
		@Test
		public void testLinkMatchingMultipleString() {
			ArrayList<String> test = ns.linkMatcher("href=\"http://www.jstor.org/stable/54321\"\nhref=\"http://www.jstor.org/stable/13524\"");
			assertEquals("http://www.jstor.org/stable/54321", test.get(0));
			assertEquals("http://www.jstor.org/stable/13524", test.get(1));
		}
		
		@Test
		public void testValidJson() {
			ArrayList<String> test = ns.parseOutput("{\"response\":{\"meta\":{\"hits\":5493,\"time\":55,\"offset\":10},\"docs\":[{\"web_url\":\"http:\\/\\/www.nytimes.com\\/2015\\/12\\/11\\/opinion\\/the-tarnished-trump-brand.html\"},{\"web_url\":\"http:\\/\\/www.nytimes.com\\/politics\\/first-draft\\/2015\\/12\\/08\\/white-house-says-donald-trumps-muslim-proposal-disqualifies-him-for-president\\/\"},]}}");
			assertEquals("http://www.nytimes.com/2015/12/11/opinion/the-tarnished-trump-brand.html", test.get(0));
		}
		
		@Test
		public void testNotValidJson() {
			ArrayList<String> test = ns.parseOutput("{\"response\":{\"meta\":{\"hits\":5493,\"time\":55,\"offset\":10},\"docs\":[]}}");
			assertEquals(0, test.size());
		}

}
