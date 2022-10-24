package com.retinaX.services.buildNetwork.cellInstance;

import com.retinaX.coreAPI.buildNetworkAPI.requests.ConnectCellsRequest;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellType;
import com.retinaX.entities.Connection;


import java.util.List;

public interface CellInstanceService {
    CellInstance createCellInstance(CellType cellType, double x, double y);

    CellInstance getCellInstance(Long id);

    List<CellInstance> getCellInstances();

    CellInstance connectCells(ConnectCellsRequest connectCellsRequest);

    List<Connection> getConnections();

    void clear();

    void deleteCellInstance(Long id);

    void deleteConnection(Long id);

    List<CellInstance> getInputCells();

    void updateCoordinates(CellInstance cellInstance, double x, double y);

   // CellInstance getHighestCellId();
}
