

#ifndef FUNC_H_
#define FUNC_H_
#define N 5
typedef char mat[N][N];
typedef enum {
	FALSE, TRUE
} boolean;
mat A;

int count(int num, int sum);
void findRecSequence(const int* arr, int size, int p, int* m, int* x, int temp);
int check(mat A);
int checkForArrowMat(mat A, int M, int row, int col);
void swap(int* v1, int* v2);
int findOrderStatistic(int* arr, int p, int r, int i);
int findMissingNumber(int* arr, int first, int last, int size);
int mp(int* M, int* p, int k, int i, int count);
void printNumOfHousesForMaxProfit(int* M, int* p, int k, int i);
#endif /* FUNC_H_ */
