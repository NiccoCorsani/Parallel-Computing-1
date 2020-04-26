package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class InteractionUser {

	private String url;
	private static String articleWikipedia;
	private int numberOfCores;

	public InteractionUser() {

	}

	public int GetNumberOfCores() {

		numberOfCores = (Runtime.getRuntime().availableProcessors());
		return numberOfCores;

	}

	public static void saveArticleInSpecificPath(String stringToSaveinToFile, String nameOfFolderUnderDesktop) {

		String home = System.getProperty("user.home");
		File f = new File(home + File.separator + "Desktop" + File.separator + nameOfFolderUnderDesktop);

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

	public static void append_Article(String whatOverWrite, String nameFile) throws IOException {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(nameFile, true));
			writer.write(whatOverWrite + System.lineSeparator());

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

	///// Funzione specifica che può caricare tantissimi dati
	public static String readArticle(String fileName) throws IOException, InterruptedException {

		// Per eseguire bene questa funzione seguire il link gui sotto
		// http://www.planetofbits.com/eclipse/increase-jvm-heap-size-in-eclipse/
		// Aggiungendo (come dice il sito) gli argomenti Xmx5gb è come se facessi "java
		// xmx5gb main"----> per fare partire il file da terminale

		String line = "";
		StringBuilder sb = new StringBuilder();
		int i = 1;

		Path path = Paths.get(fileName);


		for (String ss : Files.readAllLines(path, StandardCharsets.UTF_8))

			sb.append(ss);

		System.out.println("Stringa lunga: "+sb.toString().length()/1024+" byte");

		return sb.toString();

	}

	public static void main(String[] args) {

	}

}
