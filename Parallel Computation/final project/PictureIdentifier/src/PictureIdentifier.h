/*
 ============================================================================
 Name        : PictureIdentifier.h
 Author      : Ramy Gildenberg
 Last Update : 04/08/2022 02:00
 ============================================================================
 */
#ifndef PICTUREIDENTIFIER_H_
#define PICTUREIDENTIFIER_H_
#include <mpi.h>

//a structure to hold all the info about both the object and the picture ( both use the same fields )
typedef struct PicAndObj
{
	int *data;
	int ID;
	int dimension;

} PicAndObj;

void scan(int *resultsArr,int *flagArr,int objectAmount,PicAndObj pic, PicAndObj *obj, int indexOfPic);
void readFile(const char *fileName);
void mpiSendInitialInfo(float diffValue,int picAmount,int objectAmount,int firstHalfAmount,int secondHalfAmount);
void mpiSendPicInfo(int PicAmount,int secondHalfAmount);
void mpiSendObjInfo(int objectAmount);
void mpiSendInfoToMaster(int *results,int *flagArr,int firstHalfAmount,int secondHalfAmount);

#endif /* PICTUREIDENTIFIER_H_ */
