package com.retinaX.services.simulation.cellSimulation.cellReceivers;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources.CellDataSource;

import java.util.Map;

public class CellReceiverImplementation implements CellReceiver {

    private CellDataSource cellDataSource;

    public CellReceiverImplementation(){

    }

    @Override
    public void init(CellDataSource cellDataSource) {
        this.cellDataSource = cellDataSource;
    }

    @Override
    public Map<Variable, CellData> receive(int timePoint) {
        return cellDataSource.receive(timePoint);
    }

}
