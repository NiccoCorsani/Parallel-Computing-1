package Model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Consumer extends Thread {

	private List<String> sharedListofSubstrings;

	private HashMap<String, Integer> HashMapBigramsThread;
	private HashMap<String, Integer> HashMapTrigramsThread;
	private ConcurrentHashMap<String, Integer> concurrentHashMapBigrams;
	private ConcurrentHashMap<String, Integer> concurrentHashMapTrigrams;

	int counterBigram1 = 0;
	int counterTrigrams = 0;
	private String subArticle;
	String bigram1;
	String trigram;
	private long inizioParallelo;
	private Counter counter;
	private long fineParallelo;
	private int numberOfConsumers;

	public Consumer(List<String> sharedListofSubstrings, ConcurrentHashMap concurrentHashMapBigrams,
			ConcurrentHashMap concurrentHashMapTrigrams, long inizioParallelo, Counter counter, int numberOfConsumers) {

		this.sharedListofSubstrings = sharedListofSubstrings;
		this.concurrentHashMapBigrams = concurrentHashMapBigrams;
		this.concurrentHashMapTrigrams = concurrentHashMapTrigrams;
		this.inizioParallelo = inizioParallelo;
		this.counter = counter;
		this.numberOfConsumers = numberOfConsumers;

		HashMapTrigramsThread = new HashMap<String, Integer>();
		HashMapBigramsThread = new HashMap<String, Integer>();

	}

	public long getFineParallelo() {
		return fineParallelo;
	}

	public void setFineParallelo(long fineParallelo) {
		this.fineParallelo = fineParallelo;
	}

	@Override
	public void run() {

		synchronized (sharedListofSubstrings) {

			try {
				while (sharedListofSubstrings.get(0).equals(null)) {
					System.out.println("Thread: " + this.getName() + "is waiting for the producer to produce.");
					sharedListofSubstrings.wait();
					try {
						sharedListofSubstrings.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			} catch (IndexOutOfBoundsException e) {

				e.printStackTrace();

				System.out.println("Lista condivisa sottoArticoli vuota, termino.....");

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			subArticle = sharedListofSubstrings.remove(0);

			sharedListofSubstrings.notifyAll();

			System.err.println("Preso articolo da " + this.getName());

		}
		
		
		
		
		
		
		
		
		
		
		
		

		calculateBigrams(subArticle);
		calculateTrigrams(subArticle);

		
		
		
		
		
		
		
		
		
		
		
		///////mettere hasmap singolo thread in concurrent HashMap
		
		for (String ss : HashMapBigramsThread.keySet()) {
			
			if (!concurrentHashMapBigrams.containsKey(ss))
				//// provare a levare il containsKey putIfAbsent()
				concurrentHashMapBigrams.put(ss, HashMapBigramsThread.get(ss));
			else
				concurrentHashMapBigrams.put(ss,concurrentHashMapBigrams.get(ss)+ HashMapBigramsThread.get(ss));
			
			
		}
		for (String ss : HashMapTrigramsThread.keySet())
		{
			
			if (!concurrentHashMapTrigrams.containsKey(ss))
				//// provare a levare il containsKey putIfAbsent()
				concurrentHashMapTrigrams.put(ss, HashMapTrigramsThread.get(ss));
			else
				concurrentHashMapTrigrams.put(ss,concurrentHashMapTrigrams.get(ss) + HashMapTrigramsThread.get(ss));
			
			
		
		}
		
		
		
		
		
		
		
		
		
		
		for (String name : concurrentHashMapBigrams.keySet()) {

			if (name.contains(".") || name.contains(";") || name.contains(" ") || name.contains("^")
					|| name.contains(",") || name.contains("-") || name.contains("?") || name.contains("*")
					|| name.contains(")") || name.contains("(") || name.contains("\n") || name.contains(":")
					|| name.contains("\t") || name.contains("]") || name.contains("[") || name.contains("'")
					|| name.contains("<"))
				concurrentHashMapBigrams.remove(name);
		} 
		
			
		
		for (String name : concurrentHashMapTrigrams.keySet()) 

			if (name.contains(".") || name.contains(";") || name.contains(" ") || name.contains("^")
					|| name.contains(",") || name.contains("-") || name.contains("?") || name.contains("*")
					|| name.contains(")") || name.contains("(") || name.contains("\n") || name.contains(":")
					|| name.contains("\t") || name.contains("]") || name.contains("[") || name.contains("'")
					|| name.contains("<")) {
				concurrentHashMapTrigrams.remove(name);
				
			
							
				///////mettere hasmap singolo thread in concurrent HashMap
		
				
		}    
		
		

	}

	public void calculateTrigrams(String subArticle) {

		while (true) {

			try {
				while ((trigram = subArticle.substring(counterTrigrams + 0, counterTrigrams + 3).toLowerCase())
						.contains(" "))
					counterTrigrams++;

				if (!HashMapTrigramsThread.containsKey(trigram))
					HashMapTrigramsThread.put(trigram, 1);
				else
					HashMapTrigramsThread.put(trigram, HashMapTrigramsThread.get(trigram) + 1);

				counterTrigrams++;
			} catch (Exception e) {
				break;
			}

		}



	}

	public void calculateBigrams(String subArticle) {

		while (true) {

			try {
				while ((bigram1 = subArticle.substring(counterBigram1 + 0, counterBigram1 + 2).toLowerCase())
						.contains(" ")) {
					counterBigram1++; //// procede a bigramma successivo saltando spazzi
				}

					if (!HashMapBigramsThread.containsKey(bigram1))
						//// provare a levare il containsKey putIfAbsent()
						HashMapBigramsThread.put(bigram1, 1);
					else
						HashMapBigramsThread.put(bigram1, HashMapBigramsThread.get(bigram1) + 1);

				
				counterBigram1++; //// procede a bigramma successivo
			} catch (Exception e) {
				break;
			}

		}



		
	}

}
