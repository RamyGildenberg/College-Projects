import React, { useRef } from "react";
import "./SimulateGraph.css";

const SimulateCellItem = (props) => {
  const { index, cell, updateCell } = props;

  const inputRef = useRef();
  const inputChangeHandler = () =>
    updateCell(index, {
      ...cell,
      userInput: inputRef.current.value.split(","),
    });

  return (
    <>
      <label>Cell Id: {cell.id}</label>
      <input ref={inputRef} onChange={inputChangeHandler} />
    </>
  );
};

export default SimulateCellItem;
