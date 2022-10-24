#include "mpi.h" 
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

#define FILE_NAME "points.txt"

// This function simulates heavy computations,
// its run time depends on x, y and param values
// DO NOT change this function!!

double heavy(double x, double y, int param) {
	double center[2] = { 0.4, 0.2 };
	int i, loop, size = 1, coeff = 10000;
	double sum = 0, dx, dy, radius = 0.2 * size;
	int longLoop = 1000, shortLoop = 1;
	double pi = 3.14;
	dx = (x - center[0]) * size;
	dy = (y - center[1]) * size;
	loop = (sqrt(dx * dx + dy * dy) < radius) ? longLoop : shortLoop;

	for (i = 1; i < loop * coeff; i++)
		sum += cos(2 * pi * dy * dx + 0.1) * sin(exp(10 * cos(pi * dx))) / i;

	return sum;
}

// Reads data from from the file and allocates the array of points
// The first line contains a parameter
// The second line contains a number of points defined.
// Following lines contain two doubles each - point coordinates x, y

double* readFromFile(const char *fileName, int *numberOfPoints, int *param) {
	FILE *fp;
	double *points;

	// Open file for reading points
	if ((fp = fopen(fileName, "r")) == 0) {
		printf("cannot open file %s for reading\n", fileName);
		exit(0);
	}

	// Param
	fscanf(fp, "%d", param);

	// Number of points
	fscanf(fp, "%d", numberOfPoints);

	// Allocate array of points end Read data from the file
	points = (double*) malloc(2 * *numberOfPoints * sizeof(double));
	if (points == NULL) {
		printf("Problem to allocate memory\n");
		exit(0);
	}
	for (int i = 0; i < *numberOfPoints; i++) {
		fscanf(fp, "%le %le", &points[2 * i], &points[2 * i + 1]);
	}

	fclose(fp);

	return points;
}

double dynamic_master(int param, double points[], int *indexes,
		int numberOfPoints, int my_rank, int p, int tag, MPI_Status status) {
	int closeParams[2] = { -1, -1 };
	int answerCount = 0;
	double answer = 0, temp = 0;
	int close = 0;
	for (int i = 1; i < p; i++) {
		indexes[0] += 2;
		indexes[1] += 2;
		MPI_Send(indexes, 2, MPI_INT, i, tag, MPI_COMM_WORLD);
	}
	do {
		MPI_Recv(&temp, 1, MPI_DOUBLE, MPI_ANY_SOURCE, tag, MPI_COMM_WORLD,
				&status);
		answer = fmax(answer, temp);
		answerCount++;
		indexes[0] += 2;
		indexes[1] += 2;
		if (indexes[1] - 1 == numberOfPoints * 2) {
			indexes[0] -= 2;
			indexes[1] -= 2;
			MPI_Send(closeParams, 2, MPI_INT, status.MPI_SOURCE, tag,
					MPI_COMM_WORLD);
			close++;
		} else {
			MPI_Send(indexes, 2, MPI_INT, status.MPI_SOURCE, tag,
			MPI_COMM_WORLD);
		}
	} while (close < p - 1);

	return answer;
}

void dynamic_slave(int param, double points[], int *indexes, int numberOfPoints,
		int my_rank, int p, int tag, MPI_Status status) {
	double answer = 0, temp = 0;
	int close = 0;
	while (!close) {
		MPI_Recv(indexes, 2, MPI_INT, 0, tag, MPI_COMM_WORLD, &status);
		if (indexes[0] == -1 || indexes[1] == -1) {
			close = 1;
		} else {
			answer = heavy(points[indexes[0]], points[indexes[1]], param);
			MPI_Send(&answer, 1, MPI_DOUBLE, 0, tag, MPI_COMM_WORLD);
		}
	}

}

