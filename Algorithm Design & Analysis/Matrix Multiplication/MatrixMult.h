/*
 * MatrixMult.h
 *
 *  Created on: May 6, 2019
 *      Author: tamir-ubuntu
 */

#ifndef MATRIXMULT_H_
#define MATRIXMULT_H_

void init(int *p, int n);
void print_mat(int** mat, int size);
int memorized_matrix_chain(int *p, int size);
int lookup_chain(int *p, int i, int j);
void print_optimal(int i, int j);

#endif /* MATRIXMULT_H_ */
