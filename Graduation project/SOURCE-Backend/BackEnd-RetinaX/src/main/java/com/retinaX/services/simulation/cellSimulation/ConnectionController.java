package com.retinaX.services.simulation.cellSimulation;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.CellInputSubscriber;
import com.retinaX.services.simulation.simulateNetwork.ConnectionBuffer;
import com.retinaX.services.utils.ParameterizedLock;

public class ConnectionController {

    private ConnectionBuffer connectionBuffer;
    private ParameterizedLock lock;
    private CellInputSubscriber cellInputSubscriber;

    public ConnectionController(int connectionDelay) {
        lock = new ParameterizedLock();
        connectionBuffer = new ConnectionBuffer(connectionDelay, lock);
        cellInputSubscriber = new CellInputSubscriber(connectionBuffer);
    }

    public CellData getCellData(int time) throws InterruptedException {
        if(!connectionBuffer.isCellDataReady(time)){
            try {
                lock.lock();
                lock.await(time);
            }finally {
                lock.unlock();
            }
        }

        return connectionBuffer.getCellData(time);
    }

    public CellInputSubscriber getCellInputSubscriber() {
        return cellInputSubscriber;
    }
}
