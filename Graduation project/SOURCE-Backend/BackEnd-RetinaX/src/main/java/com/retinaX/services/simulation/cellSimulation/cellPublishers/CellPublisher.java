package com.retinaX.services.simulation.cellSimulation.cellPublishers;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.services.simulation.cellSimulation.CellSubscriber;

import java.util.concurrent.SubmissionPublisher;

public abstract class CellPublisher {

    protected SubmissionPublisher<CellDataTime> publisher;

    public CellPublisher(){
        publisher = new SubmissionPublisher<>();
    }

    public void addSubscriber(CellSubscriber subscriber) {
        publisher.subscribe(subscriber);
    }

    public abstract void send(int timePoint, CellData cellData);

    public void close(){
        publisher.close();
    }
}
