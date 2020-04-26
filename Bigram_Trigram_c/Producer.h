//
//  Producer.h
//  ParallelComputing
//
//  Created by Nicche on 19/02/2020.
//  Copyright © 2020 Nicche. All rights reserved.
//

#ifndef Producer_h
#define Producer_h

#include <stdio.h>
#include <pthread.h>

#endif /* Producer_h */


extern pthread_mutex_t mutex;
extern pthread_mutex_t mutex1;

extern int idMainThread;
extern int contatoreVerifica;

void* calculateBigrams(void *arg) ;
