package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main_separetedMap {

	public static void main(String[] args) throws InterruptedException, IOException {

		InteractionUser iu = new InteractionUser();
		String article = InteractionUser.readArticle("FileIntero.txt");
		int numberOfConsumers = 4;
		List sharedListofSubstrings = new ArrayList<String>();
		Consumer[] consumers = new Consumer[numberOfConsumers];
		Producer producer = new Producer(article, numberOfConsumers, sharedListofSubstrings);
		ConcurrentHashMap concurrentHashMapParallelo = new ConcurrentHashMap<String, Integer>();
		ConcurrentHashMap concurrentHashMapSequenziale = new ConcurrentHashMap<String, Integer>();
		ConcurrentHashMap concurrentHashMapParalleloTrigrams = new ConcurrentHashMap<String, Integer>();
		ConcurrentHashMap concurrentHashMapSequenzialeTrigrams = new ConcurrentHashMap<String, Integer>();
		producer.run();

		Counter counterParallel = new Counter();
		Counter counterSequential = new Counter();
		counterSequential.setCounter(-1);

		long inizioParallelo = System.currentTimeMillis();
		for (int i = 0; i < numberOfConsumers; i++) {
			consumers[i] = new Consumer(sharedListofSubstrings, concurrentHashMapParallelo,
					concurrentHashMapParalleloTrigrams, inizioParallelo, counterParallel, numberOfConsumers);
			consumers[i].start();

		}
		producer.join();

		for (int i = 0; i < numberOfConsumers; i++)
			consumers[i].join();

		long fineParallelo = System.currentTimeMillis();

		System.out.println("Tempo di esecuzione parallelo:  " + (fineParallelo - inizioParallelo));

		///// Sequenziale

		Consumer consumerSequential = new Consumer(null, concurrentHashMapSequenziale,
				concurrentHashMapSequenzialeTrigrams, 10, counterSequential, numberOfConsumers);
		long inizioSequenziale = System.currentTimeMillis();
		consumerSequential.calculateBigrams(article);
		consumerSequential.calculateTrigrams(article);
		long fineSequenziale = System.currentTimeMillis();
		System.err.println("Tempo di esecuzione sequenziale:  " + (fineSequenziale - inizioSequenziale));

		///// Sequenziale

		///// Salvataggio dati istogrammi

		Map mapSequenzialeTrigrams = InteractionUser.sortByValue(concurrentHashMapSequenzialeTrigrams);
		Map mapParalleloTrigrams = InteractionUser.sortByValue(concurrentHashMapParalleloTrigrams);
		InteractionUser.saveMap(mapSequenzialeTrigrams, "IstogrammaSequenzialeTrig");
		InteractionUser.saveMap(mapParalleloTrigrams, "IstogrammaParalleloTrig");
		Map mapSequenziale = InteractionUser.sortByValue(concurrentHashMapSequenziale);
		Map mapParallelo = InteractionUser.sortByValue(concurrentHashMapParallelo);
		InteractionUser.saveMap(mapSequenziale, "IstogrammaSequenziale");
		InteractionUser.saveMap(mapParallelo, "IstogrammaParallelo");

		///// Salvataggio dati istogrammi

		///// Scrivo informazioni velocità/DimensioneFile

		File file = new File("FileIntero.txt");
		float byte_number = file.length() / 1024;
		String dim = String.valueOf(byte_number);
		iu.append_Article(String.valueOf(fineSequenziale - inizioSequenziale) + " " + dim,
				"Sequenziale Dim Velocità_separetedMap_4_Threads");
		iu.append_Article(String.valueOf(fineParallelo - inizioParallelo) + " " + dim,
				"Parallelo Dim Velocità_separetedMap_4_Threads");

		///// Scrivo informazioni velocità/DimensioneFile

	}
}