package com.retinaX.services.simulation.cellSimulation.cellPublishers;


import com.retinaX.entities.cellData.CellData;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DigitalCellPublisher extends CellPublisher {

    private Queue<Integer> digitalBitsQueue = new LinkedList<>();
    private int currentDataSentReceiveTimePoint;

    @Override
    public void send(int timePoint, CellData cellData) {
        if (digitalBitsQueue.isEmpty()) {
            digitalBitsQueue.addAll(Arrays.asList(cellData.toDigitalArrayOfBits()));
            currentDataSentReceiveTimePoint = timePoint;
        }
        publishData();
    }

    private void publishData() {
        if (digitalBitsQueue.peek() != null) {
            publisher.submit(new CellDataTime(currentDataSentReceiveTimePoint++, new CellData(digitalBitsQueue.poll())));
        } else {
            throw new NullPointerException();
        }
    }
}
