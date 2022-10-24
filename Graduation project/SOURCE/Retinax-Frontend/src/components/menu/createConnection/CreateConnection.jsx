import { useState } from "react";
import { useEffect } from "react";
import useHttp from "../../../shared/hooks/useHttp.jsx";
import { addConnection } from "../../../shared/services/CellService.jsx";
import "./CreateConnection.css";

const CreateConnection = (props) => {
  const [myCell, setMyCell] = useState({ sourceCell: {}, destinationCell: {} });
  const addConnectionReq = useHttp(addConnection);
  const { closeWindow } = props;

  useEffect(() => {
    if (addConnectionReq.status !== "COMPLETED" || addConnectionReq.error)
      return;
    closeWindow();
  }, [addConnectionReq.status, addConnectionReq.error, closeWindow]);

  const stateHandler = (str, val) => {
    setMyCell((prevState) => {
      return {
        ...prevState,
        [str]: val,
      };
    });
  };

  const stateSourceHandler = (str, val) => {
    setMyCell((prevState) => {
      return {
        ...prevState,
        sourceCell: {
          ...prevState.sourceCell,
          [str]: val,
        },
      };
    });
  };

  const stateDestinationHandler = (str, val) => {
    setMyCell((prevState) => {
      return {
        ...prevState,
        destinationCell: {
          ...prevState.destinationCell,
          [str]: val,
        },
      };
    });
  };
  const submitHandler = async (event) => {
    event.preventDefault();
    if (myCell.delay === "0" || myCell.delay === "1") {
      addConnectionReq.sendRequest(myCell);
    } else alert("Delay Must Be Either 0 Or 1. You Entered " + myCell.delay);
    // alert(addConnectionReq.error);
  };

  return (
    <form onSubmit={submitHandler}>
      <div className="main-popup">
        <div className="popup-inner">
          <div className="container">
            <label>Create New Connection</label>
            <label>Source Id:</label>
            <input
              type="text"
              // value={myCell.stateSourceHandler.id}
              onChange={(e) => {
                stateSourceHandler("id", e.target.value);
              }}
              placeholder="1"
            />
            <br />
            <br />
            <label>Destination Id:</label>
            <input
              type="text"
              // value={myCell.createDestinationRequest.id}
              onChange={(e) => {
                stateDestinationHandler("id", e.target.value);
              }}
              placeholder="2"
            />
            <label>Delay:</label>
            <input
              type="text"
              value={myCell.name}
              onChange={(e) => {
                stateHandler("delay", e.target.value);
              }}
              placeholder="Either 0 or 1"
            />
            <label>Input:</label>
            <input
              type="text"
              value={myCell.name}
              onChange={(e) => {
                stateHandler("inputFunctionVariable", e.target.value);
              }}
              placeholder="Example: x"
            />
            <br />
            <br />
            <div className="accept">
              <button type="submit">Create Cell Type</button>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};

export default CreateConnection;
