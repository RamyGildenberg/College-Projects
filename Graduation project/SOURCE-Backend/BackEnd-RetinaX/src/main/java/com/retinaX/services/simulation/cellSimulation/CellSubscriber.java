package com.retinaX.services.simulation.cellSimulation;

import com.retinaX.services.simulation.cellSimulation.cellPublishers.CellDataTime;

import java.util.concurrent.Flow;

public interface CellSubscriber extends Flow.Subscriber<CellDataTime> {

}
