package com.retinaX.services.simulation.simulateNetwork;


import com.retinaX.entities.CellInstance;
import com.retinaX.entities.Connection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SimulationNetworkBuilder {

    Map<CellInstance, Set<Connection>> getSimulationNetwork(List<CellInstance> simulationOutputCells);
}
