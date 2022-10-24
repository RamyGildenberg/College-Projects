package com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources;

import com.retinaX.entities.Connection;
import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.simulation.cellSimulation.CellInstanceController;
import com.retinaX.services.simulation.cellSimulation.ConnectionController;

import java.util.HashMap;
import java.util.Map;

public class ConnectionsDataSource implements CellDataSource {

    private Map<Connection, ConnectionController> connectionsMap;

    public ConnectionsDataSource(Map<Connection, CellInstanceController> inputCells){
        connectionsMap = new HashMap<>();

        inputCells.forEach((this::initConnection));
    }

    @Override
    public Map<Variable, CellData> receive(int timePoint) {
        Map<Variable, CellData> dataAtTimePoint = new HashMap<>();
        connectionsMap.forEach((connection, connectionController) -> {
            try {
                dataAtTimePoint.put(connection.getVariable(), connectionController.getCellData(timePoint));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return dataAtTimePoint;
    }

    private void initConnection(Connection connection, CellInstanceController cellInstanceController) {
        ConnectionController connectionController = new ConnectionController(connection.getDelay());
        connectionsMap.put(connection, connectionController);
        cellInstanceController.subscribe(connectionController.getCellInputSubscriber());
    }
}
