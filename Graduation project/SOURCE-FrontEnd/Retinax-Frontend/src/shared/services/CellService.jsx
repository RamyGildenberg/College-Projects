import { AddData, GetData, DeleteData } from "./WebService.jsx";

export const getCellTypes = () => GetData("build/cellTypes");
export const getCellInstances = () => GetData("build/cellInstances/");
export const addCellType = (myCell) => AddData(myCell, "build/createCellType");
export const addCellInstance = (cellTypeId) =>
  AddData({ cellTypeId, x: 3.6, y: 2.7 }, "build/addCell");
export const addConnection = (myCell) => AddData(myCell, "build/connect");
export const deleteCellInstance = (myCell) =>
  DeleteData("build/cellInstances/" + myCell);
export const deleteCellType = (myCell) =>
  DeleteData("build/cellTypes/" + myCell);
