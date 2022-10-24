/*
 ============================================================================
 Name        : PictureIdentifier.c
 Author      : Ramy Gildenberg
 Last Update : 04/08/2022 02:00
 ============================================================================
 */
#include "PictureIdentifier.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include "mpi.h"
#include <omp.h>

int picAmount, objectAmount, firstHalfAmount, secondHalfAmount,PicFinderIndexer;
float matchingValue;

// two arrays to hold all of the pictures and objects
PicAndObj *picture, *object;

void readFile(const char *fileName) {
	FILE *filePath;
	filePath = fopen(fileName, "r");
	fscanf(filePath, "%f", &matchingValue);
	fscanf(filePath, "%d", &picAmount);

	//allocate memory for the pictures array that will hold all the pictures
	picture = (PicAndObj*) malloc(picAmount * sizeof(PicAndObj));

	//give each picture object its own id and size
	for (int i = 0; i < picAmount; i++) {
		fscanf(filePath, "%d", &picture[i].ID);
		fscanf(filePath, "%d", &picture[i].dimension);

		//allocate memory based on the size of the picture
		picture[i].data = (int*) malloc(
				picture[i].dimension * picture[i].dimension * sizeof(int));

		//read the picture data into the data field
		for (int j = 0; j < picture[i].dimension * picture[i].dimension; j++)
			fscanf(filePath, "%d", &picture[i].data[j]);
	}

	fscanf(filePath, "%d", &objectAmount);

	//allocate memory for the objects array that will hold all the objects
	object = (PicAndObj*) malloc(objectAmount * sizeof(PicAndObj));

	//give each object its id and size
	for (int i = 0; i < objectAmount; i++) {
		fscanf(filePath, "%d", &object[i].ID);
		fscanf(filePath, "%d", &object[i].dimension);

		//allocate memory based on the size of the object
		object[i].data = (int*) malloc(
				object[i].dimension * object[i].dimension * sizeof(int));

		//read the object data into the data field
		for (int j = 0; j < object[i].dimension * object[i].dimension; j++)
			fscanf(filePath, "%d", &object[i].data[j]);
	}

	fclose(filePath);

}

//the scan function that will go over the all the objects , give each thread an object , and each
//thread will compare it with the function matching against all the pictures
void scan(int *resultsArr, int *flagArr, int objectAmount, PicAndObj pic,PicAndObj *obj, int indexOfPic) {
	float currentMatchingValue;

	//here we use openMP , each thread will take an object and scan it versus all the pictures ,
	//the currentMatchingValue is private in order to keep the values we compare isolated from other threads
	//each thread will handle one object and match it against a picture, this way the comparison is done simultaneously,
	//thus , parallelizing the work load

#pragma omp parallel for private(currentMatchingValue)

	//go over all the objects
	for (int objectCounter = 0; objectCounter < objectAmount; objectCounter++) {

		//go over the entire picture but watch out for the edges ,calculated by reducing the dimension of the object from the dimension of the picture
		for (int picRow = 0; picRow < (pic.dimension - obj[1].dimension + 1);
				picRow++) {
			for (int picCol = 0;
					picCol < (pic.dimension - obj[1].dimension + 1); picCol++) {

				//check if the chosen picture has found and object inside itself, if not , continue onwards into the scan phase
				if (flagArr[indexOfPic] != 1) {

					//reset the value of currentMatchingValue for each thread
					currentMatchingValue = 0;

					//go over the object which we are looking for in the pictures
					for (int objRow = 0; objRow < obj[objectCounter].dimension;objRow++) {
						for (int objCol = 0;objCol < obj[objectCounter].dimension;objCol++) {
							//add the value of of the difference and keep accumulating it , we go over the entire picture
							//to try and find the object
							currentMatchingValue +=(abs(
											pic.data[ (objRow * pic.dimension + objCol) + (picCol + picRow* pic.dimension)]
											- obj[objectCounter].data[objRow * obj[objectCounter].dimension + objCol])
										/ (float) pic.data[(objRow * pic.dimension + objCol) + (picCol + picRow * pic.dimension)]);

						}
					}
					//check if the summed value of the difference is below the threshold in order
					//for the picture to consider the object as found
					//if the object is found , it enters the info into a results array , where the first cell is the number of the
					//picture ,the second cell is the object number and the next two cells are the row and column
					//where the object was found , respectively
					if (currentMatchingValue < matchingValue) {
										printf("Picture %d found object %d on coordinates:(%d,%d)\n",pic.ID,obj[objectCounter].ID, picRow, picCol);
										resultsArr[indexOfPic] = pic.ID;
										resultsArr[indexOfPic + 1] = obj[objectCounter].ID;
										resultsArr[indexOfPic + 2] = picRow;
										resultsArr[indexOfPic + 3] = picCol;
										flagArr[indexOfPic] = 1;
					}
				}

			}
		}
	}
}

