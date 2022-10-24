// import React, { useEffect, useState } from "react";
// import useHttp from "../../shared/hooks/useHttp.jsx";
// import { getCellTypes } from "../../shared/services/CellService.jsx";
// import CellTypeItem from "./CellTypeItem.jsx";
// import "./CellTypeList.css";

// const CellTypeList = () => {
//   const { sendRequest, data } = useHttp(getCellTypes);

//   const [cellTypes, setCellTypes] = useState([]);

//   useEffect(() => {
//     sendRequest();
//   }, [sendRequest]);

//   useEffect(() => {
//     if (!data) return;
//     setCellTypes(data);
//   }, [data]);

//   return (
//     <div className="cell-type-list-container">
//       <h1 className="cell-type-list-title"> Cell Type List</h1>
//       <ul>
//         {cellTypes.map((cellType) => (
//           <CellTypeItem cellType={cellType} key={cellType.id} />
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default CellTypeList;
