import { useState } from "react";
import { useEffect } from "react";
import useHttp from "../../../shared/hooks/useHttp.jsx";
import { deleteCellInstance } from "../../../shared/services/CellService.jsx";
import "./DeleteCell.css";

const DeleteCell = (props) => {
  const [myCell, setMyCell] = useState({});
  const deleteCellInstanceReq = useHttp(deleteCellInstance);
  const { closeWindow } = props;

  useEffect(() => {
    if (
      deleteCellInstanceReq.status !== "COMPLETED" ||
      deleteCellInstanceReq.error
    )
      return;
    closeWindow();
  }, [deleteCellInstanceReq.status, deleteCellInstanceReq.error, closeWindow]);

  const stateHandler = (str, val) => {
    setMyCell((prevState) => {
      return {
        ...prevState,
        [str]: val,
      };
    });
  };

  const submitHandler = async (event) => {
    event.preventDefault();
    deleteCellInstanceReq.sendRequest(myCell.cellInstanceID);
  };

  return (
    <form onSubmit={submitHandler}>
      <div className="main-popup">
        <div className="popup-inner">
          <div className="container">
            <label>Delete Cell</label>
            <label>Cell Instance Id:</label>
            <input
              type="text"
              value={myCell.id}
              onChange={(e) => {
                stateHandler("cellInstanceID", e.target.value);
              }}
              placeholder="1"
            />
            <br></br>
            <div className="accept">
              <button type="submit">Delete Cell</button>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};

export default DeleteCell;
