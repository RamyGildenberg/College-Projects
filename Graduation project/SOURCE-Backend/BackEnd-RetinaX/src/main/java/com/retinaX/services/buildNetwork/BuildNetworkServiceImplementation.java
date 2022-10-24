package com.retinaX.services.buildNetwork;

import com.retinaX.coreAPI.buildNetworkAPI.requests.*;
import com.retinaX.entities.*;
import com.retinaX.services.SubGraphNetwork.SubGraphService;
import com.retinaX.services.SubGraphNetwork.SubGraphServiceImplementation;
import com.retinaX.services.buildNetwork.cellInstance.CellInstanceService;
import com.retinaX.services.buildNetwork.cellType.CellTypeService;
import com.retinaX.services.function.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BuildNetworkServiceImplementation implements BuildNetworkService {

    private CellTypeService cellTypeService;
    private CellInstanceService cellInstanceService;
    private SubGraphServiceImplementation subGraphService;

    private FunctionService functionService;

    @Override
    public CellType createCellType(CreateCellTypeRequest createCellTypeRequest) {
        return cellTypeService.createCellType(createCellTypeRequest);
    }

    @Override
    public CellInstance addCellInstance(AddCellInstanceRequest addCellInstanceRequest) {
        CellType cellType = cellTypeService.getCellType(addCellInstanceRequest.getCellTypeId());
        return cellInstanceService.createCellInstance(cellType, addCellInstanceRequest.getX(), addCellInstanceRequest.getY());
    }

    @Override
    public CellInstance connectCells(ConnectCellsRequest connectCellsRequest) {

        return cellInstanceService.connectCells(connectCellsRequest);
    }

    public List<SubGraphInstance> getSubGraph(String id){ return subGraphService.getSubGraph(id);}



  //  public Optional<SubGraphInstance> getSubGraph(Long id){ return subGraphService.getSubGraph(id);}









    /*
    @Override
    public SubGraphInstance getSubGraph(String id){ return subGraphService.getSubGraph(id);}
*/
    /*
    @Override
    public SubGraphInstance getSubGraph(Long id){ return subGraphService.getSubGraph(id);}
*/
    @Override
    public CellType getCellType(Long id) {
        return cellTypeService.getCellType(id);
    }

    @Override
    public List<CellType> getCellTypes() {
        return cellTypeService.getExistingCellTypes();
    }

    @Override
    public CellInstance getCellInstance(Long id) {
        return cellInstanceService.getCellInstance(id);
    }

    @Override
    public List<CellInstance> getCellInstances() {
        return cellInstanceService.getCellInstances();
    }

    @Override
    public List<CellInstance> getInputCells() {
        return cellInstanceService.getInputCells();
    }

    @Override
    public void updateCellInstanceCoordinates(CellInstance cellInstance, double x, double y) {
        cellInstanceService.updateCoordinates(cellInstance, x, y);
    }

    /*
    @Override
    public List<SubGraphInstance> duplicateGraph(Long id) {
        return subGraphService.duplicateGraph(id);
    }
*/
    /*
        @Override
        public SubGraphInstance getSubGraph(String subGraphFilter) {
            return subGraphService.getSubGraph(subGraphFilter);
        }
    */
    @Override
    public Boolean areCompatible(CellTransformType sourceTransformType, CellTransformType destTransformType) {
        return cellTypeService.areCompatible(sourceTransformType, destTransformType);
    }

    @Override
    public List<Connection> getConnections() {
        return cellInstanceService.getConnections();
    }

    @Override
    public SubGraphInstance createSubGraph(AddSubGraphRequest request) {
      return subGraphService.createSubGraph(request);
    }

    @Override
    public void clear(){
       cellTypeService.clear();
       cellInstanceService.clear();
    }

    @Override
    public void deleteCellType(Long id) {
        cellTypeService.deleteCellType(id);
    }

    @Override
    public void deleteCellInstance(Long id) {
        cellInstanceService.deleteCellInstance(id);
    }

    @Override
    public void deleteConnection(Long id) {
        cellInstanceService.deleteConnection(id);
    }

//    @Override
//    public void deleteFunction(Long id) {
//        functionService.deleteFunction(id);
//    }

    @Autowired
    public void setCellTypeService(CellTypeService cellTypeService) {
        this.cellTypeService = cellTypeService;
    }

    @Autowired
    public void setCellInstanceService(CellInstanceService cellInstanceService) {
        this.cellInstanceService = cellInstanceService;
    }

    @Autowired
    public void setSubGraphService(SubGraphServiceImplementation subGraphService) {
        this.subGraphService = subGraphService;
    }

    public SubGraphInstance cloneSubGraph(Long id)
    {
        return subGraphService.cloneSubGraph(id);
    }

}
