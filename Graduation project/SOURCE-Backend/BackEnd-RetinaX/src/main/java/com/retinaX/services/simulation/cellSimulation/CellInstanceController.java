package com.retinaX.services.simulation.cellSimulation;


import com.retinaX.entities.CellInstance;
import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.simulation.cellSimulation.cellPublishers.CellPublisher;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.CellReceiver;
import com.retinaX.services.function.FunctionService;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.dataSources.CellDataSource;

import java.util.Map;

public class CellInstanceController{

    private CellInstance cellInstance;
    private CellPublisher cellPublisher;
    private CellReceiver cellReceiver;
    private FunctionService functionService;

    public CellInstanceController(CellInstance cellInstance, CellPublisher cellPublisher, CellReceiver cellReceiver, FunctionService functionService) {
        setCellInstance(cellInstance);
        setCellPublisher(cellPublisher);
        setCellReceiver(cellReceiver);
        setFunctionService(functionService);
    }

    public void simulateCell(int maxTime, CellDataSource cellDataSource){
        cellReceiver.init(cellDataSource);

        for(int t = 0 ; t < maxTime ; t++){
            Map<Variable, CellData> receivedData = cellReceiver.receive(t);
            CellData invocationResult = functionService.invoke(receivedData);
            cellPublisher.send(t, invocationResult);
        }

        cellPublisher.close();
    }

    public void subscribe(CellSubscriber cellSubscriber){
        cellPublisher.addSubscriber(cellSubscriber);
    }

    public Long getId(){
        return cellInstance.getId();
    }

    public CellInstance getCellInstance() {
        return cellInstance;
    }

    public void setCellInstance(CellInstance cellInstance) {
        this.cellInstance = cellInstance;
    }

    public CellPublisher getCellPublisher() {
        return cellPublisher;
    }

    public void setCellPublisher(CellPublisher cellPublisher) {
        this.cellPublisher = cellPublisher;
    }

    public CellReceiver getCellReceiver() {
        return cellReceiver;
    }

    public void setCellReceiver(CellReceiver cellReceiver) {
        this.cellReceiver = cellReceiver;
    }

    public FunctionService getFunctionService() {
        return functionService;
    }

    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }
}
