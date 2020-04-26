/*
 * Consumer.c
 *
 *  Created on: 22 feb 2020
 *      Author: nicche
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "../bigram_trigram_c/Producer.h"

typedef struct {

	char bigram[2];

	int occurences;

} bigram_occurences;

bigram_occurences mapBigrams[10000];
contatoreVerifica = 0;
int bubu = 0;

////////////////////////////// implementare funzione che fa sort su struttura

int comparator(const void *p, const void *q) {

	int a = ((bigram_occurences*) p)->occurences;

	int b = ((bigram_occurences*) q)->occurences;

	if (a < b)
		return 1;
	if (a > b)
		return -1;

	return 0;

}

int UpdateIfPresent(char a, char b) {

	int iterMap = 0;
	char c;
	char d;
	while (mapBigrams[iterMap].bigram[0] != '\0') {
		 c = mapBigrams[iterMap].bigram[0];
		d = mapBigrams[iterMap].bigram[1];
		if (a == mapBigrams[iterMap].bigram[0]
				&& b == mapBigrams[iterMap].bigram[1]) {
			mapBigrams[iterMap].occurences++;
			return 1;
		}
		iterMap++;
	}
	return 0;

}

void printMap() {

	int iterMap = 0;
	while (mapBigrams[iterMap].bigram[0] != '\0') {
		printf("\n  %c%c   %d", mapBigrams[iterMap].bigram[0],
				mapBigrams[iterMap].bigram[1], mapBigrams[iterMap].occurences);
		iterMap++;
	}

}

int counterMap = 0;

void* calculateBigrams(void *arg) {




	char bigram[2];
	char *article = (char*) arg;
	int i = 0;
	int lettera0 = 0;
	int lettera1 = 0;

	while (article[i] != '\0' || article[i + 1] != '\0') {

		bigram[0] = article[i];
		bigram[1] = article[i + 1];
		lettera0 = bigram[0];
		lettera1 = bigram[1];
		////Controllo spazzi, a capo
		while (lettera0 == 32 || lettera1 == 32 || lettera0 == 10) ///// questo è stato fatto perche non riconosceva                              bigram[0] == ' '
		{
			i++;
			bigram[0] = article[i];
			bigram[1] = article[i + 1];
			lettera0 = bigram[0];
			lettera1 = bigram[1];
		}
		////Controllo spazzi, a capo

		pthread_mutex_lock(&mutex);
		if (UpdateIfPresent(bigram[0], bigram[1]) == 0) {
			///// return 0 if not present
			strcpy(mapBigrams[counterMap].bigram, bigram);
			mapBigrams[counterMap].occurences = 1;
			counterMap++;
			contatoreVerifica++;
		}
		pthread_mutex_unlock(&mutex);

		i++;
	}
	qsort(mapBigrams, counterMap, sizeof(bigram_occurences), comparator);

	if (bubu == 3){
		printMap();
		printf("\n\n\n\n\n%d\n\n\n\n\n",contatoreVerifica);
	}
	bubu++;

	printf("\nContatore %d con", bubu);

	int numberThread = pthread_self();

	printf(" Thread_%d ha concluso la funzione\n", numberThread);


	return 0;
}
