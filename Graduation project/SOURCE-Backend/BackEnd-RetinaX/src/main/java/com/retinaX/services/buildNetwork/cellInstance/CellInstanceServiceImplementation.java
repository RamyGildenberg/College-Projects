package com.retinaX.services.buildNetwork.cellInstance;

import com.retinaX.coreAPI.buildNetworkAPI.requests.ConnectCellsRequest;
import com.retinaX.dal.CellInstanceDao;
import com.retinaX.dal.ConnectionDao;
import com.retinaX.dal.utils.IdGeneratorService;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellType;
import com.retinaX.entities.Connection;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.buildNetwork.cellType.CellTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CellInstanceServiceImplementation implements CellInstanceService {
    private CellInstanceDao cellInstanceDao;
    private ConnectionDao connectionDao;
    private CellTypeService cellTypeService;
    private IdGeneratorService idGeneratorService;

    public CellInstanceServiceImplementation(){
    }

    @Transactional
    @Override
    public CellInstance createCellInstance(CellType cellType, double xCoordinate, double yCoordinate) {
        CellInstance cellInstance = new CellInstance(cellType, xCoordinate, yCoordinate);
        return cellInstanceDao.save(cellInstance);
    }

    @Transactional(readOnly = true)
    @Override
    public CellInstance getCellInstance(Long id) {
        Optional<CellInstance> cellInstanceOptional = cellInstanceDao.findById(id, 3);

        if(cellInstanceOptional.isPresent()) {
            return cellInstanceOptional.get();
        }

        throw new NoSuchElementException("Cell instance with id: \"" + id+ "\" is not exist");
    }

    @Transactional(readOnly = true)
    @Override
    public List<CellInstance> getCellInstances() {
        List<CellInstance> cellInstancesList = new ArrayList<>();
        cellInstanceDao.findAll(3).forEach(cellInstancesList::add);
        return cellInstancesList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CellInstance> getInputCells() {
        CellType inputCellType = cellTypeService.getInputCellType();
        List<CellInstance> inputCells = cellInstanceDao.findAllByCellType(inputCellType.getId());
        inputCells.forEach(inputCell -> inputCell.setCellType(inputCellType));
        return inputCells;
    }

    @Transactional
    @Override
    public void updateCoordinates(CellInstance cellInstance, double x, double y) {
        CellInstance updatedCellInstance = getCellInstance(cellInstance.getId());
        updatedCellInstance.setxCoordinate(x);
        updatedCellInstance.setyCoordinate(y);
        cellInstanceDao.save(updatedCellInstance);
    }

    @Transactional
    public void deletePreviousConnection(ConnectCellsRequest connectCellsRequest){
        Variable variable = getFunctionVariableByName(connectCellsRequest.getDestinationCell(), connectCellsRequest.getInputFunctionVariable());
        connectionDao.deleteConnectionByToCellAndAndConnectionVariable(connectCellsRequest.getDestinationCell(), variable);
    }


    @Transactional
    @Override
    public CellInstance connectCells(ConnectCellsRequest connectCellsRequest) {
        Optional<CellInstance> src = cellInstanceDao.findById(connectCellsRequest.getSourceCell().getId(),3);
        //to do throw if not found
        Optional<CellInstance> dest = cellInstanceDao.findById(connectCellsRequest.getDestinationCell().getId(),3);
        connectCellsRequest.setSourceCell(src.get());
        connectCellsRequest.setDestinationCell(dest.get());
        deletePreviousConnection(connectCellsRequest);
        //connectionDao.findAllById(List.of(1L,2L,3L));
        Connection connection = createConnection(connectCellsRequest);
        connectionDao.save(connection);
        CellInstance destination = connection.getToCell();
        return cellInstanceDao.save(destination);
    }

    @Override
    public List<Connection> getConnections() {
        List<Connection> connections = new LinkedList<>();
        connectionDao.findAll(4).forEach(connections::add);
        return connections;
    }

    @Transactional
    @Override
    public void clear() {
        cellInstanceDao.deleteAll();
        connectionDao.deleteAll();
    }

    @Override
    public void deleteCellInstance(Long id) {
        cellInstanceDao.findById(id).ifPresent(cellInstance -> {
            connectionDao.deleteConnectionByToCellOrFromCell(cellInstance);
            cellInstanceDao.deleteById(id);
        });
    }

    @Override
    public void deleteConnection(Long id) {
        connectionDao.deleteById(id);
    }

    @Autowired
    public void setCellInstanceDao(CellInstanceDao cellInstanceDao) {
        this.cellInstanceDao = cellInstanceDao;
    }

    @Autowired
    public void setIdGeneratorService(IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    @Autowired
    public void setCellTypeService(CellTypeService cellTypeService) {
        this.cellTypeService = cellTypeService;
    }

    @Autowired
    public void setConnectionDao(ConnectionDao connectionDao) {
        this.connectionDao = connectionDao;
    }

    private Connection createConnection(ConnectCellsRequest connectCellsRequest){
        CellInstance sourceCell = getCellInstance(connectCellsRequest.getSourceCell().getId());
        CellInstance destinationCell = getCellInstance(connectCellsRequest.getDestinationCell().getId());
        if(!cellTypeService.areCompatible(sourceCell, destinationCell)){
            throw new IllegalArgumentException(
                    "source cell is not compatible with destination cell. got source cell output type to be: " +
                    sourceCell.getCellType().getOutputType() + " and destination input type: " +
                            destinationCell.getCellType().getInputType());
        }
        Variable variable = getFunctionVariableByName(destinationCell, connectCellsRequest.getInputFunctionVariable());
        return new Connection(connectCellsRequest.getDelay(),
                sourceCell, destinationCell, variable);
    }

    private Variable getFunctionVariableByName(CellInstance cell, String variableName){
        return cellTypeService.getVariableByCellTypeAndVariableName(cell.getCellType(), variableName);
    }
}
