package com.retinaX.services.SubGraphNetwork;

import com.retinaX.coreAPI.buildNetworkAPI.requests.AddSubGraphRequest;
import com.retinaX.entities.*;

import java.util.List;

public interface SubGraphService {

    //TODO: Maybe should return result? (SubGraph)
    SubGraphInstance createSubGraph(AddSubGraphRequest request);

    //Optional<SubGraphInstance> getSubGraph(Long id);

    //SubGraphInstance findByFilter(SubGraphFilter subGraphFilter);
    List<SubGraphInstance> getSubGraph(String subGraph);
    // SubGraphInstance getSubGraph(Long id);


    List<CellInstance> getCellInstances();

    //CellInstance connectCells(ConnectCellsRequest connectCellsRequest);

    List<Connection> getConnections();

    void clear();

    void deleteSubGraph(Long id);

    List<CellInstance> getInputCells();

    //   List<SubGraphInstance> duplicateGraph(Long id);

    SubGraphInstance cloneSubGraph(Long id);


}