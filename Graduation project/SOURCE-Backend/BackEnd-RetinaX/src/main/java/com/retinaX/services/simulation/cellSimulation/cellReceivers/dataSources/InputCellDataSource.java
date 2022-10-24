package com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources;

import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputCellDataSource implements CellDataSource{
    private Variable inputVariable;
    private List<CellData> inputData;

    public InputCellDataSource(Variable inputVariable, List<CellData> inputData){
        setInputVariable(inputVariable);
        setInputData(inputData);
    }

    @Override
    public Map<Variable, CellData> receive(int timePoint) {
        if(timePoint >= inputData.size()) {
            throw new RuntimeException("Received a timePoint greater than the input set");
        }
        CellData data = inputData.get(timePoint);

        Map<Variable, CellData> receivedData = new HashMap<>();
        receivedData.put(inputVariable, data);
        return receivedData;
    }

    private void setInputData(List<CellData> inputData) {
        this.inputData = inputData;
    }

    private void setInputVariable(Variable inputVariable) {
        this.inputVariable = inputVariable;
    }
}
