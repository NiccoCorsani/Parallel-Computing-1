package Model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Consumer extends Thread {

	private List<String> sharedListofSubstrings;

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

	}

	public void calculateTrigrams(String subArticle) {

		while (true) {

			try {
				while ((trigram = subArticle.substring(counterTrigrams + 0, counterTrigrams + 3).toLowerCase())
						.contains(" ")) {
					counterTrigrams++; //// procede a bigramma successivo saltando spazzi
				}

				if (concurrentHashMapTrigrams.putIfAbsent(trigram, 1) != null)
					concurrentHashMapTrigrams.merge(trigram, 1, Integer::sum);

				counterTrigrams++; //// procede a bigramma successivo
			} catch (Exception e) {
				break;// Quando a bigram null fa il break, dunque la fine del file
			}

		}

		for (String name : concurrentHashMapTrigrams.keySet()) {

			if (name.contains(".") || name.contains(";") || name.contains(" ") || name.contains("^")
					|| name.contains(",") || name.contains("-") || name.contains("?") || name.contains("*")
					|| name.contains(")") || name.contains("(") || name.contains("\n") || name.contains(":")
					|| name.contains("\t") || name.contains("]") || name.contains("[") || name.contains("'")
					|| name.contains("<"))
				concurrentHashMapTrigrams.remove(name);
		}

	}

	public void calculateBigrams(String subArticle) {

		while (true) {

			try {
				while ((bigram1 = subArticle.substring(counterBigram1 + 0, counterBigram1 + 2).toLowerCase())
						.contains(" ")) {
					counterBigram1++; //// procede a bigramma successivo saltando spazzi
				}

				if (concurrentHashMapBigrams.putIfAbsent(bigram1, 1) != null)
					concurrentHashMapBigrams.merge(bigram1, 1, Integer::sum);

				counterBigram1++; //// procede a bigramma successivo
			} catch (Exception e) {
				break; /// Quando a bigram null fa il break, dunque la fine del file
			}

		}

		for (String name : concurrentHashMapBigrams.keySet()) {

			if (name.contains(".") || name.contains(";") || name.contains(" ") || name.contains("^")
					|| name.contains(",") || name.contains("-") || name.contains("?") || name.contains("*")
					|| name.contains(")") || name.contains("(") || name.contains("\n") || name.contains(":")
					|| name.contains("\t") || name.contains("]") || name.contains("[") || name.contains("'")
					|| name.contains("<"))
				concurrentHashMapBigrams.remove(name);
		}

	}

}
