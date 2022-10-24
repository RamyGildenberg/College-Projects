package com.retinaX.services.simulation.simulateNetwork;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.services.utils.ParameterizedLock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConnectionBuffer {

    private int delay;
    private Map<Integer, CellData> cellDataBuffer;
    private ParameterizedLock lock;

    public ConnectionBuffer(int delay, ParameterizedLock lock){
        setDelay(delay);
        setLock(lock);
        cellDataBuffer = Collections.synchronizedMap(new HashMap<>());
    }

    public void add(int receiveTime, CellData cellData){
        int timeToReleaseData = receiveTime + delay;
        try{
            lock.lock();
            cellDataBuffer.put(timeToReleaseData, cellData);
            lock.awake(timeToReleaseData);
        }finally {
            lock.unlock();
        }
    }

    public CellData getCellData(int time){
        CellData itemToReturn;
        if(time < delay){
            itemToReturn = new CellData(0);
        }
        else if(cellDataBuffer.containsKey(time)){
            itemToReturn = cellDataBuffer.get(time);
        }else{
            throw new IllegalArgumentException("");
        }

        return itemToReturn;
    }

    public boolean isCellDataReady(int time){
        return time < delay || cellDataBuffer.containsKey(time);
    }

    private void setDelay(int delay) {
        this.delay = delay;
    }

    private void setLock(ParameterizedLock lock) {
        this.lock = lock;
    }
}
