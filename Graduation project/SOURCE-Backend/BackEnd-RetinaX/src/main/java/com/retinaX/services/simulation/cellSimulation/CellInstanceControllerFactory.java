package com.retinaX.services.simulation.cellSimulation;

import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.entities.CellType;
import com.retinaX.services.function.FunctionService;
import com.retinaX.services.simulation.cellSimulation.cellPublishers.AnalogCellPublisher;
import com.retinaX.services.simulation.cellSimulation.cellPublishers.CellPublisher;
import com.retinaX.services.simulation.cellSimulation.cellPublishers.DigitalCellPublisher;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.CellReceiver;
import com.retinaX.services.simulation.cellSimulation.cellReceivers.CellReceiverImplementation;
import com.retinaX.services.simulation.cellSimulation.functionInvokers.MXFunctionParserInvoker;
import org.springframework.stereotype.Service;

@Service
public class CellInstanceControllerFactory {

    private CellInstanceControllerFactory() {

    }

    public CellInstanceController makeCellInstanceController(CellInstance cellInstance) {
        CellType cellType = cellInstance.getCellType();

        CellReceiver cellReceiver = getCellReceiver(cellType.getInputType());

        CellPublisher cellPublisher = getCellPublisher(cellType.getOutputType());

        FunctionService functionService = new FunctionService(cellType.getFunction(), new MXFunctionParserInvoker());

        return new CellInstanceController(cellInstance, cellPublisher, cellReceiver, functionService);
    }


    private CellPublisher getCellPublisher(CellTransformType.Type outputType) {
        CellPublisher cellPublisher = null;

        switch (outputType) {
            case DIGITAL:
                cellPublisher = new DigitalCellPublisher();
                break;

            case ANALOG:
            case INPUT:
                cellPublisher = new AnalogCellPublisher();
                break;

            default:
                throw new RuntimeException("No implementation for " + outputType + "  Cell Type");
        }

        return cellPublisher;
    }

    private CellReceiver getCellReceiver(CellTransformType.Type outputType) {
        CellReceiver cellReceiver;

        switch (outputType){
            case DIGITAL: case ANALOG: case INPUT:
                cellReceiver = new CellReceiverImplementation();
                break;

            default:
                throw new RuntimeException("No implementation for " + outputType + "  Cell Type");
        }

        return cellReceiver;
    }
}
