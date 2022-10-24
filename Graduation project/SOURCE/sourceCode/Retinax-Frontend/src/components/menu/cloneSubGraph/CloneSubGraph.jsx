import { useState } from "react";
import { useEffect } from "react";
import useHttp from "../../../shared/hooks/useHttp.jsx";
import { cloneGraph } from "../../../shared/services/GraphService.jsx";
import "./CloneSubGraph.css";

const CreateSubGraph = (props) => {
  const [myGraph, setMyGraph] = useState({});
  const cloneGraphReq = useHttp(cloneGraph);
  const { closeWindow } = props;

  useEffect(() => {
    if (cloneGraphReq.status !== "COMPLETED" || cloneGraphReq.error) return;
    closeWindow();
  }, [cloneGraphReq.status, cloneGraphReq.error, closeWindow]);

  const stateHandler = (val) => {
    setMyGraph((prevState) => {
      return {
        ...prevState,
        val,
      };
    });
  };

  const submitHandler = async (event) => {
    event.preventDefault();
    alert("I Clicked IT");
    cloneGraphReq.sendRequest(myGraph.val);
  };

  return (
    <form onSubmit={submitHandler}>
      <div className="main-popup">
        <div className="popup-inner">
          <div className="container">
            <label>Clone Sub Graph</label>
            <label>Graph Id:</label>
            <input
              type="text"
              value={myGraph.id}
              onChange={(e) => {
                stateHandler(e.target.value);
              }}
              placeholder="1"
            />
            <br />
            <br />
            <div className="accept">
              <button type="submit">Create Sub Graph</button>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};

export default CreateSubGraph;