void mpiSendInitialInfo(float diffValue, int picAmount, int objectAmount,
		int firstHalfAmount, int secondHalfAmount) {
	MPI_Send(&diffValue, 1, MPI_DOUBLE, 1, 0, MPI_COMM_WORLD);
	MPI_Send(&picAmount, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
	MPI_Send(&objectAmount, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
	MPI_Send(&firstHalfAmount, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
	MPI_Send(&secondHalfAmount, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
}

void mpiSendPicInfo(int PicAmount, int secondHalfAmount) {
	for (int i = picAmount - secondHalfAmount; i < picAmount; i++) {
		MPI_Send(&picture[i].ID, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
		MPI_Send(&picture[i].dimension, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
		MPI_Send(picture[i].data, picture[i].dimension * picture[i].dimension,
				MPI_INT, 1, 0, MPI_COMM_WORLD);
	}

}
void mpiSendObjInfo(int objectAmount) {
	for (int i = 0; i < objectAmount; i++) {
		MPI_Send(&object[i].ID, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
		MPI_Send(&object[i].dimension, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
		MPI_Send(object[i].data, object[i].dimension * object[i].dimension,
				MPI_INT, 1, 0, MPI_COMM_WORLD);
	}

}
void mpiSendInfoToMaster(int *results, int *flagArr, int firstHalfAmount,
		int secondHalfAmount) {
	MPI_Send(results, secondHalfAmount * 4, MPI_INT, 0, 0, MPI_COMM_WORLD);
	MPI_Send(flagArr , secondHalfAmount, MPI_INT, 0, 0,
			MPI_COMM_WORLD);
}

//we will be using 2 processes , each process will receive half of the pictures to work on, this dividing the workload
//each process will create a thread pool to work on each object separately and compare them with the pictures, thus reducing the
//workload even more
int main(int argc, char *argv[]) {
	int size, rank;
	MPI_Status status;
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	double timeStart=MPI_Wtime();
	//all the actions the master will do
	if (rank == 0) {
		readFile("../input __ NxN lines with 1 member.txt");
		//calculate how many pictures will go to each process
		secondHalfAmount = picAmount / 2;
		firstHalfAmount = picAmount - secondHalfAmount;

		//send all the info about the difference value that determines if the object is in the picture ,amount of pictures and amount of objects
		mpiSendInitialInfo(matchingValue, picAmount, objectAmount,
				secondHalfAmount, firstHalfAmount);

		//send half of the pictures and their info to the other process
		mpiSendPicInfo(picAmount, secondHalfAmount);

		//send all of the objects and their info to the other process to inspect it against the pictures it has received
		mpiSendObjInfo(objectAmount);

		//allocating memory for the results array , we multiply by 4 because we want 4 fields , picture ID , Object ID , and the position of the found object in the picture (i,j)
		int *resultsArr = (int*) malloc(picAmount * 4 * sizeof(int));

		//allocate memory for the array that will hold flags of pictures finding objects in them , according to their ID-array index
		int *flagArr = (int*) malloc(picAmount * sizeof(int));
		for (int i = 0; i < firstHalfAmount; i++) {
			scan(resultsArr, flagArr, objectAmount, picture[i], object, i);
		}

		//allocate memory for results coming in from the other process
		int *additionalResultsArr = (int*) malloc(
				secondHalfAmount * 4 * sizeof(int));
		//allocate memory for flag array for pictures that found objects coming in from the other process
		int *additionalFlagArr = (int*) malloc(secondHalfAmount * sizeof(int));

		//Receive both mentioned arrays
		MPI_Recv(additionalResultsArr, secondHalfAmount * 4, MPI_INT, 1, 0,
				MPI_COMM_WORLD, &status);
		MPI_Recv(additionalFlagArr, secondHalfAmount, MPI_INT, 1, 0,
				MPI_COMM_WORLD, &status);


		//the next 2 for loops are for gathering all the info about which pictures found objects and where they found them into one array
		for (int i = firstHalfAmount * 4; i < picAmount * 4; i++) {
			resultsArr[i] = additionalResultsArr[i - firstHalfAmount * 4];
		}
		for (int i = firstHalfAmount; i < picAmount; i++) {
			flagArr[i] = additionalFlagArr[i - firstHalfAmount];
		}


		//Writing the file - this is where the output file is written===================================
		FILE *output;
		output = fopen("../output.txt", "w");
		for (int i = 0; i < picAmount; i++) {
			if (flagArr[i] == 1) {
				{
					//using the skipInterval is needed in order to progress the pointer on the array to the next batch of result information , since it is structed as picture id ,
					//object id and two indexes

					int skipInterval = 4;
					fprintf(output,
							"Picture %d found Object %d in Position(%d,%d)\n",
							resultsArr[i * skipInterval],
							resultsArr[i * skipInterval + 1],
							resultsArr[i * skipInterval + 2],
							resultsArr[i * skipInterval + 3]);
				}
			} else {
				fprintf(output, "Picture %d No Object were found\n", i);
			}
		}
		fclose(output);
	}

	//all the actions the other machine will do
	if (rank != 0) {

		//Receive all the info that was sent from the master process
		MPI_Recv(&matchingValue, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&picAmount, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&objectAmount, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&secondHalfAmount, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&firstHalfAmount, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		//allocate memory just like when it read the file
		picture = (PicAndObj*) malloc(secondHalfAmount * sizeof(PicAndObj));
		object = (PicAndObj*) malloc(objectAmount * sizeof(PicAndObj));

		//Receive all the pictures data from the master according to the amount of pictures this process receives
		for (int i = 0; i < secondHalfAmount; i++) {
			MPI_Recv(&picture[i].ID, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(&picture[i].dimension, 1, MPI_INT, 0, 0, MPI_COMM_WORLD,
					&status);
			//allocate space for the picture itself before receiving it according to its dimension
			picture[i].data = (int*) malloc(
					(picture[i].dimension * picture[i].dimension)
							* sizeof(int));
			MPI_Recv(picture[i].data,
					picture[i].dimension * picture[i].dimension, MPI_INT, 0, 0,
					MPI_COMM_WORLD, &status);
		}

		//receive all the objects data from the master, since the master has all the info
		for (int i = 0; i < objectAmount; i++) {
			MPI_Recv(&object[i].ID, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(&object[i].dimension, 1, MPI_INT, 0, 0, MPI_COMM_WORLD,
					&status);
			//allocate space for the object itself before receiving it according to its dimensions
			object[i].data = (int*) malloc(
					(object[i].dimension * object[i].dimension) * sizeof(int));
			MPI_Recv(object[i].data, object[i].dimension * object[i].dimension,
					MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		}

		//allocating memory for the results array , we multiply by 4 because we want 4 fields , picture ID , Object ID , and the position of the found object in the picture (i,j)
		int *resultsArr = (int*) malloc(secondHalfAmount * 4 * sizeof(int));

		//allocate memory for the array that will hold flags of pictures finding objects in them , according to their ID-array index
		int *flagArr = (int*) malloc(secondHalfAmount * sizeof(int));
		for (int i = 0; i < secondHalfAmount; i++) {
			scan(resultsArr, flagArr, objectAmount, picture[i], object, i);
		}
		//send all the calculated info from this process back to the master
		mpiSendInfoToMaster(resultsArr, flagArr, firstHalfAmount,secondHalfAmount);
	}
	double endTime=MPI_Wtime();
	if(rank==0)
		printf("Time taken to calculate:%f",endTime-timeStart);
	MPI_Finalize();
	return 0;
}

