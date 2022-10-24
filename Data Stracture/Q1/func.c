
#include <stdio.h>
#include "func.h"

int count(int num, int sum) {
	if (num == 0)
		return 1;
	int i;
	for (i = 1; i <= num; i++)
		sum += count(num - i, 0);
	return sum;
}

void findRecSequence(const int* arr, int size, int p, int* m, int* x, int temp) {
	if ((size == 0) || (p == size - 1))
		return;
	if (*(arr + p) < *(arr + p + 1)) {
		if (temp > *m)
			*m = temp + 1;
		findRecSequence(arr, size, p + 1, m, x, temp + 1);
	} else {
		if (temp > 1 && *x == 1)
			*x = temp;
		temp = 1;
		findRecSequence(arr, size, p + 1, m, x, temp);
	}
	return;
}

int check(mat A) {
	return checkForArrowMat(A, 0, 0, N - 1);
}
int checkForArrowMat(mat A, int M, int row, int col) {
	if (row == N)
		return M;
	if (A[row][col] != 'b')
		return M;
	else {
		int i, j;
		if (row > 0) {
			for (i = row - 1; i >= 0; i--) {
				if (A[i][col] != 'a') {
					return M;
				}
			}
			for (j = col + 1; j <= N - 1; j++) {
				if (A[row][j] != 'a') {
					return M;
				}
			}
		}
	}
	return checkForArrowMat(A, M + 1, row + 1, col - 1);
}
void swap(int* v1, int* v2) {
	int temp;
	temp = *v1;
	*v1 = *v2;
	*v2 = temp;
}
int findOrderStatistic(int* arr, int p, int r, int i) {
	int x, j, k;
	if (p >= r) return *(arr + p);
	x = *(arr + r);
	k = p - 1;
	for (j = p; j < r; j++) {
		if (*(arr + j) < x) {
			k++;
			swap(&(*(arr + j)), &(*(arr + k)));
		}
	}
	swap(&(*(arr + k + 1)), &(*(arr + r)));
	if (k+1 > i-1) return findOrderStatistic(arr, p, k, i);
	if (k+1 < i-1) return findOrderStatistic(arr, k+1, r, i-k+2);
	return *(arr + k + 1);
}
int findMissingNumber(int* arr, int first, int last, int size) {
	int median;
	median = (first+last)/2;
	findOrderStatistic(arr,first,last,median);
	if (size == 1) {
		if (*(arr+first) == first)
			return first+1;
		return first;
	}
	if (*(arr+median) == median) return findMissingNumber(arr,median+1,last,size/2);
	return findMissingNumber(arr,first,median-1,size/2);
}

int mp(int* M, int* p, int k, int i, int count) {
	int chosen, notChosen, tempChoice;
	if (i < 0) return 0;
	if (i == 0) return *p;
	tempChoice = mp(M,p,k,i-1,1);
	chosen = *(p+i) +  tempChoice;
	notChosen = tempChoice;
	if (chosen > notChosen) {
		if (((*(M+i))-(*(M+i-1))) < k)
			return mp(M,p,k,i-count,++count);
		count = 1;
		return chosen;
	}
	return notChosen;
}

void printNumOfHousesForMaxProfit(int* M, int* p, int k, int i) {
	if (i > -1) {
		int chosen, notChosen;
		chosen = mp(M,p,k,i,1);
		notChosen = mp(M,p,k,i-1,1);
		if (chosen > notChosen) {
			printf("%d\n",*(M+i));
			printNumOfHousesForMaxProfit(M,p,k,i-1);
		}
		else
			printNumOfHousesForMaxProfit(M,p,k,i-1);
	}
}
