package brainstorm.buddy;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class JSoup {
	String myUrl;
	public JSoup(String url) {
		myUrl=url;
	}
	public String getContents() throws IOException {
		Document doc = Jsoup.connect(myUrl).get();
		String contents = doc.toString();
		return contents;
	}
}
