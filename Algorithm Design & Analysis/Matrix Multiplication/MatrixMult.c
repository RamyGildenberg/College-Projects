#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "MatrixMult.h"

int **m;
int **s;

void print_mat(int** mat, int size) {
	int i, j;
	for (i = 1; i <= size; i++) {
		printf("%d|", size+1-i);
		for (j = i; j > 0; j--) {
			printf("%10d", m[i][j]);
		}
		for (j = i + 1; j <= size; j++) {
			printf("%10d", mat[i][j]);
		}
		if (i < size) {
			printf("\n");
		}
	}
	printf("\n");
	for (i = 0; i <= size*10; i++) {
		printf("-");
	}
	printf("\n");
	printf(" |");
	for (i = 1; i <= size; i++) {
		printf("%10d", i);
	}
	printf("\n");
}

void init(int* p, int n) {
	int i;
	printf("Please enter the dimentions: \n");
	for (i = 0; i <= n; i++) {
		printf("P%d: ", i);
		scanf("%d", &p[i]);
	}
	m = (int**) malloc(sizeof(int*) * (n + 1));
	if (!m) {
		perror("malloc");
		exit(-1);
	}
	s = (int**) malloc(sizeof(int*) * (n + 1));
	if (!s) {
		perror("malloc");
		exit(-1);
	}
	for (i = 0; i <= n; i++) {
		m[i] = (int*) malloc((n + 1) * sizeof(int));
		s[i] = (int*) malloc((n + 1) * sizeof(int));
		if ((!m) || (!s)) {
			perror("malloc");
			exit(-1);
		}
	}
}

int memorized_matrix_chain(int *p, int size) {
	int n = size, i, j;
	for (i = 1; i <= n; i++) {
		m[i][i] = 0;
		for (j = i; j <= n; j++) {
			m[i][j] = (int) INFINITY;
			s[i][j] = 0;
		}
	}
	return lookup_chain(p, 1, n);
}

int lookup_chain(int *p, int i, int j) {
	if (m[i][j] < (int) INFINITY) {
		return m[i][j];
	}
	if (i == j) {
		m[i][j] = 0;
	} else {
		int k, q;
		for (k = i; k < j; k++) {
			q = lookup_chain(p, i, k) + lookup_chain(p, k + 1, j)
					+ p[i - 1] * p[k] * p[j];
			if (q < m[i][j]) {
				m[i][j] = q;
				s[i][j] = k;
			}
		}
	}
	return m[i][j];
}

void print_optimal(int i, int j) {
	if (i == j) {
		printf(" A%d ", i);
	} else {
		printf("(");
		print_optimal(i, s[i][j]);
		print_optimal(s[i][j] + 1, j);
		printf(")");
	}
}

int main(void) {
	int n, i;
	printf("How many matrices: ");
	scanf("%d", &n);
	int* p = (int*) malloc(sizeof(int) * n);
	init(p, n);

	printf("Minimum multiplications: %d\n", memorized_matrix_chain(p, n));
	printf("Optimal sequence: ");
	print_optimal(1, n);
	printf("\nm:\n");
	print_mat(m, n);
	printf("s:\n");
	print_mat(s, n);
	for (i = 0; i <= n; i++) {
		free(m[i]);
		free(s[i]);
	}
	free(m);
	free(s);
	free(p);
	system("pause");
	return EXIT_SUCCESS;
}