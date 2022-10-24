

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "exe.h"
#include "func.h"

void Q9_11() {
	int sum, num;
	num = 4;
	sum = count(num, 0);
	printf("There are %d ways to calculate %d", sum, num);
}

void Q12() {
	int arr[N] = { 1, 9, 6, 7, 8 };
	int d, s;
	int* x = &s;
	int* m = &d;
	*x = 1;
	*m = 1;
	findRecSequence(arr, N, 0, m, x, 1);
	printf("\nM:%d | X:%d", *m, *x);
}
void Q13() {
	int count;
	mat A = { { 'f', 'd', 'a', 'a', 'b' }, { 'f', 'g', 's', 'b', 'a' }, { 'f',
			'h', 'b', 'a', 'a' }, { 's', 'k', 'n', 'b', 's' }, { 'a', 'b', 'k',
			'p', 'o' } };
	count = check(A);
	printf("\nM=%d", count);
}
void Q14_A() {
	int arr[] = { 5, 2, 7, 3, 8, 9, 6 };
	int i, size, start, minOrderStatistic;
	start = 0;
	size = sizeof(arr) / sizeof(*arr);
	minOrderStatistic = findOrderStatistic(arr, start, size - 1, size / 2 + 1);
	for (i = 0; i < size; i++) {
		printf("%d ", *(arr + i));
	}
	printf("\nMin order statistic: %d", minOrderStatistic);
}
void Q14_B() {
	int arr[] = { 6, 3, 0, 7, 4, 2, 5 };
	int size, start, missingNum;
	start = 0;
	size = sizeof(arr) / sizeof(*arr);
	missingNum = findMissingNumber(arr, start, size - 1, size);
	printf("\nMissing number is: %d", missingNum);
}
void Q15() {
	int M[10], p[10];
	int i, j;
	for (i = 1, j = 0; i < 21; i += 2, j++) {
		*(M + j) = i + 1;
		*(p + j) = (1 + rand() % (100 - 1)) * 5;
		printf("M:%d p:%d\n", *(M + j), *(p + j));
	}
	printNumOfHousesForMaxProfit(M, p, 3, 9);

}
