import { AddData } from "./WebService.jsx";

export const addGraph = (myGraph) => AddData(myGraph, "build/createSubGraph");
export const cloneGraph = (val) =>
  AddData(undefined, "build/cloneSubGraph/" + val);
export const simulateGraph = (val) => AddData(val, "simulate");
