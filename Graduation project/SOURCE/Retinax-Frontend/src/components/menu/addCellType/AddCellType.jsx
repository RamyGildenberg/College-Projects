import React, { useState } from "react";
import { useEffect } from "react";
import useHttp from "../../../shared/hooks/useHttp.jsx";
import { addCellType } from "../../../shared/services/CellService.jsx";
import "./AddCellType.css";

const AddCell = (props) => {
  const [myCell, setMyCell] = useState({ createFunctionRequest: {} });
  const addCellTypeReq = useHttp(addCellType);
  const { closeWindow } = props;

  useEffect(() => {
    if (addCellTypeReq.status !== "COMPLETED" || addCellTypeReq.error) return;
    closeWindow();
  }, [addCellTypeReq.status, addCellTypeReq.error, closeWindow]);

  const stateHandler = (str, val) => {
    setMyCell((prevState) => {
      return {
        ...prevState,
        [str]: val,
      };
    });
  };

  const stateFunctionHandler = (str, val) => {
    setMyCell((prevState) => {
      return {
        ...prevState,
        createFunctionRequest: {
          ...prevState.createFunctionRequest,
          [str]: val,
        },
      };
    });
  };

  const submitHandler = async (event) => {
    event.preventDefault();
    addCellTypeReq.sendRequest(myCell);
    setMyCell({ createFunctionRequest: {} });
  };

  return (
    <form className="main-popup" onSubmit={submitHandler}>
      <div className="popup-inner">
        <div className="container">
          <label>Creating New Cell Type</label>
          <label>Cell Type:</label>
          <input
            type="text"
            value={myCell.name}
            onChange={(e) => {
              stateHandler("name", e.target.value);
            }}
            placeholder="Example: A"
          />
          <br />
          <br />
          <label>Function Expression:</label>
          <input
            type="text"
            value={myCell.createFunctionRequest.expression}
            onChange={(e) => {
              stateFunctionHandler("expression", e.target.value);
            }}
            placeholder="x + y + z"
          />
          <br />
          <br />
          <label>Variables:</label>
          <input
            type="text"
            value={myCell.createFunctionRequest.variables}
            onChange={(e) => {
              const variables = e.target.value.replace(/\s/g, "").split(",");
              stateFunctionHandler("variables", variables);
            }}
            placeholder="x, y, z"
          />
          <br />
          <br />
          <label>Transform Type:</label>
          <div className="row">
            <button
              type="button"
              className={
                myCell.transformType === "ANALOG_TO_ANALOG" ? "btn-active" : ""
              }
              onClick={() => {
                stateHandler("transformType", "ANALOG_TO_ANALOG");
              }}
            >
              Analog to Analog
            </button>
            <button
              type="button"
              className={
                myCell.transformType === "ANALOG_TO_DIGITAL" ? "btn-active" : ""
              }
              onClick={() => {
                stateHandler("transformType", "ANALOG_TO_DIGITAL");
              }}
            >
              Analog to Digital
            </button>
            <button
              type="button"
              className={
                myCell.transformType === "DIGITAL_TO_ANALOG" ? "btn-active" : ""
              }
              onClick={() => {
                stateHandler("transformType", "DIGITAL_TO_ANALOG");
              }}
            >
              Digital to Analog
            </button>
            <button
              type="button"
              className={
                myCell.transformType === "DIGITAL_TO_DIGITAL"
                  ? "btn-active"
                  : ""
              }
              onClick={() => {
                stateHandler("transformType", "DIGITAL_TO_DIGITAL");
              }}
            >
              Digital to Digital
            </button>
          </div>
          <br />
          <br />
          <div className="accept">
            <button type="submit">Create Cell Type</button>
          </div>
        </div>
      </div>
    </form>
  );
};

export default AddCell;
