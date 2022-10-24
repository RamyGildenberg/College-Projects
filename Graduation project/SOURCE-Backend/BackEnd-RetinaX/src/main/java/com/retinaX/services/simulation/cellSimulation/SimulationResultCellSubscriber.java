package com.retinaX.services.simulation.cellSimulation;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.services.simulation.cellSimulation.cellPublishers.CellDataTime;

import java.util.concurrent.Flow;

public class SimulationResultCellSubscriber implements CellSubscriber {

    private Flow.Subscription subscription;
    private CellData results[];


    public SimulationResultCellSubscriber(int maxTime){
        results = new CellData[maxTime];
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        setSubscription(subscription);
        subscription.request(1);
    }

    @Override
    public void onNext(CellDataTime item) {
        //TODO LOG
        if(item.getTimePoint() >= results.length){

        }

        results[item.getTimePoint()] = item.getCellData();
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }


    public CellData[] getResults() {
        return results;
    }

    private void setSubscription(Flow.Subscription subscription) {
        this.subscription = subscription;
    }
}
