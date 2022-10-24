package com.retinaX.services.simulation.cellSimulation.cellReceivers;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources.CellDataSource;

import java.util.Map;

public interface CellReceiver {
    void init(CellDataSource cellDataSource);

    Map<Variable, CellData> receive(int timePoint);
}
