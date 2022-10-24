/*
 ============================================================================
 Name        : Q2.c
 Author      : 
 Version     :
 Copyright   : This is OURS!
 Description : MPI with Cartesian topology
 ============================================================================
 */
#include <stdio.h>
#include <string.h>
#include "mpi.h"
#include <stdlib.h>
#include <math.h>

int size, N, maxIteration, rankFound = -1, pStop = 0;
char *subString, *stringArray;
char *fileName = "../data.txt";



void readFile(char *fileName) {
	FILE *file = fopen(fileName, "r");
	if (!file) {
		fprintf(stderr, "Could not open file: %s\n", fileName);
		MPI_Abort(MPI_COMM_WORLD, 1);
	}
	fscanf(file, "%d%d%d", &size, &N, &maxIteration);
	char tempStr[N * 2];
	stringArray = (char*) malloc(sizeof(char) * (size * size * N * 2));
	subString = (char*) malloc(sizeof(char) * (N * 2));
	fscanf(file, "%s", subString);
	fscanf(file, "%s", stringArray);
	for (int i = 1; i < size * size; i++) {
		fscanf(file, "%s", tempStr);
		strncat(stringArray, tempStr, N * 2);
	}
}




void create_cart(int *dim, int *period, int reorder, MPI_Comm *comm,
		int matSize) {
	dim[0] = matSize;
	dim[1] = matSize;
	MPI_Cart_create(MPI_COMM_WORLD, 2, dim, period, reorder, comm);
}




char* rearrangeString(MPI_Comm cartComm, int my_rank, char *myStr,
		MPI_Status status) {
	int upper, lower, left, right, counter = 0;
	MPI_Cart_shift(cartComm, 1, 1, &left, &right);
	MPI_Cart_shift(cartComm, 0, 1, &upper, &lower);
	char strEven[N], strOdd[N];
	while (*myStr != '\0') {
		if (counter % 2 == 0)
			strncat(strEven, myStr, 1);
		else
			strncat(strOdd, myStr, 1);
		counter++;
		myStr++;
	}
	MPI_Send(strOdd, N, MPI_CHAR, left, 0, cartComm);
	MPI_Send(strEven, N, MPI_CHAR, upper, 0, cartComm);
	MPI_Recv(strOdd, N + 1, MPI_CHAR, right, 0, cartComm, &status);
	MPI_Recv(strEven, N + 1, MPI_CHAR, lower, 0, cartComm, &status);
	MPI_Barrier(MPI_COMM_WORLD);
	strcat(strOdd, strEven);
	return strdup(strOdd);

}




char* checkForSubString(char *myStr, int my_rank, int p, MPI_Status status,
		MPI_Comm cartComm) {
	char *temp;
	for (int i = 0; i < maxIteration; i++) {
		if (i > 0 && my_rank != 0)
			MPI_Recv(&pStop, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		if (pStop == 1)
			break;
		if (i != 0)
			strcpy(myStr, rearrangeString(cartComm, my_rank, myStr, status));

		temp = strstr(myStr, subString);
		if (temp) {
			rankFound = my_rank;
			pStop = 1;
		}
		if (my_rank != 0) {
			MPI_Send(&rankFound, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
			MPI_Send(&pStop, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		}
		MPI_Barrier(MPI_COMM_WORLD);
		if (my_rank == 0) {
			for (int i = 1; i < p; i++) {
				MPI_Recv(&rankFound, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
				MPI_Recv(&pStop, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
				if (pStop == 1) {
					break;
				}
			}
			for (int i = 1; i < p; i++) {
				MPI_Send(&pStop, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
			}
		}
		MPI_Barrier(MPI_COMM_WORLD);
	}
	return strdup(myStr);
}



int main(int argc, char *argv[]) {
	int my_rank; /* rank of process */
	int p; /* number of processes */
	MPI_Comm cartComm;
	MPI_Status status; /* return status for receive */
	int period[2] = { 1, 1 };
	/* start up MPI */
	MPI_Init(&argc, &argv);
	/* find out process rank */
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	/* find out number of processes */
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	int dim[2] = { p, p };
	if (my_rank == 0) {
		readFile(fileName);
	}
	MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Bcast(&maxIteration, 1, MPI_INT, 0, MPI_COMM_WORLD);
	subString = (char*) realloc(subString, N * 2);
	MPI_Bcast(subString, 2 * N + 1, MPI_CHAR, 0, MPI_COMM_WORLD);
	MPI_Bcast(&size, 1, MPI_INT, 0, MPI_COMM_WORLD);
	char personalProccesString[N * 2];
	MPI_Scatter(stringArray, N * 2, MPI_CHAR, &personalProccesString, N * 2,
	MPI_CHAR, 0, MPI_COMM_WORLD);
	create_cart(dim, period, 1, &cartComm, sqrt(size * size));
	strcpy(personalProccesString,
			checkForSubString(personalProccesString, my_rank, p, status,
					cartComm));
	MPI_Gather(&personalProccesString, N * 2, MPI_CHAR, stringArray, N * 2,
	MPI_CHAR, 0, MPI_COMM_WORLD);
	if (my_rank == 0){
		if (rankFound == -1)
			printf("Could not find the requested substring");
		else
			for(int i = 0; i < p ; i++)
				printf("My Rank: %d   ,    My String: %.*s \n",i, 6, stringArray + (6 * i));
	}
	MPI_Finalize();
	return 0;
}
