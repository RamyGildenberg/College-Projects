import React, { useState } from "react";
import { useEffect } from "react";
import useHttp from "../../../shared/hooks/useHttp.jsx";
import { getCellInstances } from "../../../shared/services/CellService.jsx";
import SimulateCellItem from "./SimulateCellItem.jsx";
import "./SimulateGraph.css";

const SimulateGraph = (props) => {
  const [cells, setCells] = useState({ inCells: [], outCells: [] });
  const { sendRequest, data } = useHttp(getCellInstances);
  //  {
  //   userInput: {"inputCellId":[]},
  //   maxTime:6,
  //   OutputCells: [{"OutputCellId":5}]
  //   }
  // const { closeWindow } = props;

  useEffect(() => {
    sendRequest();
  }, [sendRequest]);

  useEffect(() => {
    if (!data) return;
    const inCells = [];
    const outCells = [];
    data.forEach((cell) =>
      cell.cellType.transformType === "INPUT_TO_ANALOG"
        ? inCells.push(cell)
        : outCells.push(cell)
    );
    setCells({ inCells, outCells });
  }, [data]);

  const updateCell = (index, newCell) => {
    const newCells = [...cells.inCells];
    newCells[index] = newCell;
    setCells((prevState) => ({ ...prevState, inCells: newCells }));
  };
  const submitHandler = async (event) => {
    const mySimGraph = [];
    cells.inCells.forEach((cell) => {
      // console.log(cell.cellType.id);
      mySimGraph.push(cell.cellType.id);
    });
    console.log(mySimGraph);
    event.preventDefault();
  };

  return (
    <form className="main-popup" onSubmit={submitHandler}>
      <div className="popup-inner">
        <div className="container">
          <label>Start Simulation</label>
          {cells.inCells.map((cell, index) => (
            <SimulateCellItem
              cell={cell}
              index={index}
              key={cell.id}
              updateCell={updateCell}
            />
          ))}
          <br></br>
          <div className="accept">
            <button type="submit">Simulate</button>
          </div>
        </div>
      </div>
    </form>
  );
};

export default SimulateGraph;
