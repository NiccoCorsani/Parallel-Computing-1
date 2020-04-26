package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InteractionUser {

	private String url;
	private RetriveDataWiki retriveData;
	private static String articleWikipedia;
	private int numberOfCores;

	public InteractionUser() {
		this.retriveData = new RetriveDataWiki();

	}

	public String ObtainArticle() {

		InputStreamReader in_stream_reader = new InputStreamReader(System.in);
		BufferedReader buffered_reader = new BufferedReader(in_stream_reader);

		while (true) {

			System.out.println("Inserire url wikipedia:");

			try {

				url = buffered_reader.readLine();

				articleWikipedia = retriveData.retrive(url);


			} catch (Exception e) {

				System.out.println("Errore nell'url inserito, riprovare");
				continue;

			}

			return articleWikipedia;
		}

	}

	public int GetNumberOfCores() {

		numberOfCores = (Runtime.getRuntime().availableProcessors());
		return numberOfCores;

	}

	
	public static void saveArticleInSpecificPath(String stringToSaveinToFile, String nameOfFolderUnderDesktop) {
		
		String home = System.getProperty("user.home");
		File f = new File(home + File.separator + "Desktop" + File.separator + nameOfFolderUnderDesktop  );
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(f));
			writer.write(stringToSaveinToFile);

		} catch (IOException e) {
			
			e.printStackTrace();
			
			
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		
		
		
		
	}
	
	
	public static void append_Article(String whatOverWrite,String nameFile ) throws IOException {
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(nameFile,true));
			writer.write(whatOverWrite+System.lineSeparator());

		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		writer.close();
		
	}

	
	public static void saveArticleAsFile(String articleWikipedia, String nameArticle) throws IOException {

		
		
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(nameArticle));
			writer.write(articleWikipedia);

		} catch (IOException e) {
			
			e.printStackTrace();
			
			
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}

	}

	public static Map<String, Integer> sortByValue(final Map<String, Integer> passedMap) {

		return passedMap.entrySet().stream().sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	}

	public static void saveMap(Map<String, Integer> concurrentHash, String fileName) throws IOException {

		String mapString = "";
		for (String name : concurrentHash.keySet()) {
			String key = name.toString();
			String value = concurrentHash.get(name).toString();
			mapString = mapString + key + "  " + value + System.lineSeparator();

		}

	    

		saveArticleAsFile(mapString, fileName);

	}

	public static String readArticle(String fileName) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String ss = null;
		String articolo = null;
		while ((ss = br.readLine()) != null)
			articolo = articolo + ss +System.lineSeparator();


		return articolo;

	}

	public static void main(String[] args) {

	}

}
