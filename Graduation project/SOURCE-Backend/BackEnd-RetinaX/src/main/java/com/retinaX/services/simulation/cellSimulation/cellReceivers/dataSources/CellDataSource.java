package com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;

import java.util.Map;

public interface CellDataSource {
    Map<Variable, CellData> receive(int timePoint);
}
