#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <sys/time.h>
#include "../bigram_trigram_c/Producer.h"

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex1 = PTHREAD_MUTEX_INITIALIZER;

int idMainThread;
int main(void) {

	char article1[1000000];
	char article2[1000000];
	char article3[1000000];
	char article4[1000000];

	putFileInbuffer(article1, 0);
	putFileInbuffer(article2, 1);
	putFileInbuffer(article3, 2);
	putFileInbuffer(article4, 3);

	pthread_t threadId1, threadId2, threadId3, threadId4;

	struct timeval stop, start;
	gettimeofday(&start, NULL);

	int numberThread = pthread_self();

	if (pthread_create(&threadId1, NULL, calculateBigrams, (void*) article1)) ///// ricordarsi che per far trovare la reference bisogna andare in properties->c/c++Build->setting->GCC C++ linker->libraries in top part add "pthread"                                                                     /////come nel video
			{
		fprintf(stderr, "Error creating thread\n");
		return 1;
	}

	pthread_create(&threadId2, NULL, calculateBigrams, (void*) article2);

	pthread_create(&threadId3, NULL, calculateBigrams, (void*) article3);

	pthread_create(&threadId4, NULL, calculateBigrams, (void*) article4);
	pthread_join(threadId1, NULL);
	pthread_join(threadId2, NULL);
	pthread_join(threadId3, NULL);
	pthread_join(threadId4, NULL);

	gettimeofday(&stop, NULL);

	int time = (stop.tv_sec - start.tv_sec) * 1000000 + stop.tv_usec
			- start.tv_usec;

	printf("\n\ntook %lu us\n", time);

	FILE *fptr;

	// use appropriate location if you are using MacOS or Linux
	fptr = fopen("./ParalleloC.txt", "a");

	if (fptr == NULL) {
		perror("");
		exit(1);
	}



	FILE *fp = fopen("./src/Articles/Article_0", "rb");
	if (fp == NULL) {
			perror("");
			exit(1);
		}
	fseek(fp, 0L, SEEK_END);
	int sz = (ftell(fp)*4)/1024;

	printf("Dimensione del file: %d", sz);
	fprintf(fptr,"%d", time);
	fprintf(fptr," %d", sz);
	fprintf(fptr,"\n");

	fclose(fptr);


	return 0;
}
