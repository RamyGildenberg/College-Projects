package com.retinaX.coreAPI.simulateNetworkAPI.response;

import com.retinaX.entities.CellInstance;
import com.retinaX.entities.cellData.CellData;

import java.util.List;
import java.util.Map;

public class SimulationResult {

    private List<CellInstance> illegallyConnectedInstances;

    private boolean simulationSucceed;

    private Map<Long, CellData[]> cellsResults;

    public SimulationResult() {

    }

    public SimulationResult(boolean simulationSucceed) {
        setSimulationSucceed(simulationSucceed);
    }

    public SimulationResult(List<CellInstance> illegallyConnectedInstances, boolean simulationSucceed) {
        setIllegallyConnectedInstances(illegallyConnectedInstances);
        setSimulationSucceed(simulationSucceed);
    }

    public SimulationResult(Map<Long, CellData[]> cellsResults){
        setSimulationSucceed(true);
        setCellsResults(cellsResults);
    }

    public List<CellInstance> getIllegallyConnectedInstances() {
        return illegallyConnectedInstances;
    }

    public void setIllegallyConnectedInstances(List<CellInstance> illegallyConnectedInstances) {
        this.illegallyConnectedInstances = illegallyConnectedInstances;
    }

    public boolean isSimulationSucceed() {
        return simulationSucceed;
    }

    public void setSimulationSucceed(boolean simulationSucceed) {
        this.simulationSucceed = simulationSucceed;
    }

    public void setCellsResults(Map<Long, CellData[]> cellsResults) {
        this.cellsResults = cellsResults;
    }

    public Map<Long, CellData[]> getCellsResults() {
        return cellsResults;
    }
}
