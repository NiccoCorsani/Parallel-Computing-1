package Model;

import java.util.ArrayList;
import java.util.List;

public class Producer extends Thread {

	private String articleWikipedia;

	private int numberOfCores;

	private List sharedListofSubstrings;

	public Producer(String articleWikipedia, int numberOfCores, List sharedListofSubstrings) {

		this.articleWikipedia = articleWikipedia;

		this.numberOfCores = numberOfCores;

		this.sharedListofSubstrings = sharedListofSubstrings;

	}

	@Override
	public void run() {

		this.DivideArticle();

	}

	private void produceSubSequences(String subString) throws InterruptedException {
		{
			synchronized (sharedListofSubstrings) {
				sharedListofSubstrings.add(subString);
				sharedListofSubstrings.notifyAll();
			}
		}
	}

	public String[] DivideArticle() {

		int dimensionArticle = articleWikipedia.length();

		int dimensionSubString = (dimensionArticle / numberOfCores);

		String[] subArticles = new String[numberOfCores];

		for (int i = 0; i < numberOfCores; i++) {
			try {

				subArticles[i] = articleWikipedia.substring(i * dimensionSubString, (i + 1) * dimensionSubString);
				InteractionUser.saveArticleAsFile(subArticles[i], "Article_" + String.valueOf(i));
				InteractionUser.saveArticleInSpecificPath(subArticles[i], "/Progetti c/zzzz_09-03-2020/src/Articles/Article_" + String.valueOf(i));

				//// Critical Section
				this.produceSubSequences(subArticles[i]);
				// System.out.println(subArticles[i].length());
				//// Critical section

			} catch (Exception e) {
				e.printStackTrace();
				

			}

		}

		return subArticles;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
