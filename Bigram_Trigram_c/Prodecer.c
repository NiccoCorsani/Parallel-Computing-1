//
//  Producer.c
//  ParallelComputing
//
//  Created by Nicche on 19/02/2020.
//  Copyright © 2020 Nicche. All rights reserved.
//

#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h> /////da cambiare su mac con <unistd.h>
#include <dirent.h>
#include <errno.h>
#include "../bigram_tri#include "../bigram_trigram_c/Producer.h"
gram_c/Producer.h"

size_t strlen(const char *s) {
	size_t i;
	for (i = 0; s[i] != '\0'; i++)
		;
	return i;
}

char* strcpy(char *destination, const char *source) {
	// return if no memory is allocated to the destination
	if (destination == NULL)
		return NULL;
	// take a pointer pointing to the beginning of destination string
	char *ptr = destination;
	// copy the C-string pointed by source into the array
	// pointed by destination
	while (*source != '\0') {
		*destination = *source;
		destination++;
		source++;
	}
	// include the terminating null character
	*destination = '\0';
	// destination is returned by standard strcpy()
	return ptr;
}

char* strcat(char *destination, const char *source) {
	char *ptr;
	ptr = malloc(30);
	int count = 0;
////"src...
	while (destination[count] != '\0') {

		ptr[count] = destination[count];

		count++;
	}
// Artilce-1
	int count2 = 0;
	while (source[count2] != '\0') {
		ptr[count] = source[count2];
		count++;
		count2++;
	}
	// null terminate destination string
	ptr[count] = '\0';
	// destination is returned by standard strcat()
	return ptr;
}

int strcmp(const char *s1, const char *s2) {

	for (; *s1 == *s2; ++s1, ++s2)
		if (*s1 == 0)
			return 0;
	return *(unsigned char*) s1 < *(unsigned char*) s2 ? -1 : 1;
}

void putFileInbuffer(char *article, int n) {
	DIR *dir;
	char *articlesName[30];
	int i = 0;
	int j = 0;
	int numberOfArticles = 0;
	struct dirent *ent;
	if ((dir = opendir("./src/Articles")) != NULL) {
		/* print all the files and directories within directory */
		while ((ent = readdir(dir)) != NULL) {
			if (strcmp(ent->d_name, ".") && ent->d_name != NULL) {
				if (strcmp("......", ent->d_name) == 1)
					continue;
				articlesName[i] = malloc(strlen(ent->d_name));
				strcpy(articlesName[i], ent->d_name);
				numberOfArticles++;
				i++;
				j++;
			}
		}
		closedir(dir);
	} else {
		int errnum = errno;
		fprintf(stderr, "Value of errno: %d\n", errno);
		perror("");
	}

	char *nameArticle;
	nameArticle = strcat("./src/Articles/", articlesName[n]);
	long length;
	FILE *f = fopen(nameArticle, "rb");

	if (f) {
		fseek(f, 0, SEEK_END);
		length = ftell(f);
		fseek(f, 0, SEEK_SET);
		fread(article, 1, length, f);
		fclose(f);

	}
	else{

		printf("\nErrore apertura file\n");
		perror("");
	}

}
