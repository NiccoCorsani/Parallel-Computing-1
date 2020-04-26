package Model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RetriveDataWiki {

	private String url;
	private Document doc;
	private Elements paragraphs;
	private Element firstParagraph;
	private Element lastParagraph;
	private Element p;
	private String article;

	
	
	
	public String retrive(String url) throws IOException {

		doc = Jsoup.connect(url).get();
		paragraphs = doc.select(".mw-content-ltr p, .mw-content-ltr li");

		firstParagraph = paragraphs.first();
		lastParagraph = paragraphs.last();
		p = firstParagraph;
		int i = 1;
		article = p.text();
		while (p != lastParagraph) {

			try {
				p = paragraphs.get(i);
				article = article + p.text() + System.lineSeparator();
			} catch (Exception e) {

				break;
			}
			i++;
		}
		return article.replaceAll("^", "");
	}

	
	
	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		RetriveDataWiki r = new RetriveDataWiki();

		String d = r.retrive("https://it.wikipedia.org/wiki/Coverciano");

		System.out.println(d);

	}

}
