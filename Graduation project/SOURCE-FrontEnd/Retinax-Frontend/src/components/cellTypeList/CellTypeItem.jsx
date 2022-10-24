// import React from "react";
// import "./CellTypeItem.css";
// import useHttp from "../../shared/hooks/useHttp.jsx";
// import {
//   addCellInstance,
//   deleteCellType,
// } from "../../shared/services/CellService.jsx";
// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faCirclePlus, faTrashAlt } from "@fortawesome/free-solid-svg-icons";

// const CellTypeItem = (props) => {
//   const { cellType } = props;
//   const addCellInstanceReq = useHttp(addCellInstance);
//   const deleteCellTypeReq = useHttp(deleteCellType);

//   const addCellClickHandler = (event) => {
//     event.preventDefault();
//     addCellInstanceReq.sendRequest(cellType.id);
//   };
//   const deleteCellTypeClickHandler = (event) => {
//     event.preventDefault();
//     deleteCellTypeReq.sendRequest(cellType.id);
//   };

//   return (
//     <li>
//       <div className="cell-type-item-contents">
//         Cell Type Name: {cellType.name} <br />
//         Cell Id: {cellType.id}
//         <br />
//         Transform Type: {cellType.transformType}
//         <br />
//         Function: {cellType.function.expression}
//         <br />
//       </div>
//       <div className="cell-type-item-actions">
//         <a
//           className="cell-type-item-actions__add"
//           href="/"
//           onClick={addCellClickHandler}
//         >
//           <FontAwesomeIcon icon={faCirclePlus} />
//           <span className="tooltiptext">Add Cell Instance</span>
//         </a>
//         <a
//           className="cell-type-item-actions__delete"
//           href="/"
//           onClick={deleteCellTypeClickHandler}
//         >
//           <FontAwesomeIcon icon={faTrashAlt} />
//           <span className="tooltiptext">Delete Cell Type</span>
//         </a>
//       </div>
//     </li>
//   );
// };

// export default CellTypeItem;
