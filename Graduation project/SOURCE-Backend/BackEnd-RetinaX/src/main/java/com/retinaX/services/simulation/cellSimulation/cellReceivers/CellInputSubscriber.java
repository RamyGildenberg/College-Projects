package com.retinaX.services.simulation.cellSimulation.cellReceivers;

import com.retinaX.services.simulation.cellSimulation.CellSubscriber;
import com.retinaX.services.simulation.cellSimulation.cellPublishers.CellDataTime;
import com.retinaX.services.simulation.simulateNetwork.ConnectionBuffer;

import java.util.concurrent.Flow;

public class CellInputSubscriber implements CellSubscriber {

    private Flow.Subscription subscription;
    private ConnectionBuffer connectionBuffer;

    public CellInputSubscriber(ConnectionBuffer connectionBuffer) {
        setConnectionBuffer(connectionBuffer);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        setSubscription(subscription);
        subscription.request(1);
    }

    @Override
    public void onNext(CellDataTime item) {
        connectionBuffer.add(item.getTimePoint(), item.getCellData());
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        subscription.cancel();
        throw new IllegalArgumentException("Cell subscription exception: ", throwable);
    }

    @Override
    public void onComplete() {
        subscription.cancel();
    }

    private void setSubscription(Flow.Subscription subscription) {
        this.subscription = subscription;
    }

    private void setConnectionBuffer(ConnectionBuffer connectionBuffer) {
        this.connectionBuffer = connectionBuffer;
    }
}
