/*
 ============================================================================
 Name        : HW3.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello MPI World in C 
 ============================================================================
 */
#include <stdio.h>
#include <string.h>
#include "mpi.h"
#include <omp.h>
#include <stdlib.h>
#include <math.h>

#define NUM_THREADS 16 // change the value in order to set a different amount of threads
char *fileName = "../input.dat";
int arraySize;
double *valuesArray;
int MAX;
double grandSum = 0.0;

void readFile(char *fileName) {
	FILE *file = fopen(fileName, "r");
	if (!file) {
		fprintf(stderr, "Could not open file: %s\n", fileName);
		MPI_Abort(MPI_COMM_WORLD, 1);
	}
	fscanf(file, "%d", &arraySize);
	valuesArray = (double*) malloc(sizeof(double) * arraySize);
	for (int i = 0; i < arraySize; i++) {
		fscanf(file, "%lf", &valuesArray[i]);
	}
}

double calculateSum(double *array, int size) {
	double sum = 0;
#pragma omp parallel for reduction(+:sum)
	for (int i = 0; i < size; i++) {
		double temp = 0;
		for (int k = 0; k < MAX; k++) {
			temp = fmax(temp, cos(exp(sin(array[i] * k))));
		}
		sum += temp;
	}
	return sum;
}

int main(int argc, char *argv[]) {

	int my_rank; /* rank of process */
	int p; /* number of processes */
	MPI_Status status; /* return status for receive */
	/* start up MPI */
	double personalSum;
	MPI_Init(&argc, &argv);

	double t1 = MPI_Wtime();
	/* find out process rank */
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	/* find out number of processes */
	MPI_Comm_size(MPI_COMM_WORLD, &p);

	omp_set_dynamic(0);
	omp_set_num_threads(NUM_THREADS);

	if (my_rank == 0) {
		if (argc != 2) {
			printf("Please enter a value for MAX");
			MPI_Abort(MPI_COMM_WORLD, 1);
		}
		if ((MAX = atoi(argv[1])) < 0) {
			printf("Please set a value for MAX");
			MPI_Abort(MPI_COMM_WORLD, 1);
		}
	}
	MAX = atoi(argv[1]);

	if (my_rank == 0) {
		readFile(fileName);
	}
	MPI_Bcast(&arraySize, sizeof(arraySize), MPI_INT, 0, MPI_COMM_WORLD);
	double personalArray[arraySize / 2];
	MPI_Scatter(valuesArray, arraySize / 2, MPI_DOUBLE, &personalArray,
			arraySize / 2,
			MPI_DOUBLE, 0, MPI_COMM_WORLD);
	personalSum = calculateSum(personalArray, arraySize / 2);
	if (my_rank != 0) {
		MPI_Send(&personalSum, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);
	}
	if (my_rank == 0) {
		grandSum = personalSum;
		MPI_Recv(&personalSum, 1, MPI_DOUBLE, 1, 0, MPI_COMM_WORLD, &status);
		grandSum += personalSum;
		printf("The sum of calculations: %lf\n", grandSum);
		double t2 = MPI_Wtime();
		printf("The time it took to calculate for %d threads is: %lf\n",
		NUM_THREADS, t2 - t1);
	}

	/* shut down MPI */

	MPI_Finalize();

	return 0;
}