double dynamic_parallel(int param, double points[], int numberOfPoints,
		int my_rank, int p, int tag, MPI_Status status) {
	int indexes[2] = { -2, -1 };
	if (my_rank != 0) {
		dynamic_slave(param, points, indexes, numberOfPoints, my_rank, p, tag,
				status);
		return -1;
	} else {
		return dynamic_master(param, points, indexes, numberOfPoints, my_rank,
				p, tag, status);
	}

}

double static_master(int param, double points[], int *indexes,
		int numberOfPoints, int my_rank, int p, int tag, MPI_Status status) {
	int slave_size = (numberOfPoints * 2) / (p - 1);
	for (int i = 1; i < p - 1; i++) {
		indexes[0] = (i - 1) * slave_size;
		indexes[1] = (indexes[0] + slave_size) - 1;
		MPI_Send(indexes, 2, MPI_INT, i, tag, MPI_COMM_WORLD);
	}
	indexes[0] = indexes[1];
	indexes[1] = numberOfPoints - 1;
	MPI_Send(indexes, 2, MPI_INT, (p - 1), tag, MPI_COMM_WORLD);
	double temp = 0, answer = 0;
	for (int i = 1; i < p; i++) {
		MPI_Recv(&temp, 1, MPI_DOUBLE, MPI_ANY_SOURCE, tag, MPI_COMM_WORLD,
				&status);
		answer = fmax(answer, temp);
	}
	return answer;
}

void static_slave(int param, double points[], int *indexes, int numberOfPoints,
		int my_rank, int p, int tag, MPI_Status status) {
	double answer = 0, temp = -1;
	MPI_Recv(indexes, 2, MPI_INT, 0, tag, MPI_COMM_WORLD, &status);
	for (int i = indexes[0]; i < indexes[1]; i++) {
		answer = heavy(points[i * 2], points[i * 2 + 1], param);
		if (answer > temp)
			temp = answer;
	}
	MPI_Send(&temp, 1, MPI_DOUBLE, 0, tag, MPI_COMM_WORLD);
}

double static_parallel(int param, double points[], int numberOfPoints,
		int my_rank, int p, int tag, MPI_Status status) {
	int indexes[2] = { -1, -1 };
	if (my_rank != 0) {
		static_slave(param, points, indexes, numberOfPoints, my_rank, p, tag,
				status);
		return -1;
	} else {
		return static_master(param, points, indexes, numberOfPoints, my_rank, p,
				tag, status);
	}
}

int main(int argc, char *argv[]) {
	int my_rank, p, tag = 0;
	double answer = 0, begin, end;
	int numberOfPoints = 10;
	double *points, x, y;
	int param;
	MPI_Status status; /* return status for receive */
	/* start up MPI */
	MPI_Init(&argc, &argv);
	/* find out process rank */
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	/* find out number of processes */
	MPI_Comm_size(MPI_COMM_WORLD, &p);

	// Perform heavy sequential computation
	MPI_Bcast(&param, 4, MPI_INT, 0, MPI_COMM_WORLD);
	// Read points from the file
	points = readFromFile(FILE_NAME, &numberOfPoints, &param);
	// Find maximum value of heavy calculated for each point
	begin = MPI_Wtime();
	if (p == 1) {
		x = points[0];
		y = points[1];
		answer = heavy(x, y, param);
		// Perform heavy sequential computation
		for (int i = 1; i < numberOfPoints; i++) {
			x = points[2 * i];
			y = points[2 * i + 1];
			answer = fmax(answer, heavy(x, y, param));
		}
	} else if (p > 1) {
		//uncomment the function u want to use 
		answer = dynamic_parallel(param, points, numberOfPoints, my_rank, p,tag, status);
		//answer = static_parallel(param, points, numberOfPoints,my_rank,p,tag,status);
	}
	end = MPI_Wtime();

	if (my_rank == 0) {
		printf("answer = %f\n", answer);
		printf("time = %f\n", end - begin);
	}
	MPI_Finalize();

	return 1;

}

