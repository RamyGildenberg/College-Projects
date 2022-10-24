/*
 * main.c
 *
 *  Created on: Dec 16, 2018
 *      Author: tamir-ubuntu
 */
#include <stdio.h>
#include <stdlib.h>

#define SIZE 3
typedef struct {
	int size;
	int** matrix;
	int* numOfZeroInARow;
	int dnf;
} MyMatrix;

void Init(MyMatrix* A, int n) {
	int i, j;
	A->size = n;
	A->matrix = (int**) malloc(A->size * sizeof(int*));
	if (!A->matrix) {
		return;
	}
	for (i = 0; i < A->size; i++) {
		A->matrix[i] = (int*) malloc(A->size * sizeof(int));
		if (!A->matrix[i]) {
			return;
		}
		for (j = 0; j < n; j++) {
			A->matrix[i][j] = 1;
		}
	}
	A->numOfZeroInARow = (int*) calloc(A->size, sizeof(int));
	A->dnf = A->size;
}

void Flip(MyMatrix* A, int i, int j) {
	if (A->matrix[i][j] == 1) {
		if (A->numOfZeroInARow[i] == 0) {
			A->dnf--;
		}
		A->numOfZeroInARow[i]++;
	} else {
		if (A->numOfZeroInARow[i] == 1) {
			A->dnf++;
		}
		A->numOfZeroInARow[i]--;
	}
	A->matrix[i][j] = 1 - A->matrix[i][j];

}
void printMat(const MyMatrix* A) {
	int i, j;
	for (i = 0; i < A->size; i++) {
		for (j = 0; j < A->size; j++) {
			printf("%d ", A->matrix[i][j]);
		}
		printf("\n");
	}
}

int DNF(MyMatrix* A) {
	return A->dnf;
}

int main() {
	MyMatrix A = { 0, NULL, NULL, 0 };
	Init(&A, SIZE);
	printMat(&A);
	printf("DNF: %d\n", DNF(&A));
	Flip(&A, 0, 1);
	Flip(&A, 1, 1);
	Flip(&A, 2, 1);
	printMat(&A);
	printf("DNF: %d\n", DNF(&A));
	Flip(&A, 1, 1);
	printMat(&A);
	printf("DNF: %d\n", DNF(&A));
	return 0;
}
