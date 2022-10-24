package com.retinaX.services.simulation.cellSimulation.cellPublishers;

import com.retinaX.entities.cellData.CellData;

public class AnalogCellPublisher extends CellPublisher{

    @Override
    public void send(int timePoint, CellData cellData) {
        publisher.submit(new CellDataTime(timePoint, cellData));
    }
}
