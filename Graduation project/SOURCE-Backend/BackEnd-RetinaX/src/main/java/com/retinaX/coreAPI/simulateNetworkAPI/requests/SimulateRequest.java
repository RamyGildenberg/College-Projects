package com.retinaX.coreAPI.simulateNetworkAPI.requests;

import com.retinaX.entities.CellInstance;
import com.retinaX.entities.cellData.CellData;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulateRequest {
    private List<CellInstance> outputCells;
    //long is the id of the input cell
    private Map<Long, List<Double>> userInput;
    private int maxTime;


    public SimulateRequest() {
    }

    public SimulateRequest(List<CellInstance> outputCells) {
        this.outputCells = outputCells;
    }

    public List<CellInstance> getOutputCells() {
        return outputCells;
    }

    public void setOutputCells(List<CellInstance> outputCells) {
        this.outputCells = outputCells;
    }

    public Map<Long, List<Double>> getUserInput() {
        return userInput;
    }

    public void setUserInput(Map<Long, List<Double>> userInput) {
        this.userInput = userInput;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public String toString() {
        return "SimulateRequest{" +
                "outputCells=" + outputCells +
                '}';
    }
}
