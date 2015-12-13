package resourcesgui;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class JSoup {
	String myUrl;
	public JSoup(String url) {
		myUrl=url;
	}
	public String getContents() {
		Document doc;
		try {
			doc = Jsoup.connect(myUrl).get();
			String contents = doc.toString();
			return contents;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("fail");
			return "Error in search";
		}
	}
}
