package com.retinaX.services.simulation.cellSimulation.cellPublishers;

import com.retinaX.entities.cellData.CellData;

public class CellDataTime {

    private int timePoint;

    private CellData cellData;

    public CellDataTime(int timePoint, CellData cellData) {
        this.timePoint = timePoint;
        this.cellData = cellData;
    }

    public int getTimePoint() {
        return timePoint;
    }

    public CellData getCellData() {
        return cellData;
    }
}
