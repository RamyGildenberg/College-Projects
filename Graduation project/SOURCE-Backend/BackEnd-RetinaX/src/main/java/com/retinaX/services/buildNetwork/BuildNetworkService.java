package com.retinaX.services.buildNetwork;

//import com.retinaX.coreAPI.buildNetworkAPI.requests.AddBlockRequest;
import com.retinaX.coreAPI.buildNetworkAPI.requests.*;
import com.retinaX.entities.*;

import java.util.List;
import java.util.Optional;

public interface BuildNetworkService {

    CellType createCellType(CreateCellTypeRequest createCellTypeRequest);

    CellInstance addCellInstance(AddCellInstanceRequest addCellInstanceRequest);

    CellInstance connectCells(ConnectCellsRequest connectCellsRequest);

    //SubGraphInstance getSubGraph(Long id);
    //SubGraphInstance getSubGraph(String id);
    List<SubGraphInstance> getSubGraph(String id);
    //Optional<SubGraphInstance> getSubGraph(Long id);

    CellType getCellType(Long id);

    List<CellType> getCellTypes();

    CellInstance getCellInstance(Long id);

    List<CellInstance> getCellInstances();

    Boolean areCompatible(CellTransformType sourceCell, CellTransformType destCell);

    List<Connection> getConnections();

    SubGraphInstance createSubGraph(AddSubGraphRequest request);

    void clear();

    void deleteCellType(Long id);

    void deleteCellInstance(Long id);

    void deleteConnection(Long id);

    //void deleteFunction(Long id);

    List<CellInstance> getInputCells();

    void updateCellInstanceCoordinates(CellInstance cellInstance, double x, double y);

    SubGraphInstance cloneSubGraph(Long id);

    //  List<SubGraphInstance> duplicateGraph();

    //SubGraphInstance filterSubGraph(String subGraphFilter);
}
